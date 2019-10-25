package com.tax.verify.api;


import com.tax.verify.dto.Data;
import com.tax.verify.job.Scheduler;
import com.tax.verify.jpa.*;
import com.tax.verify.jpa.pojo.Queue;
import com.tax.verify.jpa.QueueService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.tax.verify.jpa.pojo.Queue.QueueState.PROCESSED;
import static com.tax.verify.jpa.pojo.Queue.QueueState.WAITING;

@RestController
@RequestMapping("api")
public class DataController {
    @Autowired
    private DataRepositoryImp dataRepositoryImp;
    @Autowired
    private IndexRepository dataRepository;

    @Autowired
    private QueueRepo queueRepo;

    @Autowired
    QueueService service;

    //Bu servis gelen SQL string leri alır ve Queue tablosuna set eder.
    @GetMapping("/datas")
    @ResponseBody
    public void all(@RequestParam String sql, @RequestParam String queryType) {
        service.setQueueRepo(sql, queryType);

    }

/*
    @RequestMapping( value = "/orders", method = RequestMethod.POST, produces = "application/json; charset=UTF-8" )
    public ResponseEntity<List<Queue>> update(@RequestBody List<Queue> queues){
        queues.stream().forEach(q -> q.setSql_string(q.getSql_string()));
        ResponseEntity<List<Queue>> listResponseEntity = new ResponseEntity<List<Queue>>(queues, HttpStatus.OK);

        List<Queue> newList = listResponseEntity.getBody();

        for(Queue q: newList){
            String state = q.getState().toString();
            String notification_mail = q.getNotification_mail().toString();
            String sql_string = q.getSql_string().toString();
            String query_type = q.getQueryType().toString();

            //service.setQueueRepo(sql_string,query_type);
            System.out.println("--->>>"+q.getState().toString());
        }


        return listResponseEntity;
    }
*/
}
