package com.example.connector.domain.ports;

import com.example.connector.domain.model.Access;
import com.example.connector.domain.model.Card;

import java.util.List;

public interface CardAccessRepository {

  String setCardAccesses(final Card card, final List<Access> accesses);
}
