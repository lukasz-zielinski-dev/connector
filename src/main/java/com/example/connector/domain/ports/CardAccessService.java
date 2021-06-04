package com.example.connector.domain.ports;

import com.example.connector.domain.model.AccessesSnapshot;
import com.example.connector.domain.model.Card;
import org.springframework.stereotype.Component;

@Component
public final class CardAccessService {

  private final CardAccessRepository accessRepository;

  public CardAccessService(final CardAccessRepository accessRepository) {
    this.accessRepository = accessRepository;
  }

  public String setCardAccess(final Card card, final AccessesSnapshot accessesSnapshot) {
    return accessRepository.setCardAccesses(card, accessesSnapshot);
    //        TODO schedule job generation
  }
}
