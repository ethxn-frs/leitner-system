package com.leitner.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leitner.application.service.CardService;
import com.leitner.domain.model.Card;
import com.leitner.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CardControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private CardService cardService;
    @InjectMocks
    private CardController cardController;
    private MockMvc mockMvc;

    private UUID testId;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cardController).build();
        testId = UUID.randomUUID();
    }

    @Test
    void shouldReturnAllCards() throws Exception {
        when(cardService.getAllCards()).thenReturn(List.of(new Card(testId, "Q1", "A1", "Tag1", Category.FIRST)));

        mockMvc.perform(get("/cards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testId.toString()))
                .andExpect(jsonPath("$[0].question").value("Q1"))
                .andDo(print());

        verify(cardService, times(1)).getAllCards();
    }

    @Test
    void shouldCreateCard() throws Exception {
        Card card = new Card(testId, "New Question", "New Answer", "New Tag", Category.FIRST);
        when(cardService.createCard(any(Card.class))).thenReturn(card);

        mockMvc.perform(post("/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(card)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(testId.toString()))
                .andExpect(jsonPath("$.question").value("New Question"))
                .andDo(print());

        verify(cardService, times(1)).createCard(any(Card.class));
    }

    @Test
    void shouldReturnCardsForQuizz() throws Exception {
        when(cardService.getCardsForQuizz(anyString()))
                .thenReturn(List.of(new Card(testId, "Quiz Q1", "Quiz A1", "Tag1", Category.FIRST)));

        mockMvc.perform(get("/cards/quizz?date=2024-02-03"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testId.toString()))
                .andExpect(jsonPath("$[0].question").value("Quiz Q1"))
                .andDo(print());

        verify(cardService, times(1)).getCardsForQuizz(anyString());
    }

    @Test
    void shouldAnswerCard() throws Exception {
        doNothing().when(cardService).answerCard(testId, true);

        mockMvc.perform(patch("/cards/" + testId + "/answer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("true"))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(cardService, times(1)).answerCard(testId, true);
    }
}
