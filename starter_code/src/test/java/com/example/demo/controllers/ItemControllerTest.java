package com.example.demo.controllers;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    @Mock
    ItemRepository itemRepository;

    @InjectMocks
    ItemController itemController;

    List<Item> items;
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        Item item = new Item();
        item.setId(1L);
        item.setName("itemYaKwanza");
        item.setDescription("ki description tu");
        item.setPrice(BigDecimal.valueOf(12));

        Item itemIngine = new Item();
        itemIngine.setId(2L);
        itemIngine.setName("itemYaPili");
        itemIngine.setDescription("ki description tu");
        itemIngine.setPrice(BigDecimal.valueOf(23));

        items = new LinkedList<>();
        items.add(item);
        items.add(itemIngine);
    }

    @Test
    public void getItems() {
        when(itemRepository.findAll()).thenReturn(items);

        ResponseEntity<List<Item>> itemsResponseEntity = itemController.getItems();
        List<Item> testList = itemsResponseEntity.getBody();

        assertNotNull(itemsResponseEntity);
        assertEquals(200, itemsResponseEntity.getStatusCodeValue());

        assertNotNull(testList);
        assertArrayEquals(items.toArray(), testList.toArray());
    }
    
}
