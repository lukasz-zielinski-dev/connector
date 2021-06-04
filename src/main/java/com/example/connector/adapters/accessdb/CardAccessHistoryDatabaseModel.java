package com.example.connector.adapters.accessdb;

import com.example.connector.domain.model.AccessesSnapshot;
import com.example.connector.domain.model.Card;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
import java.util.UUID;

@Getter(value = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Document(collection = "cardAccessHistory")
class CardAccessHistoryDatabaseModel {
  @MongoId private final String id;
  private final CardDatabaseModel card;
  private final List<AccessesSnapshotDatabaseModel> accessesSnapshots;

  static CardAccessHistoryDatabaseModel fromDomain(
      final Card card, final AccessesSnapshot accessesSnapshot) {
    var id = UUID.randomUUID().toString();
    var accessesSnapshots = List.of(AccessesSnapshotDatabaseModel.fromDomain(accessesSnapshot));
    return new CardAccessHistoryDatabaseModel(
        id, CardDatabaseModel.fromDomain(card), accessesSnapshots);
  }
}
