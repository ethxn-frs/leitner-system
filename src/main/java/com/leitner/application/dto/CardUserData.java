package com.leitner.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Données utilisateur pour créer une carte")
public class CardUserData {

    @Schema(description = "Question posée", example = "Quelle est la capitale de la France?")
    private String question;

    @Schema(description = "Réponse attendue", example = "Paris")
    private String answer;

    @Schema(description = "Tag pour catégoriser la carte", example = "Géographie")
    private String tag;

    @Schema(description = "Catégorie de révision", example = "FIRST")
    private String category;
}
