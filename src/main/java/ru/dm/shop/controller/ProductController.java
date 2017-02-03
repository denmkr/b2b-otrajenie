package ru.dm.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.dm.shop.entity.Product;
import ru.dm.shop.service.ProductService;

/**
 * Created by Denis on 22/03/2016.
 */

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/product/{articule}", method = RequestMethod.GET)
    public String product(ModelMap model, @PathVariable(value = "articule") String articule) {
        Product product = productService.findByArticule(articule);
        model.addAttribute("product", product);

        return "ajax/product_content";
    }

}
