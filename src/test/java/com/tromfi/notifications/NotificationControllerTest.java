package com.tromfi.notifications.adapters.in.rest;

import com.tromfi.notifications.adapters.in.rest.dto.NotificationRequestDTO;
import com.tromfi.notifications.adapters.in.rest.mapper.NotificationMapper;
import com.tromfi.notifications.application.usecase.notification.NotificationService;
import com.tromfi.notifications.application.usecase.notification.NotificationServiceImpl;
import com.tromfi.notifications.application.usecase.notification.command.CreateNotificationCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    private NotificationService notificationService;

    NotificationRequestDTO notificationRequestDTO;

//    NotificationServiceImpl notificationServiceImpl;
//
    @BeforeEach
    void setUp() {
        notificationRequestDTO = new NotificationRequestDTO("content", "LOW",
                "someone@gmail.com", "EMAIL");
    }

    @Test
    public void SuccessControllerRequest() throws Exception {

        //NotificationRequestDTO

        CreateNotificationCommand notificationCommand = NotificationMapper.MAPPER.
                toCreateNotificationCommand(notificationRequestDTO);

        //given(notificationService.sendNotification(notificationCommand));

        mockMvc.perform(post("/notification", notificationRequestDTO)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        //verify()
    }

}