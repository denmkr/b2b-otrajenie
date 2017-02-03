package ru.dm.shop.controller;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.dm.shop.entity.Product;
import ru.dm.shop.service.CartProductService;
import ru.dm.shop.service.GroupService;
import ru.dm.shop.service.ProductService;
import ru.dm.shop.service.UserService;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


/**
 * Created by Denis on 22/03/2016.
 */

@Controller
@RequestMapping("/")
public class CatalogController {

    @Autowired
    CartProductService cartProductService;
    @Autowired
    ProductService productService;
    @Autowired
    GroupService groupService;
    @Autowired
    UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String catalogPageFromHome(ModelMap model) {
        return "redirect:/catalog/page/1";
    }

    @RequestMapping(value = "catalog", method = RequestMethod.GET)
    public String catalogPage(ModelMap model) {
        return "redirect:/catalog/page/1";
    }

    @RequestMapping(value = "catalog/page/{page}", method = RequestMethod.GET)
    public String catalog(ModelMap model, @RequestParam(value = "stock", required = false, defaultValue = "off") String stock,
                          @RequestParam(value = "sort", required = false, defaultValue = "name_ASC") String sort,
                          @RequestParam(value = "groupId", required = false, defaultValue = "") String groupId,
                          @RequestParam(value = "search", required = false, defaultValue = "") String search,
                          @RequestParam(value = "ajax", required = false, defaultValue = "0") String ajax,
                          @RequestParam(value = "mode", required = false, defaultValue = "none") String mode,
                          @RequestParam(value = "count", required = false, defaultValue = "20") String count,
                          @PathVariable(value = "page") int page) throws UnsupportedEncodingException {

        if (mode.equals("all")) {
            groupId = "";
        }

        Page<Product> products = productService.findAll(groupId, page, stock, StringEscapeUtils.escapeHtml4(search), StringEscapeUtils.escapeHtml4(search), sort, Integer.parseInt(count));

        // Page<Product> products = productService.findAll(groupId, page, stock, StringEscapeUtils.escapeHtml4(new String(search.getBytes("ISO-8859-1"), "UTF-8")), StringEscapeUtils.escapeHtml4(new String(search.getBytes("ISO-8859-1"), "UTF-8")), sort, Integer.parseInt(count));

        int current = products.getNumber() + 1;
        int begin = Math.max(1, current - 3);
        int end = Math.min(begin + 5, products.getTotalPages());

        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);

        model.addAttribute("currentGroup", groupService.findByGroupId(groupId));

        model.addAttribute("products", products);

        model.addAttribute("groups", groupService.findAllParents());

        // cartProductService.getCart().getSizeByArticule();

        if (ajax.equals("1")) return "ajax/catalog_content";
        else return "catalog";
    }

}
