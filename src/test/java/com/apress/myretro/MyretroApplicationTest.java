package com.apress.myretro;

import com.apress.myretro.board.Card;
import com.apress.myretro.board.CardType;
import com.apress.myretro.board.RetroBoard;
import com.apress.myretro.exception.CardNotFoundException;
import com.apress.myretro.exception.RetroBoardNotFoundException;
import com.apress.myretro.service.RetroBoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MyretroApplicationTest {

    @Autowired
    RetroBoardService service;

    UUID retroBoardUUID = UUID.fromString("9DC9B71B-A07E-418B-B972-40225449AFF2");
    UUID cardUUID = UUID.fromString("BB2A80A5-A0F5-4180-A6DC-80C84BC014C9");
    UUID mehCardUUID = UUID.fromString("775A3905-D6BE-49AB-A3C4-EBE287B51539");

    @Test
    void saveRetroBoardTest() {
        StepVerifier.create(service.save(RetroBoard.builder()
                        .name("Gathering 2023")
                        .build()))
                .assertNext(retroBoard -> {
                    assertThat(retroBoard).isNotNull();
                    assertThat(retroBoard.getId()).isNotNull();
                })
                .verifyComplete();
    }

    @Test
    void findAllRetroBoardsTest() {
        StepVerifier.create(service.findAll().collectList())
                .assertNext(retroBoards -> {
                    assertThat(retroBoards).isNotNull();
                    assertThat(retroBoards).isNotEmpty();
                })
                .verifyComplete();
    }

    @Test
    void cardsRetroBoardNotFoundTest() {
        StepVerifier.create(service.findAllCardsFromRetroBoard(UUID.randomUUID()))
                .verifyError(RetroBoardNotFoundException.class);
    }

    @Test
    void findRetroBoardTest() {
        StepVerifier.create(service.findById(retroBoardUUID))
                .assertNext(retroBoard -> {
                    assertThat(retroBoard).isNotNull();
                    assertThat(retroBoard.getName()).isEqualTo("Spring Boot Conference 2023");
                    assertThat(retroBoard.getId()).isEqualTo(retroBoardUUID);
                })
                .verifyComplete();
    }

    @Test
    void findCardsInRetroBoardTest() {
        StepVerifier.create(service.findById(retroBoardUUID))
                .assertNext(retroBoard -> {
                    assertThat(retroBoard).isNotNull();
                    assertThat(retroBoard.getCards()).isNotEmpty();
                })
                .verifyComplete();
    }

    @Test
    void addCardToRetroBoardTest() {
        StepVerifier.create(service.addCardToRetroBoard(retroBoardUUID, Card.builder()
                        .comment("Amazing session")
                        .cardType(CardType.HAPPY)
                        .build()))
                .assertNext(card -> {
                    assertThat(card).isNotNull();
                    assertThat(card.getId()).isNotNull();
                })
                .verifyComplete();

        StepVerifier.create(service.findById(retroBoardUUID))
                .assertNext(retroBoard -> {
                    assertThat(retroBoard).isNotNull();
                    assertThat(retroBoard.getCards()).isNotEmpty();
                })
                .verifyComplete();
    }

    @Test
    void findAllCardsFromRetroBoardTest() {
        StepVerifier.create(service.findAllCardsFromRetroBoard(retroBoardUUID).collectList())
                .assertNext(cardList -> {
                    assertThat(cardList).isNotNull();
                    assertThat(cardList.size()).isGreaterThan(3);
                })
                .verifyComplete();
    }

    @Test
    void removeCardsFromRetroBoardTest() {
        StepVerifier.create(service.removeCardByUUID(retroBoardUUID, cardUUID))
                .verifyComplete();

        StepVerifier.create(service.findById(retroBoardUUID))
                .assertNext(retroBoard -> {
                    assertThat(retroBoard).isNotNull();
                    assertThat(retroBoard.getCards()).isNotEmpty();
                    assertThat(retroBoard.getCards()).hasSizeLessThan(4);
                })
                .verifyComplete();
    }

    @Test
    void finCardByIdInRetroBoardTest() {
        StepVerifier.create(service.findCardByUUID(mehCardUUID))
                .assertNext(card -> {
                    assertThat(card).isNotNull();
                    assertThat(card.getId()).isEqualTo(mehCardUUID);
                })
                .verifyComplete();
    }

    @Test
    void notFoundCardInRetroBoardTest() {
        StepVerifier.create(service.findCardByUUID(UUID.randomUUID()))
                .verifyError(CardNotFoundException.class);
    }
}
