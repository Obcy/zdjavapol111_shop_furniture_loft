package com.loft.service.impl;

import com.loft.currency.model.CurrencyRate;
import com.loft.currency.service.CurrencyRateService;
import com.loft.model.*;
import com.loft.repository.ShoppingCartRepository;
import com.loft.repository.UserRepository;
import com.loft.service.ShoppingCartService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceImplTest {

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private CurrencyRateService currencyRateService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private HttpSession httpSession;

    private final User user = new User();

    @Before("createOrGetShouldReturnNewShoppingCartForLoggedUser")
    public void init() {
        user.setEmailAddress("test@test.pl");
        user.setId(1);
        Role role = new Role();
        role.setName("USER");
        role.setId(0);
        user.setRoles(List.of(role));
    }

    @Test
    void createOrGetShouldReturnNewShoppingCartForAnonymousUser() {
        //given
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("anonymousUser");
        //when
        ShoppingCart result = shoppingCartService.createOrGet();
        //then
        assertThat(result).isNotNull();
        assertThat(result.getUser()).isNull();
    }

    @Test
    void createOrGetShouldReturnNewShoppingCartForLoggedUser() {
        //given
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("test@test.pl");
        Mockito.when(userService.findByEmailAddress("test@test.pl")).thenReturn(user);

        //when
        ShoppingCart result = shoppingCartService.createOrGet();
        //then
        assertThat(result).isNotNull();
        assertThat(result.getUser()).isEqualTo(user);
    }

    @Test
    void getByIdShouldReturnProperCartOrNewIfNotExists() {
        //given
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(99);
        Mockito.when(shoppingCartRepository.findById(99)).thenReturn(Optional.of(shoppingCart));
        Mockito.when(shoppingCartRepository.findById(100)).thenReturn(Optional.empty());

        //when
        ShoppingCart result = shoppingCartService.getById(99);
        ShoppingCart newResult = shoppingCartService.getById(100);
        //then
        assertThat(result.getId()).isEqualTo(99);
        assertThat(newResult).isNotNull();

    }

    @Test
    void shouldAddNewProductToCart() {
        //given
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("anonymousUser");

        Product product = new Product();
        product.setTitle("test");
        product.setPrice(BigDecimal.ONE);
        //when
        ShoppingCart shoppingCart = shoppingCartService.createOrGet();
        shoppingCartService.addProduct(product);
        shoppingCartService.addProduct(product);
        Set<ShoppingCartItem> cartItems = shoppingCart.getCartItems();
        //then
        assertThat(cartItems.size()).isEqualTo(1);
        assertThat(cartItems.iterator().next().getProduct()).isEqualTo(product);
        assertThat(cartItems.iterator().next().getQuantity()).isEqualTo(2);

    }

    @Test
    void shouldRemoveProductFromCart() {
        //given
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("anonymousUser");

        Product product = new Product();
        product.setTitle("test");
        product.setPrice(BigDecimal.ONE);
        //when
        ShoppingCart shoppingCart = shoppingCartService.createOrGet();
        shoppingCartService.addProduct(product);
        shoppingCartService.removeProduct(product);
        Set<ShoppingCartItem> cartItems = shoppingCart.getCartItems();
        //then
        assertThat(cartItems.size()).isEqualTo(0);

    }

    @Test
    void shouldChangeProductQuantityInCart() {
        //given
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("anonymousUser");

        Product product = new Product();
        product.setTitle("test");
        product.setPrice(BigDecimal.ONE);
        //when
        ShoppingCart shoppingCart = shoppingCartService.createOrGet();
        shoppingCartService.addProduct(product);
        shoppingCartService.changeProductQuantity(product, 3);
        Set<ShoppingCartItem> cartItems = shoppingCart.getCartItems();
        //then
        assertThat(cartItems.iterator().next().getProduct()).isEqualTo(product);
        assertThat(cartItems.iterator().next().getQuantity()).isEqualTo(3);

    }

    @Test
    void shouldChangeProductQuantityByProductIdInCart() {
        //given
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("anonymousUser");

        Product product = new Product();
        product.setTitle("product");
        product.setPrice(BigDecimal.ONE);
        product.setId(1);
        Product product2 = new Product();
        product2.setTitle("second product");
        product2.setPrice(BigDecimal.ONE);
        product2.setId(2);
        //when
        ShoppingCart shoppingCart = shoppingCartService.createOrGet();
        shoppingCartService.addProduct(product);
        shoppingCartService.addProduct(product2);
        shoppingCartService.changeProductQuantityById(product.getId(), 3);
        Set<ShoppingCartItem> cartItems = shoppingCart.getCartItems();
        //then
        assertThat(cartItems.stream().filter(item -> item.getProduct() == product).findFirst().get().getQuantity()).isEqualTo(3);
        assertThat(cartItems.stream().filter(item -> item.getProduct() == product2).findFirst().get().getQuantity()).isEqualTo(1);

    }

    @Test
    void shouldReturnProperTotalCartItemsPrice() {
        //given
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("anonymousUser");
        Mockito.when(currencyRateService.getDisplayCurrency()).thenReturn( "PLN" );

        Product product = new Product();
        product.setTitle("product");
        product.setPrice(BigDecimal.ONE);
        product.setId(1);
        Product product2 = new Product();
        product2.setTitle("second product");
        product2.setPrice(BigDecimal.TEN);
        product2.setId(2);
        //when
        ShoppingCart shoppingCart = shoppingCartService.createOrGet();
        shoppingCartService.addProduct(product);
        shoppingCartService.addProduct(product);
        shoppingCartService.addProduct(product2);


        //then
        assertThat(shoppingCartService.getTotal()).isEqualTo(BigDecimal.valueOf(12));

    }
}