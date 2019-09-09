package com.tax.verify.jpa.pojo;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@Component
@Entity
@Table(name = "SCHEDULED_VD_TC_INDEX")
@EntityListeners(AuditingEntityListener.class)
public class Queue {


    public Queue(){}
    public Queue(String notificationMail, String sqlString) {
        this.notification_mail = notificationMail;
        this.sql_string = sqlString;
    }

    public Queue(String sql_string) {
        this.sql_string = sql_string;
    }

    public enum QueueState {
        WAITING, PROCESSING, PROCESSED, FAILED
    }
    @Column(name = "QUERY_TYPE")
    private String queryType;

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "JOB_OID")
    private String job_oid;


    @Column(name = "CREATED_AT")
    @CreatedDate
    private Date created_at;

    @Column(name = "START_DATE")
    private Date start_date;

    @Column(name = "END_DATE")
    private Date end_date;

    public Date getStart_date() {
        return this.start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return this.end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    @Column(name = "NOTIFICATION_MAIL")

    private String notification_mail;

    @Column(name = "SQL_STRING")
    private String sql_string;

    @Column(name = "STATE")
    @Enumerated(EnumType.STRING)
    private QueueState state;

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public QueueState getState() {
        return state;
    }

    public void setState(QueueState state) {
        this.state = state;
    }

    public String getNotification_mail() {
        return notification_mail;
    }

    public void setNotification_mail(String notification_mail) {
        this.notification_mail = notification_mail;
    }

    public String getSql_string() {
        return sql_string;
    }

    public void setSql_string(String sql_string) {
        this.sql_string = sql_string;
    }

    public String getJob_oid() {
        return job_oid;
    }

    public void setJob_oid(String job_oid) {
        this.job_oid = job_oid;
    }
}