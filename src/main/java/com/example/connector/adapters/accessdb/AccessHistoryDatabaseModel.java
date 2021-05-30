package com.example.connector.adapters.accessdb;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder(access = AccessLevel.PACKAGE)
class AccessHistoryDatabaseModel {
    private final LocalDateTime activeFrom;
    private final LocalDateTime activeTo;
    private final List<AccessDatabaseModel> accesses;
}
