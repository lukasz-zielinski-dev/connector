package com.example.connector.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor(staticName = "of")
@Builder
@Getter
public class AccessesSnapshot {
  private final String id;
  private final LocalDateTime activeFrom;
  private final LocalDateTime activeTo;
  private final String replacedBy;
  private final List<Access> accesses;
}
