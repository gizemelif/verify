package com.tax.verify.job;

import com.tax.verify.api.DataController;
import com.tax.verify.jpa.DataRepositoryImp;
import com.tax.verify.jpa.QueueRepo;
import com.tax.verify.jpa.pojo.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.tax.verify.jpa.pojo.Queue.QueueState.PROCESSED;
import static com.tax.verify.jpa.pojo.Queue.QueueState.PROCESSING;

@Component
public class Scheduler {
    private Queue queue = new Queue();

    @Autowired
    QueueRepo queueRepo;

    @Autowired
    private DataRepositoryImp dataRepositoryImp;

    public Queue findByState() {

        return queueRepo.findByState();
    }

    @Scheduled(fixedDelay = 20000)
    public void checkTheSchedule() {

        queue = findByState();
        if(queue == null){return;}
        try{
            if (queue != null && queue.getQueryType().equals("tc") || queue.getQueryType().equals("TC")) {
                queueRepo.updateState(Queue.QueueState.PROCESSING,"Process is starting...", queue.getJob_oid());
                dataRepositoryImp.updateTable(queue.getSql_string());

                queueRepo.updateStateProcessed(PROCESSED,"Process is completed", queue.getEnd_date(),queue.getJob_oid());

            } else if (queue != null && queue.getQueryType().equals("vd") || queue.getQueryType().equals("VD")) {
                queueRepo.updateState(Queue.QueueState.PROCESSING,"Process is starting...", queue.getJob_oid());
                dataRepositoryImp.updateVknTable(queue.getSql_string());

                queueRepo.updateStateProcessed(PROCESSED,"Process is completed", queue.getEnd_date(),queue.getJob_oid());
            }

            return;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Queue is null");

        }

    }

}
