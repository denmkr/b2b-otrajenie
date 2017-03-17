package ru.dm.shop.controller;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.dm.shop.entity.SignUpForm;
import ru.dm.shop.entity.SignupConfirmation;
import ru.dm.shop.entity.User;
import ru.dm.shop.service.SignupConfirmationService;
import ru.dm.shop.service.UserRoleService;
import ru.dm.shop.service.UserService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.sql.Timestamp;

/**
 * Created by Denis on 25.04.16.
 */

@Controller
public class SignUpController {
    @Autowired
    UserService userService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    SignupConfirmationService signupConfirmationService;
    @Autowired
    JavaMailSender mailSender;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signupPage(ModelMap model) {

        model.addAttribute("signup_form", new SignUpForm());
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@ModelAttribute("signup_form") @Valid SignUpForm form, BindingResult result) {
        if (!result.hasErrors()) {
            User user = new User();
            user.setUsername(StringEscapeUtils.escapeHtml4(form.getUsername()));
            user.setEmail(StringEscapeUtils.escapeHtml4(form.getEmail()));
            BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder(12);
            user.setPassword(bcryptEncoder.encode(form.getPassword()));
            user.setEnabled(false);

            java.util.Date date = new java.util.Date();
            user.setDate(new Timestamp(date.getTime()));

            SignupConfirmation signupConfirmation = new SignupConfirmation();
            signupConfirmation.setCode(java.util.UUID.randomUUID().toString());
            signupConfirmation.setUser(user);

            String email = user.getEmail();

            userService.create(user);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper;
            try {
                helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
                helper.setSubject("Подтверждение регистрации на сайте 'Отражение'");
                helper.setFrom("webmaventest@gmail.com");
                helper.setTo(email);

                String htmlMsg = "";

                htmlMsg += "<p> Добро пожаловать на сайт «Отражение»! Подтвердите свой e-mail, пройдя по ссылке. </p>";

                htmlMsg += "<p><a href=\"http://localhost:8080/confirm_signup?code=" + signupConfirmation.getCode() + "\">Подтвердить</a></p>";
                htmlMsg += "<p>Внимание, если вы не регистрировались на сайте otrajenie, проигнорируйте данное письмо.</p>";

                System.out.println(htmlMsg);

                mimeMessage.setContent(htmlMsg, "text/html; charset=utf-8");

                mailSender.send(mimeMessage);
            } catch (MessagingException e) {
            }

            signupConfirmationService.create(signupConfirmation);
            userRoleService.createUser(user);

            return "redirect:/signup_success";
        } else {
            return "signup";
        }
    }

    @RequestMapping(value = "/signup_success", method = RequestMethod.GET)
    public String signupSuccess() {
        return "signup_success";
    }

    @RequestMapping(value = "/confirm_signup", method = RequestMethod.GET)
    public String confirmSignup(@RequestParam(value = "code", required = true) String code) {
        SignupConfirmation signupConfirmation = signupConfirmationService.findByCode(code);

        if (signupConfirmation != null) {
            User user = signupConfirmation.getUser();
            user.setEnabled(true);
            userService.update(user);
            signupConfirmationService.delete(signupConfirmation);
        }
        return "signup_confirm";
    }

}

