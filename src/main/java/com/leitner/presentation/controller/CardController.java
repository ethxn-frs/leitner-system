package com.leitner.presentation.controller;

import com.leitner.application.service.CardService;
import com.leitner.domain.model.Card;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cards")
@Tag(name = "Cards", description = "Gestion des fiches Leitner")
public class CardController {

    @Autowired
    private CardService cardService;

    @Operation(summary = "Récupère toutes les cartes", description = "Retourne les cartes filtrées par tag si fourni.")
    @GetMapping
    public ResponseEntity<List<Card>> getAllCards(@RequestParam(required = false) List<String> tags) {
        List<Card> cards = (tags == null || tags.isEmpty()) ? cardService.getAllCards() : cardService.getCardsByTag(String.join(",", tags));
        return ResponseEntity.ok(cards);
    }

    @Operation(
            summary = "Créer une nouvelle carte",
            description = "Ajoute une nouvelle carte à la base de données et la retourne.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Carte créée avec succès", content = @Content(schema = @Schema(implementation = Card.class))),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            }
    )
    @PostMapping
    public ResponseEntity<Card> createCard(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Détails de la carte à créer", required = true,
                    content = @Content(schema = @Schema(implementation = Card.class)))
            @RequestBody Card card) {
        return ResponseEntity.status(201).body(cardService.createCard(card));
    }

    @Operation(
            summary = "Récupérer les cartes pour un quiz",
            description = "Retourne une liste de cartes correspondant à la date fournie. Si aucune date n'est fournie, retourne les cartes du jour.",
            parameters = {
                    @Parameter(name = "date", description = "Date du quiz au format YYYY-MM-DD (facultatif)", required = false)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des cartes retournée", content = @Content(schema = @Schema(implementation = Card.class))),
                    @ApiResponse(responseCode = "400", description = "Format de date invalide")
            }
    )
    @GetMapping("/quizz")
    public ResponseEntity<List<Card>> getCardsForQuizz(@RequestParam(required = false) String date) {
        List<Card> quizzCards = cardService.getCardsForQuizz(date);
        return ResponseEntity.ok(quizzCards);
    }

    @Operation(
            summary = "Répondre à une carte",
            description = "Met à jour l'état d'une carte selon si la réponse était correcte ou non.",
            parameters = {
                    @Parameter(name = "cardId", description = "Identifiant unique de la carte", required = true)
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "État de la réponse (true si correcte, false sinon)",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Boolean.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Réponse enregistrée avec succès"),
                    @ApiResponse(responseCode = "400", description = "Requête invalide"),
                    @ApiResponse(responseCode = "404", description = "Carte non trouvée")
            }
    )
    @PatchMapping("/{cardId}/answer")
    public ResponseEntity<Void> answerCard(@PathVariable UUID cardId, @RequestBody Boolean isValid) {
        cardService.answerCard(cardId, isValid);
        return ResponseEntity.noContent().build();
    }
}
