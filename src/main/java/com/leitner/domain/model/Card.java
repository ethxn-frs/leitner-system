package com.leitner.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String question;
    private String answer;
    private String tag;

    @Enumerated(EnumType.STRING)
    private Category category;

    public Card(String question, String answer, String tag, Category category) {
        this.question = question;
        this.answer = answer;
        this.tag = tag;
        this.category = category;
    }
}
