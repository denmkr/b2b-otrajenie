package ru.dm.shop.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.dm.shop.entity.Cart;
import ru.dm.shop.entity.CartProduct;
import ru.dm.shop.service.CartProductService;
import ru.dm.shop.service.OrderService;
import ru.dm.shop.service.UserService;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
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

    String FONT;

    @RequestMapping(value = "/email", method = RequestMethod.GET)
    public String emailOrder(ModelMap model, HttpSession session) throws Exception {
        return "email";
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public String placeOrder(ModelMap model, HttpSession session, @RequestParam(value = "email", required = true) String email) throws Exception {

        FONT = session.getServletContext().getRealPath("/WEB-INF/font/");
        String path = session.getServletContext().getRealPath("/resources/");
        Cart cart;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            if (session.getAttribute("cart") == null) session.setAttribute("cart", new Cart());
            cart = (Cart) session.getAttribute("cart");
            orderService.createOrder(cart);

            session.setAttribute("cart", null); // Очищаем корзину
        }
        else {
            cart = cartProductService.getCart();
            orderService.createOrder(cart);

            cartProductService.removeCart(cart);
        }

        htmlMail(cart, email, path);

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
        while(in.hasNext()) htmlMsg += in.nextLine() + "\r\n";
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
            htmlMsg += "<td>" + cartProduct.getProduct().getRetailPrice() * cartProduct.getCount() + " " + cartProduct.getProduct().getCurrency() + "</td>";
            htmlMsg += "</tr>";
        }

        in = new Scanner(new File(path + "mailend.html"));
        while(in.hasNext()) htmlMsg += in.nextLine() + "\r\n";
        in.close();

        mimeMessage.setContent(htmlMsg, "text/html; charset=utf-8");

        mailSender.send(mimeMessage);
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
        table.setWidths(new float[] {2.0f, 4.0f, 2.0f, 2.0f});
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
