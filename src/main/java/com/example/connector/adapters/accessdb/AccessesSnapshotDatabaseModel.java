package com.example.connector.adapters.accessdb;

import com.example.connector.domain.model.AccessesSnapshot;
import lombok.AccessLevel;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder(access = AccessLevel.PACKAGE)
class AccessesSnapshotDatabaseModel {
  @MongoId private final String id;
  private final LocalDateTime activeFrom;
  private final LocalDateTime activeTo;
  private final String replacedBy;
  private final List<AccessDatabaseModel> accesses;

  static AccessesSnapshotDatabaseModel fromDomain(final AccessesSnapshot accessesSnapshot) {
    var accesses =
        accessesSnapshot.getAccesses().stream()
            .map(AccessDatabaseModel::fromDomain)
            .collect(Collectors.toList());

    return builder()
        .id(accessesSnapshot.getId())
        .activeFrom(accessesSnapshot.getActiveFrom())
        .activeTo(accessesSnapshot.getActiveTo())
        .replacedBy(accessesSnapshot.getReplacedBy())
        .accesses(accesses)
        .build();
  }
}
