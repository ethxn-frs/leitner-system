package com.leitner.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    private Integer id;
    private String question;
    private String answer;
    private String tag;
    private Category category;
}