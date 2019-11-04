package com.tax.verify.job;

import com.tax.verify.dto.Data;
import com.tax.verify.jpa.DataRepositoryImp;
import com.tax.verify.jpa.IndexRepository;
import com.tax.verify.jpa.QueueRepo;
import com.tax.verify.jpa.QueueService;
import com.tax.verify.jpa.pojo.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Service;

import static com.tax.verify.jpa.pojo.Queue.QueueState.PROCESSED;

@Service
public class AnnotationScheduler implements SchedulingConfigurer {
    private static Logger LOGGER = LoggerFactory.getLogger(AnnotationScheduler.class);
    private static ScheduledTaskRegistrar registrar;

    @Autowired
    DataRepositoryImp dataRepositoryImp;

    @Autowired
    QueueService queueService;

    @Autowired
    Queue queue;

   /* @Bean
    public TaskScheduler poolScheduler(){
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        scheduler.setPoolSize(2);
        scheduler.initialize();

        return scheduler;
    }*/
    @Scheduled(cron = "0 50 17 * * MON-FRI")
    public void printCron() {
        System.out.println("cron runs");
        queueService.addRepoAskedBefore();
        //configureTasks(registrar);
        LOGGER.info("printCron: Print every month in a day");

    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {

            LOGGER.info("Asked sql string could not found.");


    }

    // Only reason this method gets the cron as parameter is for debug purposes.
    public void scheduleCron(String cron) {
        LOGGER.info("scheduleCron: Next execution time of this taken from cron expression -> {}", cron);
    }
}
