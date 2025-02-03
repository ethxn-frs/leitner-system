package com.leitner.application.serviceImpl;

import com.leitner.domain.model.Card;
import com.leitner.domain.model.Category;
import com.leitner.domain.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardServiceImpl cardService;

    private Card testCard;

    @BeforeEach
    void setUp() {
        testCard = new Card(1, "Sample Question", "Sample Answer", "Sample Tag", Category.FIRST);
    }

    @Test
    void shouldReturnAllCards() {
        // Given
        when(cardRepository.findAll()).thenReturn(List.of(testCard));

        // When
        List<Card> cards = cardService.getAllCards();

        // Then
        assertEquals(1, cards.size());
        verify(cardRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnCardById() {
        // Given
        when(cardRepository.findById(1)).thenReturn(Optional.of(testCard));

        // When
        Optional<Card> card = cardService.getCardById(1);

        // Then
        assertTrue(card.isPresent());
        assertEquals(testCard.getId(), card.get().getId());
        verify(cardRepository, times(1)).findById(1);
    }

    @Test
    void shouldCreateCard() {
        // Given
        when(cardRepository.save(testCard)).thenReturn(testCard);

        // When
        Card createdCard = cardService.createCard(testCard);

        // Then
        assertNotNull(createdCard);
        assertEquals(testCard.getId(), createdCard.getId());
        verify(cardRepository, times(1)).save(testCard);
    }

    @Test
    void shouldDeleteCard() {
        // Given
        doNothing().when(cardRepository).deleteById(1);

        // When
        cardService.deleteCard(1);

        // Then
        verify(cardRepository, times(1)).deleteById(1);
    }

    @Test
    void shouldUpdateCardCategory() {
        // Given
        when(cardRepository.findById(1)).thenReturn(Optional.of(testCard));
        when(cardRepository.save(any(Card.class))).thenReturn(testCard);

        // When
        Card updatedCard = cardService.updateCardCategory(1, Category.SECOND);

        // Then
        assertNotNull(updatedCard);
        assertEquals(Category.SECOND, updatedCard.getCategory());
        verify(cardRepository, times(1)).findById(1);
        verify(cardRepository, times(1)).save(any(Card.class));
    }
}

