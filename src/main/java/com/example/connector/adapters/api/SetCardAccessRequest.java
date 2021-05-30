package com.example.connector.adapters.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PACKAGE)
@ToString
class SetCardAccessRequest {
  @JsonProperty("cardNumber")
  private final String cardNumber;
  @JsonProperty("accesses")
  private final List<AccessRequestData> accesses;
}
