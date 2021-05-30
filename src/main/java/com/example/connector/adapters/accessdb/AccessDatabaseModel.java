package com.example.connector.adapters.accessdb;

import com.example.connector.domain.model.Access;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
class AccessDatabaseModel {
  @MongoId private final String id;

  private final AccessSourceDatabaseModel source;
  private final LocalDateTime from;
  private final LocalDateTime to;
  private final List<CardReaderDatabaseModel> cardReaders;

  static AccessDatabaseModel fromDomain(Access access) {
    final var id = access.getId();
    final var source = AccessSourceDatabaseModel.fromDomain(access.getSource());
    final var from = access.getFrom();
    final var to = access.getTo();
    final var cardReaders =
        access.getCardReaders().stream()
            .map(CardReaderDatabaseModel::fromDomain)
            .collect(Collectors.toList());
    return new AccessDatabaseModel(id, source, from, to, cardReaders);
  }
}
