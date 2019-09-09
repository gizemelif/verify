package com.tax.verify.job;

import com.tax.verify.jpa.DataRepositoryImp;
import com.tax.verify.jpa.QueueRepo;
import com.tax.verify.jpa.pojo.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
    public void checkTheSchedule(){
        queue = findByState();

        if(queue != null && queue.getQueryType().equals("tc") ){

            dataRepositoryImp.updateTable(queue.getSql_string());

            //queueRepo.updateState(PROCESSED, queueRepo.findByState().getJob_oid(), queueRepo.findByState().getEnd_date());

        }else if(queue != null && queue.getQueryType().equals("vkn")){
            dataRepositoryImp.updateVknTable(queue.getSql_string());
        }
        //queueRepo.findByState().setState(PROCESSED);
       // queueRepo.findByState().setEnd_date(new Date());

        return;



    }


}
