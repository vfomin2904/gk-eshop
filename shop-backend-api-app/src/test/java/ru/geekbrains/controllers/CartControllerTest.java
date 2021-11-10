package ru.geekbrains.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.geekbrains.controller.dto.AddLineItemDto;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.model.Category;
import ru.geekbrains.persist.model.Product;
import ru.geekbrains.service.CartService;
import ru.geekbrains.service.ProductService;
import org.springframework.security.test.context.support.WithMockUser;
import ru.geekbrains.service.dto.LineItem;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class CartControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    CartService cartService;

    @Autowired
    ProductRepository productRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private SimpMessagingTemplate template;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void addToCartForriben() throws Exception {
        AddLineItemDto addLineItemDto = new AddLineItemDto();
        addLineItemDto.setColor("red");
        addLineItemDto.setMaterial("wood");
        addLineItemDto.setProductId(1L);
        addLineItemDto.setQty(10);
        System.out.println(objectMapper.writeValueAsString(addLineItemDto));

        mvc.perform(MockMvcRequestBuilders
        .post("/cart")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(addLineItemDto)))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(value = "admin", password = "admin", roles = {"ADMIN"})
    @Test
    public void addToCart() throws Exception {
        Product product = new Product();
        product.setName("Test product");
        product.setId(1L);
        product.setCategory(new Category(1L, "test"));
        productRepository.save(product);

        AddLineItemDto addLineItemDto = new AddLineItemDto();
        addLineItemDto.setColor("red");
        addLineItemDto.setMaterial("wood");
        addLineItemDto.setProductId(1L);
        addLineItemDto.setQty(10);

        mvc.perform(MockMvcRequestBuilders
                .post("/cart")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addLineItemDto)))
                .andExpect(status().isOk());

        LineItem item = cartService.getLineItems().stream().filter(lineItem -> lineItem.getProductId() == 1L).findFirst().orElse(null);

        Assertions.assertNotNull(item);
    }
}
