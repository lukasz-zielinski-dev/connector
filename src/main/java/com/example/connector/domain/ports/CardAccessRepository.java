package com.example.connector.domain.ports;

import com.example.connector.domain.model.AccessesSnapshot;
import com.example.connector.domain.model.Card;

public interface CardAccessRepository {

  String setCardAccesses(final Card card, final AccessesSnapshot accessesSnapshot);
}
