package com.example.connector.adapters.api;

import com.example.connector.domain.model.CardReader;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
class CardReaderRequestData {
  @JsonProperty("number")
  private String number;

  CardReader cardReader() {
    return CardReader.of(number);
  }
}
