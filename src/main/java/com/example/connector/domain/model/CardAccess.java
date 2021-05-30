package com.example.connector.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(staticName = "of")
@Getter
public class CardAccess {
  private final String id;
  private final Card card;
  private final List<Access> accesses;
}
