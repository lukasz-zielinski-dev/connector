package com.example.connector.domain.ports;

import com.example.connector.domain.model.Access;
import com.example.connector.domain.model.Card;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class CardAccessService {

  private final CardAccessRepository accessRepository;

  public CardAccessService(final CardAccessRepository accessRepository) {
    this.accessRepository = accessRepository;
  }

  public String setCardAccess(final Card card, final List<Access> accesses) {
    //        TODO log request
    return accessRepository.setCardAccesses(card, accesses);
    //        TODO schedule job generation
  }
}
