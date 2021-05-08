package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CartControllerTest {
    @Mock
    UserRepository userRepository;
    @Mock
    CartRepository cartRepository;
    @Mock
    ItemRepository itemRepository;
    @InjectMocks
    CartController cartController;

    ModifyCartRequest modifyCartRequest;
    User user;
    Cart cart;
    Item item;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);

        item = new Item();
        item.setId(1L);
        item.setName("ki-item");
        item.setDescription("ki description kya item");
        item.setPrice(BigDecimal.valueOf(23));



        cart = new Cart();
        cart.setId(1L);
        cart.addItem(item);

        user = new User();
        user.setId(1);
        user.setUsername("Muthee");
        user.setPassword("kapassword");
        user.setCart(cart);

        cart.setUser(user);

        modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(item.getId());
        modifyCartRequest.setUsername(user.getUsername());
        modifyCartRequest.setQuantity(1);





    }

    @Test
    public void addToCart() {
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));

        ResponseEntity<Cart> cartResponseEntity = cartController.addTocart(modifyCartRequest);
        Cart savedCart = cartResponseEntity.getBody();

        assertNotNull(cartResponseEntity);
        assertEquals(200, cartResponseEntity.getStatusCodeValue());

        assertNotNull(savedCart);
        assertEquals(user, savedCart.getUser());
        assertEquals(BigDecimal.valueOf(46), savedCart.getTotal());
    }

    @Test
    public  void removeFromCart(){
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));

        ResponseEntity<Cart> cartResponseEntity = cartController.removeFromcart(modifyCartRequest);
        Cart cartWithRemovedItem = cartResponseEntity.getBody();

        assertNotNull(cartResponseEntity);
        assertEquals(200, cartResponseEntity.getStatusCodeValue());

        assertNotNull(cartWithRemovedItem);
        assertEquals(0, cartWithRemovedItem.getItems().size());
        assertEquals(user, cartWithRemovedItem.getUser());
    }

    @Test
    public void userNameNotFound() {
        when(userRepository.findByUsername("Muthee")).thenReturn(user);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));

        modifyCartRequest.setUsername("test");

        final ResponseEntity<Cart> cartResponseEntity = cartController.addTocart(modifyCartRequest);
        assertNotNull(cartResponseEntity);
        assertEquals(404, cartResponseEntity.getStatusCodeValue());
    }

    @Test
    public void itemNotFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        modifyCartRequest.setItemId(2L);

        final ResponseEntity<Cart> cartResponseEntity = cartController.removeFromcart(modifyCartRequest);
        assertNotNull(cartResponseEntity);
        assertEquals(404, cartResponseEntity.getStatusCodeValue());
    }
}
