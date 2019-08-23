package com.tax.verify.job;

import com.tax.verify.api.DataController;
import com.tax.verify.dto.Data;
import com.tax.verify.jpa.DataRepositoryImp;
import com.tax.verify.jpa.GetHttpResponse;
import com.tax.verify.jpa.IndexRepository;
import com.tax.verify.jpa.pojo.Queue;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PreDestroy;
import java.util.List;

@Component
public class StartUpInit {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DataRepositoryImp dataRepositoryImp;

    @Autowired
    IndexRepository dataRepository;

    @Autowired
    Queue queue;

    @PreDestroy
    public List<Queue> findUnprocessed(){

    }



}
