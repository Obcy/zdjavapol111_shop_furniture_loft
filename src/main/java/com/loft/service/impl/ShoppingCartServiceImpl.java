package com.loft.service.impl;

import com.loft.model.Product;
import com.loft.model.ShoppingCart;
import com.loft.model.ShoppingCartItem;
import com.loft.model.User;
import com.loft.repository.ShoppingCartRepository;
import com.loft.service.ShoppingCartService;
import com.loft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Objects;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private UserService userService;

    private ShoppingCart shoppingCart;

    @Autowired
    private HttpSession httpSession;

    @Override
    public ShoppingCart create() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!username.equals("anonymousUser")) {
            return getShoppingCartByUser(username);
        }
        return getShoppingCart();
    }

    private ShoppingCart getShoppingCart() {
        if (httpSession.getAttribute("shoppingCartId") == null) {
            shoppingCart = new ShoppingCart();
            return shoppingCart;
        }
        Integer shoppingCartId = (Integer) httpSession.getAttribute("shoppingCartId");
        return this.get(shoppingCartId);
    }

    private ShoppingCart getShoppingCartByUser(String username) {
        User user = userService.findByEmailAddress(username);
        if (shoppingCartRepository.existsByUserId(user.getId())) {
            shoppingCart = this.getByUserId(user.getId());
            return shoppingCart;
        } else {
            if (httpSession.getAttribute("shoppingCartId") == null) {
                shoppingCart = new ShoppingCart();
            } else {
                Integer shoppingCartId = (Integer) httpSession.getAttribute("shoppingCartId");
                shoppingCart = this.get(shoppingCartId);
            }
            shoppingCart.setUser(user);
            return shoppingCart;
        }
    }

    @Override
    public ShoppingCart get(int id) {
        return shoppingCartRepository.findById(id).orElse(new ShoppingCart());
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        return shoppingCartRepository.findByUserId(userId);
    }

    @Override
    public void save(ShoppingCart shoppingCart) {
        shoppingCartRepository.save(shoppingCart);
        httpSession.setAttribute("shoppingCartId", shoppingCart.getId());
    }

    @Override
    public void addProduct(Product product) {
        shoppingCart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct() == product)
                .findFirst()
                .ifPresentOrElse(cartItem -> {
                    cartItem.setQuantity(cartItem.getQuantity() + 1);
                    cartItem.setTotalItemPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));

                }, () -> {
                    ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
                    shoppingCartItem.setProduct(product);
                    shoppingCartItem.setQuantity(1);
                    shoppingCartItem.setTotalItemPrice(product.getPrice().multiply(BigDecimal.valueOf(shoppingCartItem.getQuantity())));
                    shoppingCart.getCartItems().add(shoppingCartItem);
                });
    }

    @Override
    public void removeProduct(Product product) {
        shoppingCart.getCartItems().removeIf(cartItem -> cartItem.getProduct() == product);
    }

    @Override
    public void changeProductQuantity(Product product, int quantity) {
        if (quantity <= 0) {
            this.removeProduct(product);
        } else {
            shoppingCart.getCartItems().stream()
                    .filter(cartItem -> cartItem.getProduct() == product)
                    .findFirst()
                    .ifPresentOrElse(cartItem -> cartItem.setQuantity(quantity), () -> {
                        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
                        shoppingCartItem.setProduct(product);
                        shoppingCartItem.setQuantity(quantity);
                        shoppingCartItem.setTotalItemPrice(product.getPrice().multiply(BigDecimal.valueOf(shoppingCartItem.getQuantity())));
                        shoppingCart.getCartItems().add(shoppingCartItem);
                    });
        }
    }

    @Override
    public BigDecimal getTotal() {
        return shoppingCart.getCartItems().stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
