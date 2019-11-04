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
    private Queue queue;

    @Autowired
    private QueueRepo queueRepo;

    @Autowired
    QueueService service;

    //Bu servis gelen SQL string leri alır ve Queue tablosuna set eder.
    //Queue'ya üzerinden 1 aydan fazla zaman geçmiş vd ve tc lerin yeniden sorulması için gerekli olan sorgu da kaydedildi.
    //Bir kereliğine tabloya eklendi ve her gece çalıştırılması için zamanlandı.
    
    @GetMapping("/datas")
    @ResponseBody
    public void all( @RequestParam String sql, @RequestParam String queryType ) {

        service.setQueueRepo(sql, queryType);
    }
}
