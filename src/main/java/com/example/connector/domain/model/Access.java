package com.example.connector.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor(staticName = "of")
@Getter
public class Access {
  private final String id;
  private final AccessSource source;
  private final LocalDateTime from;
  private final LocalDateTime to;
  private final List<CardReader> cardReaders;
}
