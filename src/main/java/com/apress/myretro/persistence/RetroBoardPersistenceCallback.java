package com.apress.myretro.persistence;

import com.apress.myretro.board.RetroBoard;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;
import org.springframework.data.mongodb.core.mapping.event.ReactiveBeforeConvertCallback;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.UUID;

@Component
@Slf4j
public class RetroBoardPersistenceCallback implements ReactiveBeforeConvertCallback<RetroBoard> {

    @Override
    public Publisher<RetroBoard> onBeforeConvert(RetroBoard entity, String collection) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }

        if (entity.getCards() == null) {
            entity.setCards(new ArrayList<>());
        }

        log.info("[CALLBACK] onBeforeConvert {}", entity);

        return Mono.just(entity);
    }
}
