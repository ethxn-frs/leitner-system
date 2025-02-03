package com.leitner.domain.repository;

import com.leitner.domain.model.Card;
import com.leitner.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
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
        Card card = new Card(1, "Question?", "Answer", "Tag", Category.FIRST);

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
        Integer randomId = (1);

        // When
        Optional<Card> found = cardRepository.findById(1);

        // Then
        assertTrue(found.isEmpty());
    }

    @Test
    void shouldFindCardsByCategory() {
        // Given
        Card card1 = new Card(1, "Q1", "A1", "Tag1", Category.FIRST);
        Card card2 = new Card(2, "Q2", "A2", "Tag2", Category.SECOND);
        cardRepository.save(card1);
        cardRepository.save(card2);

        // When
        List<Card> firstCategoryCards = cardRepository.findByCategory(Category.FIRST);

        // Then
        assertEquals(1, firstCategoryCards.size());
        assertEquals(Category.FIRST, firstCategoryCards.getFirst().getCategory());
    }

    @Test
    void shouldFindCardsByTag() {
        // Given
        Card card1 = new Card(1, "Q1", "A1", "Tag1", Category.FIRST);
        Card card2 = new Card(2, "Q2", "A2", "Tag2", Category.SECOND);
        cardRepository.save(card1);
        cardRepository.save(card2);

        // When
        List<Card> firstCategoryCards = cardRepository.findByTag("Tag1");

        // Then
        assertEquals(1, firstCategoryCards.size());
        assertEquals(Category.FIRST, firstCategoryCards.getFirst().getCategory());
    }

    @Test
    void shouldDeleteCard() {
        // Given
        Card card = new Card(1, "Question?", "Answer", "Tag", Category.FIRST);
        card = cardRepository.save(card);

        // When
        cardRepository.deleteById(card.getId());
        Optional<Card> deletedCard = cardRepository.findById(card.getId());

        // Then
        assertTrue(deletedCard.isEmpty());
    }
}
