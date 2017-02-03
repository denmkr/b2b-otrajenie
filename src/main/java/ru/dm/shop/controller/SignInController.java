package ru.dm.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignInController {

    // @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signin(ModelMap model, @RequestParam(value = "error", required = false) boolean error) {
        if (error) model.addAttribute("error", error);
        return "signin";
    }


}