package com.loft.controller;

import com.loft.currency.model.CurrencyRate;
import com.loft.currency.service.CurrencyRateService;
import com.loft.model.Order;
import com.loft.model.User;
import com.loft.service.AutoLoginService;
import com.loft.service.OrderService;
import com.loft.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private CurrencyRateService currencyRateService;

    @Autowired
    private AutoLoginService autologinService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/login")
    public String login(ModelMap modelMap) {

        addDefaultsToModelMap(modelMap);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/login";
    }


    @GetMapping(path = "/registration")
    public ModelAndView showRegistrationForm() {
        ModelAndView modelAndView = new ModelAndView("registration");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }


    @PostMapping(path = "/register")
    public String handleNewUser(@ModelAttribute User user) {

        if (userService.existsByEmailAddress(user.getEmailAddress())) {
            return "registration";
        }
        userService.save(user);
        autologinService.autologin(user.getEmailAddress());

        return "redirect:/panel";

    }

    @GetMapping("/panel")
    String showUserPanel(ModelMap modelMap) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "You are not allowed to view this page."
            );
        }
        addDefaultsToModelMap(modelMap);
        String currentUser = authentication.getName();

        User user = userService.findByEmailAddress(currentUser);


        log.info("user: " + user.getId());

        modelMap.addAttribute("userName", currentUser);
        List<Order> orders = orderService.findByUserId(user.getId());

        modelMap.addAttribute("orders", orders);

        return "panel";
    }

    private void addDefaultsToModelMap(ModelMap modelMap) {
        modelMap.addAttribute("displayCurrency", currencyRateService.getDisplayCurrency());
        List<CurrencyRate> currencyRates = currencyRateService.getCurrentRateByDate(LocalDate.now());
        List<String> options = currencyRates.stream().map(CurrencyRate::getCode).collect(Collectors.toList());
        modelMap.addAttribute("options", options);
    }


}
