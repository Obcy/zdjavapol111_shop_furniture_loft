package com.loft.service.impl;

import com.loft.model.ShoppingCart;
import com.loft.model.User;
import com.loft.repository.ShoppingCartRepository;
import com.loft.service.ShoppingCartService;
import com.loft.service.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ShoppingCart create() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!username.equals("anonymousUser")) {
            User user = userService.findByEmailAddress(username);
            if (shoppingCartRepository.existsByUserId(user.getId())) {
                ShoppingCart shoppingCart = this.getByUserId(user.getId());
                return shoppingCart;
            } else {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                        .getRequestAttributes()).getRequest();
                HttpSession session = request.getSession();
                if (session.getAttribute("shoppingCartId") == null) {
                    ShoppingCart shoppingCart = new ShoppingCart();
                    shoppingCart.setUser(user);


                } else {
                    Integer shoppingCartId = (Integer) session.getAttribute("shoppingCartId");
                    ShoppingCart shoppingCart = this.get(shoppingCartId);
                    shoppingCart.setUser(user);
                    return shoppingCart;
                }

            }
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        if (session.getAttribute("shoppingCartId") == null) {
            ShoppingCart shoppingCart = new ShoppingCart();
            session.setAttribute("shoppingCartId", shoppingCart.getId());
            return shoppingCart;
        }
        Integer shoppingCartId = (Integer) session.getAttribute("shoppingCartId");
        return this.get(shoppingCartId);
    }

    @Override
    public ShoppingCart get(int id) {
        return shoppingCartRepository.findById(id).orElseThrow();
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);
        return shoppingCart;
    }

    @Override
    public void save(ShoppingCart shoppingCart) {
        shoppingCartRepository.save(shoppingCart);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("shoppingCartId", shoppingCart.getId());
    }

    @Override
    public boolean update(ShoppingCart shoppingCart) {
        return false;
    }


}
