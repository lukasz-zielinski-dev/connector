package com.example.connector.adapters.scheduler;

import com.example.connector.domain.ports.AccessSynchronizationJob;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ScheduledAccessSynchronizationJob implements AccessSynchronizationJob {

  @Override
  @Scheduled(fixedDelay = 5000)
  public void synchronizePendingAccesses() {
    //    TODO implement
    log.info("Found 0 pending accesses.");
  }
}
