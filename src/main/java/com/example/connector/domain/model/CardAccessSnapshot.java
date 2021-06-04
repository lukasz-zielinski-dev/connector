package com.example.connector.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
public class CardAccessSnapshot {
  private final String id;
  private final Card card;
  private final AccessesSnapshot currentAccessesSnapshot;
  private final AccessesSnapshot previousAccessesSnapshot;
}
