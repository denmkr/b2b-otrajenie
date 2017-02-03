package ru.dm.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.dm.shop.entity.Product;
import ru.dm.shop.modul.XMLParser;
import ru.dm.shop.service.GroupService;
import ru.dm.shop.service.ProductService;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Denis on 22/03/2016.
 */

@Controller
@RequestMapping("/")
public class UpdateDataController {

    @Autowired
    ProductService productService;
    @Autowired
    GroupService groupService;

    @RequestMapping(value = "database", method = RequestMethod.GET)
    public String database(ModelMap model, HttpServletRequest request) {

        return "database";
    }

    @RequestMapping(value = "admin/products/update", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public String completeData(ModelMap model, HttpServletRequest request) {
        XMLParser parser = new XMLParser(request);

        List<Product> productListFromDB = productService.findAll();

        groupService.updateGroups(parser.getProductGroups());
        productService.updateProducts(parser.getProducts());
        int test = parser.getProducts().size();
        productService.deleteMissing(parser.getProducts());
        groupService.addParentsGroupsToGroups(parser.getRelations());

        List<Product> newProductListFromDB = productService.findAll();
        System.out.println("В файле было товаров: " + test);
        System.out.println("В базе было товаров: " + productListFromDB.size());
        System.out.println("В базе стало товаров: " + newProductListFromDB.size());

        model.addAttribute("message", "Товары загружены");
        model.addAttribute("href", "/products/upload");
        return "admin/notif";
    }

}
