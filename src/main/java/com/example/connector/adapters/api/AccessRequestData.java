package com.example.connector.adapters.api;

import com.example.connector.domain.model.Access;
import com.example.connector.domain.model.AccessSource;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PACKAGE)
class AccessRequestData {
  @JsonProperty("source")
  private final String source;

  @JsonProperty("cardReaders")
  private final List<CardReaderRequestData> cardReaders;

  @JsonProperty("from")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
  private final LocalDateTime from;

  @JsonProperty("to")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
  private final LocalDateTime to;

  Access access() {
    var id = UUID.randomUUID().toString();
    var source = AccessSource.of(this.source);
    var cardReaders =
        this.cardReaders.stream()
            .map(CardReaderRequestData::cardReader)
            .collect(Collectors.toList());

    return Access.of(id, source, this.from, this.to, cardReaders);
  }
}
