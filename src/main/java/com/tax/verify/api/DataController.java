package com.tax.verify.api;


import com.tax.verify.dto.Data;
import com.tax.verify.job.Scheduler;
import com.tax.verify.jpa.*;
import com.tax.verify.jpa.pojo.Queue;
import com.tax.verify.jpa.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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
       // String sql = "select * from vd_tc_index where tc_sorulan = '42923007322' and point_status_name = 'ACIK' and il_sorulan = 'Tekirdağ'";
        service.setQueueRepo(sql, queryType);


    }


/*

    public void setQueueRepo(String sql){

        queue.setSql_string(sql);
        queue.setNotification_mail("Process is waiting");
        queue.setState(WAITING);
        queue.setEnd_date(new Date());
        queue.setCreated_at( new Date());
        queue.setStarted_date(new Date());
        queueRepo.save(queue);
    }
*/

}
