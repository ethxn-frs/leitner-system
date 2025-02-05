package com.leitner.application.serviceImpl;

import com.leitner.domain.model.Card;
import com.leitner.domain.model.Category;
import com.leitner.domain.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Transactional
class CardServiceImplTest {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardServiceImpl cardService;

    private UUID testId;

    @BeforeEach
    void setUp() {
        Card testCard = new Card("Sample Question", "Sample Answer", "Sample Tag", Category.FIRST);
        testCard = cardRepository.save(testCard);
        testId = testCard.getId();
    }

    @Test
    void shouldReturnAllCards() {
        // When
        List<Card> cards = cardService.getAllCards();

        // Then
        assertFalse(cards.isEmpty());
        assertEquals(4, cards.size());
    }

    @Test
    void shouldReturnCardById() {
        // When
        Card card = cardService.getCardById(testId);

        // Then
        assertNotNull(card);
        assertEquals(testId, card.getId());
    }

    @Test
    void shouldCreateCard() {
        // Given
        Card newCard = new Card("New Question", "New Answer", "New Tag", Category.SECOND);

        // When
        Card createdCard = cardService.createCard(newCard);

        // Then
        assertNotNull(createdCard);
        assertNotNull(createdCard.getId());
        assertEquals("New Question", createdCard.getQuestion());
    }

    @Test
    void shouldDeleteCard() {
        // When
        cardService.deleteCard(testId);

        // Then
        assertFalse(cardRepository.existsById(testId));
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentCard() {
        // Given
        UUID randomId = UUID.randomUUID();

        // When & Then
        assertThrows(RuntimeException.class, () -> cardService.deleteCard(randomId));
    }

    @Test
    void shouldUpdateCardCategory() {
        // When
        Card updatedCard = cardService.updateCardCategory(testId, Category.SECOND);

        // Then
        assertNotNull(updatedCard);
        assertEquals(Category.SECOND, updatedCard.getCategory());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentCard() {
        // Given
        UUID randomId = UUID.randomUUID();

        // When & Then
        assertThrows(RuntimeException.class, () -> cardService.updateCardCategory(randomId, Category.SECOND));
    }
}
