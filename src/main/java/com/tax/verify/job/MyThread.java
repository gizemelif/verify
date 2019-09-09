/*package com.tax.verify.job;

import com.tax.verify.api.DataController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyThread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(MyThread.class);
    private String sql = "";

    @Autowired
    DataController dataController;



    @Scheduled(fixedDelay = 1000)
    @Override
    public void run(){
        logger.info("Called from thread");
        dataController.all(sql);
        System.out.println("deneme");
    }


}
*/