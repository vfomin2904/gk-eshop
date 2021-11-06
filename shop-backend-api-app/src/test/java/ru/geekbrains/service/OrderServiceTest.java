package ru.geekbrains.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.ActiveProfiles;
import ru.geekbrains.controller.dto.OrderDto;
import ru.geekbrains.controller.dto.ProductDto;
import ru.geekbrains.persist.CategoryRepository;
import ru.geekbrains.persist.OrderRepository;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.UserRepository;
import ru.geekbrains.persist.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrderServiceTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private SimpMessagingTemplate template;

    @Autowired
    private OrderServiceImpl orderService;

    boolean init = false;

    @BeforeEach
    public void init() {
        if (init) {
            return;
        }
        Order order = new Order();
        User user = new User();
        user.setUsername("Ivan");
        order.setStatus(Order.OrderStatus.CREATED);
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        OrderLineItem orderLineItem = new OrderLineItem();
        Category category = new Category();
        category.setName("cat1");
        Picture picture = new Picture();
        picture.setName("main");
        picture.setStorageId("121212");
        picture.setContentType("jpg");
        Product product = new Product();
        product.setName("Product1");
        product.setPictures(Collections.singletonList(picture));
        product.setCategory(category);
        orderLineItem.setProduct(product);
        orderLineItem.setColor("red");
        orderLineItem.setMaterial("wood");
        orderLineItem.setQty(10);
        orderLineItem.setPrice(BigDecimal.ONE);
        order.setOrderLineItems(Collections.singletonList(orderLineItem));

        orderRepository.save(order);
        init = true;
    }

    @Test
    public void findOrdersByUsername() {
        List<OrderDto> searchedOrders = orderService.findOrdersByUsername("Ivan");
        Assertions.assertEquals(1, searchedOrders.size());
        Assertions.assertEquals("Ivan", searchedOrders.get(0).getUsername());
    }

    @Test
    public void findAll() {
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setPrice(BigDecimal.ONE);
        cartService.addProductQty( productDto, "red", "wood", 2);
        orderService.createOrder("Ivan");
        List<Order> orders = orderRepository.findAllByUsername("Ivan");
        Assertions.assertEquals(2, orders.size());
    }
}
