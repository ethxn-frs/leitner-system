package com.leitner.application.serviceImpl;

import com.leitner.application.service.CardService;
import com.leitner.domain.model.Card;
import com.leitner.domain.model.Category;
import com.leitner.domain.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    @Override
    public Card getCardById(UUID id) {
        return cardRepository.findById(id).orElse(null);
    }

    @Override
    public List<Card> getCardsByCategory(Category category) {
        return cardRepository.findByCategory(category);
    }

    @Override
    public List<Card> getCardsByTag(String tag) {
        return cardRepository.findByTag(tag);
    }

    @Override
    public Card createCard(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public void deleteCard(UUID id) {
        if (!cardRepository.existsById(id)) {
            throw new RuntimeException("Card not found");
        }
        cardRepository.deleteById(id);
    }

    @Override
    public Card updateCardCategory(UUID id, Category newCategory) {
        return cardRepository.findById(id)
                .map(card -> {
                    card.setCategory(newCategory);
                    return cardRepository.save(card);
                })
                .orElseThrow(() -> new RuntimeException("Card not found"));
    }

    @Override
    public List<Card> getCardsForQuizz(String date) {
        // TODO: Remplacer par une logique spécifique à la récupération des cartes de quizz
        return cardRepository.findAll();
    }

    @Override
    public void answerCard(UUID id, boolean isValid) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        if (isValid) {
            int nextCategoryIndex = Math.min(card.getCategory().ordinal() + 1, Category.values().length - 1);
            card.setCategory(Category.values()[nextCategoryIndex]);
        } else {
            card.setCategory(Category.FIRST);
        }

        cardRepository.save(card);
    }
}
