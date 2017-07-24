package ru.dm.shop.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.dm.shop.entity.Cart;
import ru.dm.shop.entity.CartProduct;
import ru.dm.shop.entity.Order;
import ru.dm.shop.service.CartProductService;
import ru.dm.shop.service.OrderService;
import ru.dm.shop.service.UserService;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Denis on 22/03/2016.
 */


@Controller
@RequestMapping("/placeorder")
public class PlaceOrderController {
    @Autowired
    JavaMailSender mailSender;

    @Autowired
    CartProductService cartProductService;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    ServletContext context;


    String FONT;

    @RequestMapping(value = "/email", method = RequestMethod.GET)
    public String emailOrder(ModelMap model, HttpSession session) throws Exception {
        return "email";
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public String placeOrder(ModelMap model, HttpSession session, @RequestParam(value = "email", required = true) String email) throws Exception {

        FONT = session.getServletContext().getRealPath("/WEB-INF/font/");
        // String path = session.getServletContext().getRealPath("/resources/");
        String path = context.getRealPath("/resources/");
        Cart cart;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            if (session.getAttribute("cart") == null) session.setAttribute("cart", new Cart());
            cart = (Cart) session.getAttribute("cart");

            session.setAttribute("cart", null); // Очищаем корзину
        } else {
            cart = cartProductService.getCart();
            cartProductService.removeCart(cart);
        }

        Order order = orderService.create(cart);
        excelMail(cart, email, order, path);

        return "redirect:/cart";
    }

    public void pdfMail(Cart cart, String email) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

        helper.setTo(email);
        helper.setSubject("This is the test message for testing gmail smtp server using spring mail");
        helper.setFrom("webmaventest@gmail.com");

        //now write the PDF content to the output stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        writePdf(outputStream, cart);
        byte[] bytes = outputStream.toByteArray();

        //construct the pdf body part
        DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
        MimeBodyPart pdfBodyPart = new MimeBodyPart();
        pdfBodyPart.setDataHandler(new DataHandler(dataSource));
        pdfBodyPart.setFileName("order_otrajenie.pdf");

        //construct the mime multi part
        MimeMultipart mimeMultipart = new MimeMultipart();
        mimeMultipart.addBodyPart(pdfBodyPart);

        mimeMessage.setContent(mimeMultipart);

