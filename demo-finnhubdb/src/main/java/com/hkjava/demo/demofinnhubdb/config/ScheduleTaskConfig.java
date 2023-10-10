package com.hkjava.demo.demofinnhubdb.config;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hkjava.demo.demofinnhubdb.exception.FinnhubException;
import com.hkjava.demo.demofinnhubdb.service.AdminService;
import lombok.extern.slf4j.Slf4j;

@Component
@EnableScheduling
@Slf4j
// @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class ScheduleTaskConfig {

  @Autowired
  AdminService adminService;
  // fixed rate 係base on task 行幾耐既話，就應該係隔 10S , 依家隔15s
  public static boolean start = false;

  @Scheduled(fixedRate = 60000) // 60 sec
  public void fixedRateTask() throws InterruptedException, FinnhubException {
    if (start) {
      // System.out.println("FixedRate Task - " + System.currentTimeMillis());
      // Thread.sleep(15000L); // doing task ... 15 seconds
      log.info("Refresh times : " + LocalDateTime.now());
      adminService.refresh();
    }
  }

  @Scheduled(fixedRate = 10000) // 10sec
  public void fixedRateTask2() throws InterruptedException {
    if (start) {
      System.out.println("Fixed  Rate Task - " + System.currentTimeMillis());
      Thread.sleep(15000L);// doing task ... 15 seconds
    }
  }

  @Scheduled(fixedDelay = 4000) // 4sec
  public void fixedDelayTask() throws InterruptedException {
    if (start) {
      System.out.println("Fixed Delay Task - " + System.currentTimeMillis());
      Thread.sleep(5000L);// doing task ... 5 seconds
    }
  }

  @Scheduled(cron = " 0/30 * 9-23 * * MON-FRI") // set this String to yml
  public void fixedTimeTask() throws InterruptedException {
    if (start) {
      System.out.println("Fixed  Time Task - " + System.currentTimeMillis());
      Thread.sleep(5000L);// doing task ... 5 seconds

    }
  }


}
