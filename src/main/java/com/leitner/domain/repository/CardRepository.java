package com.leitner.domain.repository;

import com.leitner.domain.model.Card;
import com.leitner.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findByCategory(Category category);

    List<Card> findByTag(String tag);
}
