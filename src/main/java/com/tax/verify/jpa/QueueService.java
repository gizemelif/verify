package com.tax.verify.jpa;

import com.tax.verify.jpa.pojo.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

import static com.tax.verify.jpa.pojo.Queue.QueueState.WAITING;

@Service
@Transactional
public class QueueService {
    @Autowired
    private DataRepositoryImp dataRepositoryImp;

    @Autowired
    private IndexRepository dataRepository;

    @Autowired
    private QueueRepo queueRepo;

    public void setQueueRepo(String sql, String queryType){
        Queue queue = new Queue();

        queue.setSql_string(sql);
        queue.setNotification_mail("Queued");
        queue.setState(WAITING);
        queue.setCreated_at( new Date());
        queue.setQueryType(queryType);
        //queue.setIsPlate(isPlate);
        queueRepo.save(queue);
    }
    public void setQueue(String data){
        Queue queue = new Queue();
        queue.setSql_string(data);
    }
}
