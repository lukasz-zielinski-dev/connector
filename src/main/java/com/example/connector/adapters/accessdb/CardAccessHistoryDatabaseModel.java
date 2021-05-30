package com.example.connector.adapters.accessdb;

import lombok.AccessLevel;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Builder(access = AccessLevel.PACKAGE)
@Document(collection = "cardAccessHistory")
class CardAccessHistoryDatabaseModel {
  @MongoId private final String id;
  private final CardDatabaseModel card;
  private final List<AccessHistoryDatabaseModel> accessesHistory;
}
