package com.example.connector.adapters.api;

import com.example.connector.domain.model.Card;
import com.example.connector.domain.ports.CardAccessService;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CardAccessFacade {

  private final CardAccessService cardAccessService;

  public CardAccessFacade(final CardAccessService cardAccessService) {
    this.cardAccessService = cardAccessService;
  }

  public String setCardAccess(SetCardAccessRequest setCardAccessRequest) {
    var card = Card.of(setCardAccessRequest.getCardNumber());
    var accesses =
        setCardAccessRequest.getAccesses().stream()
            .map(AccessRequestData::access)
            .collect(Collectors.toList());

    return cardAccessService.setCardAccess(card, accesses);
  }
}
