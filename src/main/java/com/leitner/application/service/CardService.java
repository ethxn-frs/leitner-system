package com.leitner.application.service;

import com.leitner.domain.model.Card;
import com.leitner.domain.model.Category;

import java.util.List;
import java.util.UUID;

public interface CardService {

    List<Card> getAllCards();

    Card getCardById(UUID id);

    List<Card> getCardsByCategory(Category category);

    List<Card> getCardsByTag(String tag);

    Card createCard(Card card);

    void deleteCard(UUID id);

    Card updateCardCategory(UUID id, Category newCategory);

    List<Card> getCardsForQuizz(String date);

    void answerCard(UUID id, boolean isValid);
}
