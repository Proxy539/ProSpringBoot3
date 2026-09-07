package com.apress.myretro.board;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class RetroBoard {

    @Id
    private UUID id;

    @NotBlank(message = "A name must be provided")
    private String name;

    @Singular
    private List<Card> cards;

    public void addCard(Card card) {
        if (this.cards == null) {
            this.cards = new ArrayList<>();
        }
        this.cards.add(card);
    }

    public void addCards(List<Card> cards) {
        if (this.cards == null) {
            this.cards = new ArrayList<>();
        }
        this.cards.addAll(cards);
    }

}
