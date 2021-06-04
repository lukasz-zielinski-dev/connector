package com.example.connector.adapters.api;

import com.example.connector.domain.model.AccessesSnapshot;
import com.example.connector.domain.model.Card;
import com.example.connector.domain.ports.CardAccessService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Log4j2
public class CardAccessFacade {

  private final CardAccessService cardAccessService;

  public CardAccessFacade(final CardAccessService cardAccessService) {
    this.cardAccessService = cardAccessService;
  }

  public String setCardAccess(SetCardAccessRequest setCardAccessRequest) {
    log.info(setCardAccessRequest.toString());
    var card = Card.of(setCardAccessRequest.getCardNumber());
    var id = UUID.randomUUID().toString();
    var accesses =
        setCardAccessRequest.getAccesses().stream()
            .map(AccessRequestData::access)
            .collect(Collectors.toList());

    var accessesSnapshot =
        AccessesSnapshot.builder()
            .id(id)
            .activeFrom(LocalDateTime.now())
            .accesses(accesses)
            .build();

    return cardAccessService.setCardAccess(card, accessesSnapshot);
  }
}
