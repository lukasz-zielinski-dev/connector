package com.example.connector.adapters.accessdb;

import com.example.connector.domain.model.AccessSource;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
class AccessSourceDatabaseModel {
  private final String id;

  static AccessSourceDatabaseModel fromDomain(AccessSource accessSource) {
    return new AccessSourceDatabaseModel(accessSource.getId());
  }
}
