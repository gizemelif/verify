package com.tax.verify.job;

import com.tax.verify.jpa.DataRepositoryImp;
import com.tax.verify.jpa.QueueRepo;
import com.tax.verify.jpa.pojo.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;


@Service
public class SchedulerConfig implements SchedulingConfigurer {
    private static Logger LOGGER = LoggerFactory.getLogger(SchedulerConfig.class);
    private static ScheduledTaskRegistrar registrar;

    ScheduledFuture dailyScheduler;
    ScheduledFuture queueSchedule;
    Map<ScheduledFuture, Boolean> futureMap = new HashMap<>();

    @Autowired
    DataRepositoryImp dataRepositoryImp;

    @Autowired
    Queue queue;

    @Autowired
    QueueRepo queueRepo;

    @Bean
    public TaskScheduler poolScheduler2(){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        threadPoolTaskScheduler.setPoolSize(1);
        threadPoolTaskScheduler.initialize();

        return threadPoolTaskScheduler;
    }
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        if (registrar == null) {
            registrar = taskRegistrar;
        }
        if (taskRegistrar.getScheduler() == null) {
            taskRegistrar.setScheduler(poolScheduler2());
        }
        if (dailyScheduler == null || (dailyScheduler.isCancelled() && futureMap.get(dailyScheduler) == true)) {
            CronTrigger croneTrigger = new CronTrigger("0 0 10 * * ?", TimeZone.getDefault());
            dailyScheduler = taskRegistrar.getScheduler().schedule(() -> scheduleCron("0 0 0 * * ?"), croneTrigger);
        }
        if (queueSchedule == null || (queueSchedule.isCancelled() && futureMap.get(queueSchedule) == true)) {
            CronTrigger croneTrigger = new CronTrigger("20 * * * * ?", TimeZone.getDefault());
            queueSchedule = taskRegistrar.getScheduler().schedule(() -> scheduleCron("20 * * * * ?"), croneTrigger);
        }
        LOGGER.info("Asked sql string could not found.");
    }

    public void activateFuture(ScheduledFuture future) {
        LOGGER.info("Re-Activating a future");
        futureMap.put(future, true);
        configureTasks(registrar);
    }
    public void activateAll() {
        activateFuture(dailyScheduler);
        activateFuture(queueSchedule);
    }
    // Only reason this method gets the cron as parameter is for debug purposes.
    public void scheduleCron(String cron) {
        LOGGER.info("scheduleCron: Next execution time of this taken from cron expression -> {}", cron);
    }
}
