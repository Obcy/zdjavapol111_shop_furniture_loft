package com.loft.controller;

import com.loft.currency.model.CurrencyRate;
import com.loft.currency.service.CurrencyRateService;
import com.loft.model.*;
import com.loft.service.OrderService;
import com.loft.service.ShoppingCartService;
import com.loft.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private CurrencyRateService currencyRateService;

    @GetMapping("/order/{orderKey}")
    public String showOrderWithIdAndKey(@PathVariable String orderKey, ModelMap modelMap) {

        Order order = orderService.findByOrderKey(orderKey);
        modelMap.addAttribute("order", order);
        modelMap.addAttribute("totalPrice", orderService.getTotal(order));

        addDefaultsToModelMap(modelMap);

        return "order";
    }

    @GetMapping("/order/new")
    public String newOrder(ModelMap modelMap) {

        Order order = new Order();
        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(BigDecimal.valueOf(159));
        orderItem.setProductName("Sofa");
        orderItem.setQuantity(2);
        order.getOrderItems().add(orderItem);

        orderService.save(order);

        modelMap.addAttribute("order", order);

        addDefaultsToModelMap(modelMap);

        return "order";
    }

    @PostMapping("/order/checkout")
    public String handleCreateNewOrder(@ModelAttribute("billingInfo")BillingInfo billingInfo
            , @ModelAttribute("deliveryInfo")DeliveryInfo deliveryInfo, @RequestParam(value = "sameAsDelivery", required = true) String sameAsDelivery) {

        if (sameAsDelivery.equals("on")) {
            deliveryInfo.setDeliveryName(billingInfo.getBillingName());
            deliveryInfo.setDeliveryCity(billingInfo.getBillingCity());
            deliveryInfo.setDeliveryPostalCode(billingInfo.getBillingPostalCode());
            deliveryInfo.setDeliveryStreet(billingInfo.getBillingStreet());
            deliveryInfo.setDeliveryHouseNumber(billingInfo.getBillingHouseNumber());
            deliveryInfo.setDeliveryFlatNumber(billingInfo.getBillingFlatNumber());
            deliveryInfo.setDeliveryCountry(billingInfo.getBillingCountry());
        }

        Order order = new Order();
        ShoppingCart shoppingCart = shoppingCartService.createOrGet();
        order.setOrderItems(shoppingCart.getCartItems().stream().map(shoppingCartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setQuantity(shoppingCartItem.getQuantity());
                    orderItem.setProductName(shoppingCartItem.getProduct().getTitle());
                    orderItem.setPrice(shoppingCartItem.getProduct().getPrice());
                    return orderItem;  })
                .collect(Collectors.toSet()));
        order.setBillingInfo(billingInfo);
        order.setDeliveryInfo(deliveryInfo);
        order.setOrderStatus(OrderStatus.NEW);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {

            User user = userService.findByEmailAddress(authentication.getName());
            order.setUser(user);
        }

        orderService.save(order);

        shoppingCartService.delete(shoppingCart);



        return "redirect:/order/" + order.getOrderKey();
    }

    private void addDefaultsToModelMap(ModelMap modelMap) {
        modelMap.addAttribute("displayCurrency", currencyRateService.getDisplayCurrency());
        List<CurrencyRate> currencyRates = currencyRateService.getCurrentRateByDate(LocalDate.now());
        List<String> options = currencyRates.stream().map(CurrencyRate::getCode).collect(Collectors.toList());
        modelMap.addAttribute("options", options);
    }


}
