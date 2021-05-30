package com.example.connector.adapters.accessdb;

import com.example.connector.domain.model.CardReader;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
class CardReaderDatabaseModel {
  private final String number;

  static CardReaderDatabaseModel fromDomain(CardReader cardReader) {
    return new CardReaderDatabaseModel(cardReader.getNumber());
  }
}
