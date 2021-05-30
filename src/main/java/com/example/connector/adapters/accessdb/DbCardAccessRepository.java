package com.example.connector.adapters.accessdb;

import com.example.connector.domain.model.Access;
import com.example.connector.domain.model.Card;
import com.example.connector.domain.model.CardAccess;
import com.example.connector.domain.ports.CardAccessRepository;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
class DbCardAccessRepository implements CardAccessRepository {

  private final MongoTemplate mongoTemplate;

  DbCardAccessRepository(final MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public String setCardAccesses(final Card card, final List<Access> accesses) {
    CardAccessDatabaseModel operationResult;

    final var query = getQueryByCardNumber(card.getNumber());
    final var update = getUpdateSetAccesses(accesses);
    final var options = getOptionsReturnNew();
    final var updatedCardAccess =
        mongoTemplate.findAndModify(query, update, options, CardAccessDatabaseModel.class);

    if (updatedCardAccess == null) {
      final var id = UUID.randomUUID().toString();
      final var cardAccess = CardAccess.of(id, card, accesses);
      final var cardAccessDatabaseModel = CardAccessDatabaseModel.fromDomain(cardAccess);
      operationResult = mongoTemplate.save(cardAccessDatabaseModel);
    } else {
      operationResult = updatedCardAccess;
    }

    createAndPersistHistoryRecord(operationResult);

    return operationResult.getId();
  }

  private Query getQueryByCardNumber(final String cardNumber) {
    final var query = new Query();
    query.addCriteria(Criteria.where("card.number").is(cardNumber));
    return query;
  }

  private Update getUpdateSetAccesses(final List<Access> accesses) {
    final var accessDatabaseModels =
        accesses.stream().map(AccessDatabaseModel::fromDomain).collect(Collectors.toList());
    final var update = new Update();
    update.set("accesses", accessDatabaseModels);
    return update;
  }

  private FindAndModifyOptions getOptionsReturnNew() {
    final var options = new FindAndModifyOptions();
    options.returnNew(true);
    return options;
  }

  private void createAndPersistHistoryRecord(CardAccessDatabaseModel cardAccess) {
    final var id = UUID.randomUUID().toString();
    final var card = cardAccess.getCard();
    final var newAccesses = cardAccess.getAccesses();
    final var now = LocalDateTime.now();

    final var newAccessHistoryDatabaseModel =
        AccessHistoryDatabaseModel.builder().activeFrom(now).accesses(newAccesses).build();
    final var historyRecord =
        CardAccessHistoryDatabaseModel.builder()
            .id(id)
            .card(card)
            .accessesHistory(Collections.singletonList(newAccessHistoryDatabaseModel))
            .build();

    final var query = getQueryByCardNumber(card.getNumber());
    final var update = getUpdatePushAccessesHistory(newAccessHistoryDatabaseModel);
    final var updatedCardAccessHistory =
        mongoTemplate.findAndModify(query, update, CardAccessHistoryDatabaseModel.class);

    if (updatedCardAccessHistory == null) {
      mongoTemplate.save(historyRecord);
    } else {
      final var queryPreviousAccessHistory =
          getQueryByCardNumberAndAccessesHistory_ActiveToDoesNotExist(card.getNumber());
      final var updatePreviousAccessHistory = getUpdateSetAccessesHistory_Entry_ActiveTo(now);

      mongoTemplate.findAndModify(
          queryPreviousAccessHistory,
          updatePreviousAccessHistory,
          CardAccessHistoryDatabaseModel.class);
    }
  }

  private Update getUpdatePushAccessesHistory(
      final AccessHistoryDatabaseModel accessHistoryDatabaseModel) {
    final var update = new Update();
    update.push("accessesHistory", accessHistoryDatabaseModel);
    return update;
  }

  private Query getQueryByCardNumberAndAccessesHistory_ActiveToDoesNotExist(
      final String cardNumber) {
    final var cardNumberCriteria = Criteria.where("card.number").is(cardNumber);
    final var accessHistory_ActiveToDoesNotExistCriteria =
        Criteria.where("accessesHistory").elemMatch(Criteria.where("activeTo").exists(false));
    final var andBothCriteria =
        new Criteria().andOperator(cardNumberCriteria, accessHistory_ActiveToDoesNotExistCriteria);
    return new Query(andBothCriteria);
  }

  private Update getUpdateSetAccessesHistory_Entry_ActiveTo(final LocalDateTime activeTo) {
    final var update = new Update();
    update.set("accessesHistory.$.activeTo", activeTo);
    return update;
  }
}
