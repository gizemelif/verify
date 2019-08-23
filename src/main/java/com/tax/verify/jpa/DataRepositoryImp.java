package com.tax.verify.jpa;

import com.tax.verify.dto.Data;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class DataRepositoryImp  {
    @PersistenceContext
    EntityManager em;

    @Autowired
    IndexRepository ındexRepository;

    public List<Data> getSqlQuery(String sql) {
        Session session = em.unwrap(Session.class);
        SQLQuery s = session.createSQLQuery(sql);
        s.addEntity(Data.class);
        return s.list();
    }
}
