package com.tax.verify.job;

import com.tax.verify.jpa.DataRepositoryImp;
import com.tax.verify.jpa.QueueRepo;
import com.tax.verify.jpa.QueueService;
import com.tax.verify.jpa.pojo.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import static com.tax.verify.jpa.pojo.Queue.QueueState.PROCESSED;

@Component
@EnableAsync
public class Scheduler {
    private Queue queue = new Queue();
    private static Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);

    @Autowired
    SchedulerConfig schedulerConfig;

    @Autowired
    QueueRepo queueRepo;

    @Autowired
    private DataRepositoryImp dataRepositoryImp;

    @Autowired
    QueueService queueService;

    public Queue findByState() {

        return queueRepo.findByState();
    }

    @Async
    @Scheduled(cron = "0 0 0 * * ?")
    public void dailyScheduler() {
        System.out.println("cron runs");
        try{
            queueService.addRepoVdSql();
            Thread.sleep(20000);
            queueService.addRepoTcSql();

        }catch (Exception e){
            LOGGER.info(e.getMessage());
        }

    }

    @Async
    @Scheduled(fixedDelay = 20000)
    public void queueSchedule() {
        queue = findByState();
        if (queue == null) {
            return;
        }
        try {
            if (queue != null && (queue.getQueryType().equals("tc") || queue.getQueryType().equals("TC"))) {

                queueRepo.updateState(Queue.QueueState.PROCESSING, "Process is starting...", queue.getJob_oid());
                dataRepositoryImp.updateTable(queue.getSql_string());

                queueRepo.updateStateProcessed(PROCESSED, "Process is completed", queue.getEnd_date(), queue.getJob_oid());
            } else if (queue != null && (queue.getQueryType().equals("vd") || queue.getQueryType().equals("VD"))) {
                queueRepo.updateState(Queue.QueueState.PROCESSING, "Process is starting...", queue.getJob_oid());
                dataRepositoryImp.updateVknTable(queue.getSql_string());

                queueRepo.updateStateProcessed(PROCESSED, "Process is completed", queue.getEnd_date(), queue.getJob_oid());
            }
            return;

        } catch (Exception e) {
            LOGGER.info(e.getMessage());
         //   e.printStackTrace();
            System.out.println("Queue is null");
        }
    }
}
/*
--> Eğer plate için servisle ayrı bir parametre alınması gerekirse;

    if(queue != null && (queue.getQueryType().equals("tc") || queue.getQueryType().equals("TC") && queue.getIsPlate().equals("none"))) {
                queueRepo.updateState(Queue.QueueState.PROCESSING,"Process is starting...", queue.getJob_oid());
                dataRepositoryImp.updateTCNonePlate(queue.getSql_string());

                queueRepo.updateStateProcessed(PROCESSED,"Process is completed", queue.getEnd_date(),queue.getJob_oid());

            } else if (queue != null && (queue.getQueryType().equals("tc") || queue.getQueryType().equals("TC") && queue.getIsPlate().length() != 0)) {
                queueRepo.updateState(Queue.QueueState.PROCESSING,"Process is starting...", queue.getJob_oid());
                dataRepositoryImp.updateTable(queue.getSql_string());

                queueRepo.updateStateProcessed(PROCESSED,"Process is completed", queue.getEnd_date(),queue.getJob_oid());

            } else if (queue != null && (queue.getQueryType().equals("vd") || queue.getQueryType().equals("VD") && queue.getIsPlate().equals("none"))) {
                queueRepo.updateState(Queue.QueueState.PROCESSING,"Process is starting...", queue.getJob_oid());
                dataRepositoryImp.updateVknNullPlate(queue.getSql_string());

                queueRepo.updateStateProcessed(PROCESSED,"Process is completed", queue.getEnd_date(),queue.getJob_oid());
            } else if (queue != null && (queue.getQueryType().equals("vd") || queue.getQueryType().equals("VD") && queue.getIsPlate().length() != 0)) {
                queueRepo.updateState(Queue.QueueState.PROCESSING,"Process is starting...", queue.getJob_oid());
                dataRepositoryImp.updateVknTable(queue.getSql_string());

                queueRepo.updateStateProcessed(PROCESSED,"Process is completed", queue.getEnd_date(),queue.getJob_oid());
            }*/