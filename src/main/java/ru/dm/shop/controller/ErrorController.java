package ru.dm.shop.controller;

/**
 * Created by Denis on 05.05.16.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorController {

    @RequestMapping(value = "/error404", method = RequestMethod.GET)
    public String error404() {
        return "redirect:/404";
    }

    @RequestMapping(value = "/error403", method = RequestMethod.GET)
    public String error403() {
        return "redirect:/403";
    }

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String e404() {
        return "404";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String e403() {
        return "403";
    }
}