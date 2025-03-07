package com.leitner.domain.repository;

import com.leitner.domain.model.Card;
import com.leitner.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Transactional
class CardRepositoryTest {

    @Autowired
    private CardRepository cardRepository;

    @BeforeEach
    void setUp() {
        cardRepository.deleteAll();
    }

    @Test
    void shouldSaveAndFindCard() {
        // Given
        Card card = new Card("Question?", "Answer", "Tag", Category.FIRST);

        // When
        card = cardRepository.save(card);
        Optional<Card> found = cardRepository.findById(card.getId());

        // Then
        assertTrue(found.isPresent());
        assertEquals(card.getId(), found.get().getId());
    }

    @Test
    void shouldReturnEmptyIfCardNotFound() {
        // Given
        UUID randomId = UUID.randomUUID();

        // When
        Optional<Card> found = cardRepository.findById(randomId);

        // Then
        assertFalse(found.isPresent());
    }

    @Test
    void shouldFindCardsByCategory() {
        // Given
        Card card1 = new Card("Q1", "A1", "Tag1", Category.FIRST);
        Card card2 = new Card("Q2", "A2", "Tag2", Category.SECOND);
        cardRepository.save(card1);
        cardRepository.save(card2);

        // When
        List<Card> firstCategoryCards = cardRepository.findByCategory(Category.FIRST);

        // Then
        assertEquals(1, firstCategoryCards.size());
        assertEquals(Category.FIRST, firstCategoryCards.get(0).getCategory());
    }

    @Test
    void shouldFindCardsByTag() {
        // Given
        Card card1 = new Card("Q1", "A1", "Tag1", Category.FIRST);
        Card card2 = new Card("Q2", "A2", "Tag2", Category.SECOND);
        cardRepository.save(card1);
        cardRepository.save(card2);

        // When
        List<Card> tag1Cards = cardRepository.findByTag("Tag1");

        // Then
        assertEquals(1, tag1Cards.size());
        assertEquals("Tag1", tag1Cards.get(0).getTag());
    }

    @Test
    void shouldDeleteCard() {
        // Given
        Card card = new Card("Question?", "Answer", "Tag", Category.FIRST);
        card = cardRepository.save(card);
        UUID cardId = card.getId();

        // When
        cardRepository.deleteById(cardId);
        Optional<Card> deletedCard = cardRepository.findById(cardId);

        // Then
        assertFalse(deletedCard.isPresent());
    }
}
