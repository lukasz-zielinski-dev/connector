package com.example.connector.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
public class CardReader {
  private final String number;
}
