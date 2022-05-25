package com.loft.service.impl;

import com.loft.model.Product;
import com.loft.model.ShoppingCart;
import com.loft.model.ShoppingCartItem;
import com.loft.model.User;
import com.loft.repository.ShoppingCartRepository;
import com.loft.service.ShoppingCartService;
import com.loft.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@Slf4j
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
    public ShoppingCart createOrGet() {
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
        shoppingCart = getById(shoppingCartId);
        return shoppingCart;
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
                shoppingCart = this.getById(shoppingCartId);
            }
            shoppingCart.setUser(user);
            return shoppingCart;
        }
    }

    @Override
    public ShoppingCart getById(int id) {
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
        if (shoppingCart == null) { shoppingCart = createOrGet(); }
        shoppingCart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct() == product)
                .findFirst()
                .ifPresentOrElse(cartItem -> {
                    cartItem.setQuantity(cartItem.getQuantity() + 1);

                }, () -> {
                    ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
                    shoppingCartItem.setProduct(product);
                    shoppingCartItem.setQuantity(1);
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
                        shoppingCart.getCartItems().add(shoppingCartItem);
                    });
        }
    }

    @Override
    public void changeProductByIdQuantity(int id, int quantity) {

            shoppingCart.getCartItems().stream()
                    .filter(cartItem -> cartItem.getProduct().getId() == id)
                    .findFirst()
                    .ifPresent(cartItem -> this.changeProductQuantity(cartItem.getProduct(), quantity));

    }

    @Override
    public BigDecimal getTotal() {
        return shoppingCart.getCartItems().stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public void delete(ShoppingCart shoppingCart) {
        shoppingCartRepository.delete(shoppingCart);
    }
}
