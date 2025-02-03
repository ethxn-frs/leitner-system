package com.leitner.presentation.controller;

import com.leitner.application.service.CardService;
import com.leitner.domain.model.Card;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public ResponseEntity<List<Card>> getAllCards(@RequestParam(required = false) List<String> tags) {
        List<Card> cards = (tags == null || tags.isEmpty()) ? cardService.getAllCards() : cardService.getCardsByTag(String.join(",", tags));
        return ResponseEntity.ok(cards);
    }

    @PostMapping
    public ResponseEntity<Card> createCard(@RequestBody Card card) {
        return ResponseEntity.status(201).body(cardService.createCard(card));
    }

    @GetMapping("/quizz")
    public ResponseEntity<List<Card>> getCardsForQuizz(@RequestParam(required = false) String date) {
        List<Card> quizzCards = cardService.getCardsForQuizz(date);
        return ResponseEntity.ok(quizzCards);
    }

    @PatchMapping("/{cardId}/answer")
    public ResponseEntity<Void> answerCard(@PathVariable Integer cardId, @RequestBody Boolean isValid) {
        cardService.answerCard(cardId, isValid);
        return ResponseEntity.noContent().build();
    }
}
