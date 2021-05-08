package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderControllerTest {

    @Mock
    UserRepository userRepository;

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderController orderController;


    private User user;
    private Cart cart;
    Item item;
    Item itemIngine;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        user = new User();
        user.setUsername("Muthee");

        cart = new Cart();
        cart.setUser(user);

        item = new Item();
        item.setId(1L);
        item.setName("itemYaKwanza");
        item.setDescription("ki description tu");
        item.setPrice(BigDecimal.valueOf(12));
        cart.addItem(item);

        itemIngine = new Item();
        itemIngine.setId(2L);
        itemIngine.setName("itemYaPili");
        itemIngine.setDescription("ki description tu");
        itemIngine.setPrice(BigDecimal.valueOf(23));
        cart.addItem(itemIngine);

        user.setCart(cart);


    }

    @Test
    public void submit() {
        when(userRepository.findByUsername("Muthee")).thenReturn(user);
        
        ResponseEntity<UserOrder> userOrderResponseEntity = orderController.submit("Muthee");
        UserOrder testUserOrder = userOrderResponseEntity.getBody();

        assertNotNull(userOrderResponseEntity);
        assertEquals(200, userOrderResponseEntity.getStatusCodeValue());

        assertNotNull(testUserOrder);
        assertEquals(2, testUserOrder.getItems().size());
        assertEquals(user, testUserOrder.getUser());
        assertEquals(BigDecimal.valueOf(35), testUserOrder.getTotal());

        verify(userRepository).findByUsername("Muthee");
        verify(orderRepository).save(any(UserOrder.class));
    }


}
