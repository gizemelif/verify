package com.tax.verify.jpa.pojo;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "QUEUE")
public class Queue {

    public enum QueueState {
        WAITING, PROCESSING, PROCESSED, FAILED
    }

    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Column(name = "STARTED_AT")
    private Date startedAt;

    @Column(name = "FINISHED_AT")
    private Date finishedAt;

    @Column(name = "STATE")
    @Enumerated(EnumType.STRING)
    private QueueState state;

    @Column(name = "NOTIFICATION_MAIL")
    private String notificationMail;

    @Column(name = "CLASS_NAME")
    private String clazz;

    @Column(name = "SQL_STRING")
    private String sqlString;

    @Column(name = "JOB_OID")
    private String jobOid;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }

    public QueueState getState() {
        return state;
    }

    public void setState(QueueState state) {
        this.state = state;
    }

    public String getNotificationMail() {
        return notificationMail;
    }

    public void setNotificationMail(String notificationMail) {
        this.notificationMail = notificationMail;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getSqlString() {
        return sqlString;
    }

    public void setSqlString(String sqlString) {
        this.sqlString = sqlString;
    }

    public String getJobOid() {
        return jobOid;
    }

    public void setJobOid(String jobOid) {
        this.jobOid = jobOid;
    }
}
