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
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartControllerTest {
    @Mock
    UserRepository userRepository;
    @Mock
    CartRepository cartRepository;
    @Mock
    ItemRepository itemRepository;
    @InjectMocks
    CartController cartController;

    MockMvc mockMvc;
    ModifyCartRequest modifyCartRequest;
    User user;
    Cart cart;
    Item item;

    @Before
    public void setUp(){
        cartController = new CartController(userRepository, cartRepository, itemRepository);

        mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();


        item = new Item();
        item.setId(1L);
        item.setName("ki-item");
        item.setDescription("ki description kya item");
        item.setPrice(BigDecimal.valueOf(23));


        List<Item> itemList = new LinkedList<>();
        itemList.add(item);

        cart = new Cart();
        cart.setId(1L);
        cart.setItems(itemList);

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
    public void addToCart() throws Exception{
//        Cart cartTOSave = new Cart();
//        cartTOSave.setId(2L);
//        when(userRepository.findByUsername(anyString())).thenReturn(user);
//        when(cartRepository.save(any())).thenReturn(cart);
//        ResponseEntity<Cart> cartResponseEntity = cartController.addTocart(modifyCartRequest);
//        Cart savedCart = cartResponseEntity.getBody();
//
//        assertNotNull(cart);
//        assertNotNull(cartResponseEntity);
//        assertNotNull(savedCart);
    }

    @Test
  public  void  deleteFromCart(){

    }
}
