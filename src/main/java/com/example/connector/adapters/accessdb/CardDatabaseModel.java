package com.example.connector.adapters.accessdb;

import com.example.connector.domain.model.Card;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PACKAGE)
class CardDatabaseModel {
  private final String number;

  static CardDatabaseModel fromDomain(Card card) {
    return new CardDatabaseModel(card.getNumber());
  }
}
