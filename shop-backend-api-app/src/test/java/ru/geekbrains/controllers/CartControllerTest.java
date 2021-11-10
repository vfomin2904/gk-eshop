package ru.geekbrains.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.geekbrains.controller.dto.AddLineItemDto;
import ru.geekbrains.service.CartService;
import ru.geekbrains.service.ProductService;

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

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private SimpMessagingTemplate template;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void addToCart() throws Exception {
        AddLineItemDto addLineItemDto = new AddLineItemDto();
        addLineItemDto.setColor("red");
        addLineItemDto.setMaterial("wood");
        addLineItemDto.setProductId(1L);
        addLineItemDto.setQty(10);
        System.out.println(objectMapper.writeValueAsString(addLineItemDto));

        mvc.perform(MockMvcRequestBuilders
        .post("/cart")
        .contentType(MediaType.APPLICATION_JSON)
        .param("addLineItemDto", objectMapper.writeValueAsString(addLineItemDto)))
                .andExpect(status().isForbidden());
    }
}
