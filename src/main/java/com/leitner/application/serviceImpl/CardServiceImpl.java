package com.leitner.application.serviceImpl;

import com.leitner.application.service.CardService;
import com.leitner.domain.model.Card;
import com.leitner.domain.model.Category;
import com.leitner.domain.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    @Override
    public Optional<Card> getCardById(Integer id) {
        return cardRepository.findById(id);
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
    public void deleteCard(Integer id) {
        cardRepository.deleteById(id);
    }

    @Override
    public Card updateCardCategory(Integer id, Category newCategory) {
        Optional<Card> cardOpt = cardRepository.findById(id);
        if (cardOpt.isPresent()) {
            Card card = cardOpt.get();
            card.setCategory(newCategory);
            return cardRepository.save(card);
        }
        throw new RuntimeException("Card not found");
    }
}