        mailSender.send(mimeMessage);
    }

    public void htmlMail(Cart cart, String email, String path) throws MessagingException, FileNotFoundException {
        String htmlMsg = "";

        Scanner in = new Scanner(new File(path + "mailstart.html"));
        while (in.hasNext()) htmlMsg += in.nextLine() + "\r\n";
        in.close();

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

        helper.setTo(email);
        helper.setSubject("Оформление заказа на сайте shop.otrajenie.com");
        helper.setFrom("webmaventest@gmail.com");

        for (CartProduct cartProduct : cart.getCartProducts()) {
            htmlMsg += "<tr>";
            htmlMsg += "<td>" + cartProduct.getProduct().getArticule() + "</td>";
            htmlMsg += "<td>" + cartProduct.getProduct().getName() + "</td>";
            htmlMsg += "<td>" + cartProduct.getProduct().getRetailPrice() + " " + cartProduct.getProduct().getCurrency() + "</td>";
            htmlMsg += "<td>" + cartProduct.getCount() + "</td>";
            htmlMsg += "<td>" + cartProduct.getProduct().getRetailPrice().multiply(new BigDecimal(cartProduct.getCount())) + " " + cartProduct.getProduct().getCurrency() + "</td>";
            htmlMsg += "</tr>";
        }

        in = new Scanner(new File(path + "mailend.html"));
        while (in.hasNext()) htmlMsg += in.nextLine() + "\r\n";
        in.close();

        mimeMessage.setContent(htmlMsg, "text/html; charset=utf-8");

        mailSender.send(mimeMessage);
    }

    public void excelMail(Cart cart, String email, Order order, String path) throws Exception {
        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

        helper.setTo(email);
        helper.setSubject("Otrajenie Shop | Заказ №" + order.getId());
        helper.setFrom("webmaventest@gmail.com");

        //construct the pdf body part
        DataSource dataSource = buildExcelDocument(cart, order);
        MimeBodyPart pdfBodyPart = new MimeBodyPart();
        pdfBodyPart.setDataHandler(new DataHandler(dataSource));
        pdfBodyPart.setFileName("order_otrajenie.xls");

        //construct the mime multi part
        MimeMultipart mimeMultipart = new MimeMultipart();
        mimeMultipart.addBodyPart(pdfBodyPart);


        String htmlMsg = "";

        Scanner in = new Scanner(new File(path + "mailstart.html"));
        while (in.hasNext()) htmlMsg += in.nextLine() + "\r\n";
        in.close();


        for (CartProduct cartProduct : cart.getCartProducts()) {
            htmlMsg += "<tr>";
            htmlMsg += "<td>" + cartProduct.getProduct().getArticule() + "</td>";
            htmlMsg += "<td>" + cartProduct.getProduct().getName() + "</td>";
            String price = cartProduct.getProduct().getRetailPrice() + " " + cartProduct.getProduct().getCurrency();
            price = price.replace('.', ',');
            htmlMsg += "<td>" + price + "</td>";
            htmlMsg += "<td>" + cartProduct.getCount() + "</td>";
            price = cartProduct.getProduct().getRetailPrice().multiply(new BigDecimal(cartProduct.getCount())) + " " + cartProduct.getProduct().getCurrency();
            price = price.replace('.', ',');
            htmlMsg += "<td>" + price + "</td>";
            htmlMsg += "</tr>";
        }

        in = new Scanner(new File(path + "mailend.html"));
        while (in.hasNext()) htmlMsg += in.nextLine() + "\r\n";
        in.close();

        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(htmlMsg, "text/html; charset=utf-8");
        mimeMultipart.addBodyPart(htmlPart);

        mimeMessage.setContent(mimeMultipart);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mailSender.send(mimeMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    public DataSource buildExcelDocument(Cart cart, Order order) throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook(); // create a workbook
        HSSFSheet sheet = workbook.createSheet("Заказ");

        HSSFCellStyle cellinfo = workbook.createCellStyle();
        HSSFFont hSSFFontInfo = workbook.createFont();
        hSSFFontInfo.setFontName(HSSFFont.FONT_ARIAL);
        hSSFFontInfo.setFontHeightInPoints((short) 11);
        hSSFFontInfo.setColor(HSSFColor.BLACK.index);
        cellinfo.setFont(hSSFFontInfo);

        cellinfo.setWrapText(true);
        cellinfo.setVerticalAlignment(VerticalAlignment.TOP);
        cellinfo.setAlignment(HorizontalAlignment.LEFT);


        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFFont hSSFFont = workbook.createFont();
        hSSFFont.setFontName(HSSFFont.FONT_ARIAL);
        hSSFFont.setFontHeightInPoints((short) 11);
        hSSFFont.setColor(HSSFColor.BLACK.index);
        cellStyle.setFont(hSSFFont);

        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

        cellStyle.setWrapText(true);
        cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);


        HSSFCellStyle numStyle = workbook.createCellStyle();
        HSSFFont hSSFFont3 = workbook.createFont();
        hSSFFont3.setFontName(HSSFFont.FONT_ARIAL);
        hSSFFont3.setFontHeightInPoints((short) 11);
        hSSFFont3.setColor(HSSFColor.BLACK.index);
        numStyle.setFont(hSSFFont3);

        numStyle.setBorderBottom(BorderStyle.THIN);
        numStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        numStyle.setBorderLeft(BorderStyle.THIN);
        numStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        numStyle.setBorderRight(BorderStyle.THIN);
        numStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        numStyle.setBorderTop(BorderStyle.THIN);
        numStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

        numStyle.setWrapText(true);
        numStyle.setVerticalAlignment(VerticalAlignment.TOP);
        numStyle.setAlignment(HorizontalAlignment.RIGHT);


        HSSFCellStyle numStyleBold = workbook.createCellStyle();
        HSSFFont hSSFFont4 = workbook.createFont();
        hSSFFont4.setFontName(HSSFFont.FONT_ARIAL);
        hSSFFont4.setBold(true);
        hSSFFont4.setFontHeightInPoints((short) 11);
        hSSFFont4.setColor(HSSFColor.BLACK.index);
        numStyleBold.setFont(hSSFFont4);

        numStyleBold.setBorderBottom(BorderStyle.THIN);
        numStyleBold.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        numStyleBold.setBorderLeft(BorderStyle.THIN);
        numStyleBold.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        numStyleBold.setBorderRight(BorderStyle.THIN);
        numStyleBold.setRightBorderColor(IndexedColors.BLACK.getIndex());
        numStyleBold.setBorderTop(BorderStyle.THIN);
        numStyleBold.setTopBorderColor(IndexedColors.BLACK.getIndex());

        numStyleBold.setWrapText(true);
        numStyleBold.setVerticalAlignment(VerticalAlignment.TOP);
        numStyleBold.setAlignment(HorizontalAlignment.RIGHT);


        HSSFCellStyle cellStyleBold = workbook.createCellStyle();
        HSSFFont hSSFFont2 = workbook.createFont();
        hSSFFont2.setFontName(HSSFFont.FONT_ARIAL);
        hSSFFont2.setBold(true);
        hSSFFont2.setFontHeightInPoints((short) 11);
        hSSFFont2.setColor(HSSFColor.BLACK.index);
        cellStyleBold.setFont(hSSFFont2);

        cellStyleBold.setBorderBottom(BorderStyle.THIN);
        cellStyleBold.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBold.setBorderLeft(BorderStyle.THIN);
        cellStyleBold.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBold.setBorderRight(BorderStyle.THIN);
        cellStyleBold.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBold.setBorderTop(BorderStyle.THIN);
        cellStyleBold.setTopBorderColor(IndexedColors.BLACK.getIndex());

        cellStyleBold.setWrapText(true);
        cellStyleBold.setVerticalAlignment(VerticalAlignment.TOP);
        cellStyleBold.setAlignment(HorizontalAlignment.LEFT);


        int rowNum = 0;

        HSSFRow main = sheet.createRow(rowNum++);
        Cell maincell1 = main.createCell(0);
        maincell1.setCellValue("Заказ №");
        maincell1.setCellStyle(cellinfo);
        Cell maincell2 = main.createCell(1);
        maincell2.setCellValue(order.getId());
        maincell2.setCellStyle(cellinfo);

        main = sheet.createRow(rowNum++);
        Cell maincell3 = main.createCell(0);
        maincell3.setCellValue("Дата и время");
        maincell3.setCellStyle(cellinfo);
        Cell maincell4 = main.createCell(1);

        String date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(order.getDate());
        maincell4.setCellValue(date);
        maincell4.setCellStyle(cellinfo);

        main = sheet.createRow(rowNum++);
        Cell maincell5 = main.createCell(0);
        maincell5.setCellValue("Название организации");
        maincell5.setCellStyle(cellinfo);
        Cell maincell6 = main.createCell(1);
        maincell6.setCellValue("Отражение");
        maincell6.setCellStyle(cellinfo);

        main = sheet.createRow(rowNum++);
        Cell maincell7 = main.createCell(0);
        maincell7.setCellValue("Контактное лицо");
        maincell7.setCellStyle(cellinfo);
        Cell maincell8 = main.createCell(1);
        maincell8.setCellValue(order.getUser().getUsername());
        maincell8.setCellStyle(cellinfo);

        main = sheet.createRow(rowNum++);
        Cell maincell9 = main.createCell(0);
        maincell9.setCellValue("Email");
        maincell9.setCellStyle(cellinfo);
        Cell maincell10 = main.createCell(1);
        maincell10.setCellValue(order.getUser().getEmail());
        maincell10.setCellStyle(cellinfo);

        rowNum++;


        HSSFRow header = sheet.createRow(rowNum++);

        Cell cell = header.createCell(0);
        cell.setCellValue("Артикул");
        cell.setCellStyle(cellStyleBold);

        Cell cell1 = header.createCell(1);
        cell1.setCellValue("Наименование товара");
        cell1.setCellStyle(cellStyleBold);

        Cell cell2 = header.createCell(2);
        cell2.setCellValue("Цена");
        cell2.setCellStyle(numStyleBold);

        Cell cell3 = header.createCell(3);
        cell3.setCellValue("Количество");
        cell3.setCellStyle(numStyleBold);

        Cell cell4 = header.createCell(4);
        cell4.setCellValue("Сумма");
        cell4.setCellStyle(numStyleBold);


        for (CartProduct cartProduct : cart.getCartProducts()) {
            HSSFRow row = sheet.createRow(rowNum++);
            Cell cell5 = row.createCell(0);
            cell5.setCellValue(cartProduct.getProduct().getArticule());
            cell5.setCellStyle(cellStyle);
            Cell cell6 = row.createCell(1);
            cell6.setCellValue(cartProduct.getProduct().getName());
            cell6.setCellStyle(cellStyle);
            Cell cell7 = row.createCell(2);
            cell7.setCellValue(cartProduct.getProduct().getRetailPrice().doubleValue());
            cell7.setCellStyle(numStyle);
            cell7.setCellType(CellType.NUMERIC);
            Cell cell8 = row.createCell(3);
            cell8.setCellValue(cartProduct.getCount());
            cell8.setCellStyle(numStyle);
            cell8.setCellType(CellType.NUMERIC);
            Cell cell9 = row.createCell(4);
            cell9.setCellValue(cartProduct.getProduct().getRetailPrice().multiply(new BigDecimal(cartProduct.getCount())).doubleValue());
            cell9.setCellStyle(numStyle);
            cell9.setCellType(CellType.NUMERIC);
        }

        HSSFRow row = sheet.createRow(rowNum++);

        Cell cell10 = row.createCell(3);
        cell10.setCellValue("ИТОГО");
        cell10.setCellStyle(numStyleBold);
        Cell cell11 = row.createCell(4);
        cell11.setCellValue(cart.getPrice().doubleValue());
        cell11.setCellStyle(numStyleBold);
        cell11.setCellType(CellType.NUMERIC);

        sheet.autoSizeColumn(0);
        sheet.setColumnWidth(1, 100*150);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);



        // Write the output to a temporary excel file
        FileOutputStream fos = new FileOutputStream("temp.xls");
        workbook.write(fos);
        fos.close();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos); // write excel data to a byte array
        fos.close();

        // Now use your ByteArrayDataSource as
        DataSource fds = new ByteArrayDataSource(bos.toByteArray(), "application/vnd.ms-excel");

        return fds;

    }

    public void writePdf(OutputStream outputStream, Cart cart) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        Font font = FontFactory.getFont(FONT + "OpenSans-Regular.ttf", "Cp1251", BaseFont.EMBEDDED);
        font.setSize(12);
        font.setColor(new BaseColor(78, 127, 169));

        Font lfont = FontFactory.getFont(FONT + "OpenSans-Regular.ttf", "Cp1251", BaseFont.EMBEDDED);
        lfont.setSize(40);
        lfont.setColor(new BaseColor(78, 127, 169));

        Font pfont = FontFactory.getFont(FONT + "OpenSans-Regular.ttf", "Cp1251", BaseFont.EMBEDDED);
        pfont.setSize(14);

        Font dfont = FontFactory.getFont(FONT + "OpenSans-Regular.ttf", "Cp1251", BaseFont.EMBEDDED);
        dfont.setSize(12);
        dfont.setColor(new BaseColor(34, 34, 34));

        document.add(new Paragraph("Отражение", lfont));
        document.add(new Paragraph(" ", pfont));
        document.add(new Paragraph("Ваш заказ (№ " + "12233" + ")", pfont));
        document.add(new Paragraph(" ", pfont));
        document.add(new Paragraph(" ", pfont));

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[]{2.0f, 4.0f, 2.0f, 2.0f});
        table.setSpacingBefore(10);

        PdfPCell cell = new PdfPCell();
        cell.setBorderColor(BaseColor.WHITE);
        cell.setBorderColorBottom(new BaseColor(78, 127, 169));
        cell.setBackgroundColor(BaseColor.WHITE);
        cell.setBorderWidthBottom(2);
        cell.setPadding(5);
        cell.setPaddingBottom(8);

        cell.setPhrase(new Phrase("Артикул", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Наименование", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Количество", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Цена", font));
        table.addCell(cell);

        cell.setBorderColor(new BaseColor(204, 204, 204));
        cell.setBorderColorBottom(new BaseColor(204, 204, 204));
        cell.setBackgroundColor(BaseColor.WHITE);
        cell.setBorderWidthBottom(1);
        cell.setPadding(5);
        cell.setPaddingBottom(10);
        cell.setPaddingTop(10);

        int i = 1;
        for (CartProduct cartProduct : cart.getCartProducts()) {
            if (i % 2 == 0) cell.setBackgroundColor(new BaseColor(238, 238, 238));
            else cell.setBackgroundColor(BaseColor.WHITE);
            cell.setPhrase(new Phrase(cartProduct.getProduct().getArticule(), dfont));
            table.addCell(cell);
            cell.setPhrase(new Phrase(cartProduct.getProduct().getName(), dfont));
            table.addCell(cell);
            cell.setPhrase(new Phrase(cartProduct.getCount().toString(), dfont));
            table.addCell(cell);
            cell.setPhrase(new Phrase(cartProduct.getProduct().getRetailPrice().toString() + " " + cartProduct.getProduct().getCurrency(), dfont));
            table.addCell(cell);

            i++;
        }

        document.add(table);

        document.add(new Paragraph(" ", pfont));
        document.add(new Paragraph("Общая стоимость заказа: " + cart.getPrice() + " руб.", pfont));

        document.close();
    }


}
