package com.tax.verify.jpa;

import com.tax.verify.jpa.pojo.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;

import java.util.Date;

@Repository
@org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRES_NEW)
public interface QueueRepo extends JpaRepository< Queue, String > {
    @Modifying
    @Query("UPDATE Queue q SET q.state =:state, q.notification_mail =:notification_mail, q.start_date = current_timestamp where q.job_oid =:job_oid")
    void updateState(@Param("state")Queue.QueueState state, @Param("notification_mail") String notification_mail, @Param("job_oid") String job_oid);

    @Modifying
    @Query("UPDATE Queue q SET q.state =:state, q.notification_mail =:notification_mail, q.end_date =:end_date where q.job_oid =:job_oid")
    void updateStateProcessed(@Param("state")Queue.QueueState state, @Param("notification_mail") String notification_mail,@Param("end_date") Date end_date,@Param("job_oid") String job_oid);

    @Query(value = "SELECT * FROM SCHEDULED_VD_TC_INDEX u WHERE u.state = 'WAITING' OR u.state = 'PROCESSING' ORDER BY u.created_at ASC LIMIT 1", nativeQuery = true)
    Queue findByState();
}
