package com.example.connector.adapters.accessdb;

import com.example.connector.domain.model.CardAccess;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.stream.Collectors;

@Getter(value = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Document(collection = "cardAccess")
class CardAccessDatabaseModel {
    @Id
    private final String id;
    private final CardDatabaseModel card;
    private final List<AccessDatabaseModel> accesses;

    static CardAccessDatabaseModel fromDomain(CardAccess cardAccess) {
        var access = cardAccess.getAccesses().stream().map(AccessDatabaseModel::fromDomain).collect(Collectors.toList());
        return new CardAccessDatabaseModel(cardAccess.getId(), CardDatabaseModel.fromDomain(cardAccess.getCard()), access);
    }
}

