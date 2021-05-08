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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
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

    List<Item> itemList;

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

        itemList = new LinkedList<>();
        itemList.add(item);
        itemList.add(itemIngine);

    }

    @Test
    public void submit() {
        when(userRepository.findByUsername(anyString())).thenReturn(user);

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

    @Test
    public void getOrdersForUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        List<UserOrder> userOrders = createOrderHistory(user);
        when(orderRepository.findByUser(user)).thenReturn(userOrders);

        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("Muthee");
        List<UserOrder> testUserOrders = response.getBody();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        assertNotNull(testUserOrders);
        assertArrayEquals(userOrders.toArray(), testUserOrders.toArray());

        verify(userRepository, times(1)).findByUsername("Muthee");
        verify(orderRepository, times(1)).findByUser(user);

    }


    /**
     * convenience method for creating user orders
     */
    private List<UserOrder> createOrderHistory(User user) {
        UserOrder firstOrder = new UserOrder();
        firstOrder.setId(1L);
        firstOrder.setItems(itemList);
        firstOrder.setUser(user);
        firstOrder.setTotal(BigDecimal.valueOf(12 + 23));

        UserOrder secondOrder = new UserOrder();
        secondOrder.setId(2L);
        secondOrder.setItems(itemList);
        secondOrder.setUser(user);
        secondOrder.setTotal(BigDecimal.valueOf(12 + 23));

        List<UserOrder> userOrders = new ArrayList<>();
        userOrders.add(firstOrder);
        userOrders.add(secondOrder);

        return userOrders;
    }

}
