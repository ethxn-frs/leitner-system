package com.leitner.application.service;

import com.leitner.domain.model.Card;
import com.leitner.domain.model.Category;

import java.util.List;
import java.util.Optional;

public interface CardService {

    List<Card> getAllCards();

    Optional<Card> getCardById(Integer id);

    List<Card> getCardsByCategory(Category category);

    List<Card> getCardsByTag(String tag);

    Card createCard(Card card);

    void deleteCard(Integer id);

    Card updateCardCategory(Integer id, Category newCategory);
}
