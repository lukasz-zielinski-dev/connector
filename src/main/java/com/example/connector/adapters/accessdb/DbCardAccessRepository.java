package com.example.connector.adapters.accessdb;

import com.example.connector.domain.model.AccessesSnapshot;
import com.example.connector.domain.model.Card;
import com.example.connector.domain.ports.CardAccessRepository;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Repository
class DbCardAccessRepository implements CardAccessRepository {

  private final MongoTemplate mongoTemplate;

  DbCardAccessRepository(final MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public String setCardAccesses(final Card card, final AccessesSnapshot accessesSnapshot) {
    CardAccessHistoryDatabaseModel operationResult;

    final var query = getQueryByCardNumber(card.getNumber());
    final var update = getUpdatePushAccessesSnapshots(accessesSnapshot);
    final var options = getOptionsReturnNew();
    final var updatedCardAccessHistory =
        mongoTemplate.findAndModify(query, update, options, CardAccessHistoryDatabaseModel.class);

    if (updatedCardAccessHistory == null) {
      final var cardAccessHistoryDatabaseModel =
          CardAccessHistoryDatabaseModel.fromDomain(card, accessesSnapshot);
      operationResult = mongoTemplate.save(cardAccessHistoryDatabaseModel);
    } else {
      operationResult = updatedCardAccessHistory;

      final var queryPreviousAccessesSnapshot =
          getQueryByCardNumberAndAccessesSnapshots_ActiveToDoesNotExistAndAccessesSnapshots_IdNotEqual(
              card.getNumber(), accessesSnapshot.getId());
      final var updatePreviousAccessesSnapshot =
          getUpdateSetAccessesSnapshots_Entry_ActiveToAndReplacedBy(accessesSnapshot);
      mongoTemplate.findAndModify(
          queryPreviousAccessesSnapshot,
          updatePreviousAccessesSnapshot,
          options,
          CardAccessHistoryDatabaseModel.class);
    }

    return operationResult.getId();
  }

  private Query getQueryByCardNumber(final String cardNumber) {
    final var query = new Query();
    query.addCriteria(Criteria.where("card.number").is(cardNumber));
    return query;
  }

  private Update getUpdatePushAccessesSnapshots(final AccessesSnapshot accessesSnapshot) {
    final var accessDatabaseModels = AccessesSnapshotDatabaseModel.fromDomain(accessesSnapshot);
    final var update = new Update();
    update.push("accessesSnapshots", accessDatabaseModels);
    return update;
  }

  private FindAndModifyOptions getOptionsReturnNew() {
    final var options = new FindAndModifyOptions();
    options.returnNew(true);
    return options;
  }

  private Query
      getQueryByCardNumberAndAccessesSnapshots_ActiveToDoesNotExistAndAccessesSnapshots_IdNotEqual(
          final String cardNumber, String newAccessSnapshotId) {
    final var cardNumberCriteria = Criteria.where("card.number").is(cardNumber);
    final var accessesSnapshots_ActiveToDoesNotExistCriteria =
        Criteria.where("accessesSnapshots").elemMatch(Criteria.where("activeTo").exists(false));
    final var accessesSnapshots_IdNotEqualCriteria =
        Criteria.where("accessesSnapshots").elemMatch(Criteria.where("id").ne(newAccessSnapshotId));
    final var andBothCriteria =
        new Criteria()
            .andOperator(
                cardNumberCriteria,
                accessesSnapshots_ActiveToDoesNotExistCriteria,
                accessesSnapshots_IdNotEqualCriteria);
    return new Query(andBothCriteria);
  }

  private Update getUpdateSetAccessesSnapshots_Entry_ActiveToAndReplacedBy(
      final AccessesSnapshot newAccessesSnapshot) {
    final var update = new Update();
    update.set("accessesSnapshots.$.activeTo", newAccessesSnapshot.getActiveFrom());
    update.set("accessesSnapshots.$.replacedBy", newAccessesSnapshot.getId());
    return update;
  }
}
