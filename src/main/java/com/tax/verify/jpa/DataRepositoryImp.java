package com.tax.verify.jpa;

import com.tax.verify.dto.Data;
import com.tax.verify.job.Scheduler;
import com.tax.verify.jpa.pojo.Queue;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;

import static com.tax.verify.jpa.pojo.Queue.QueueState.PROCESSED;

@Transactional
@Service
public class DataRepositoryImp {
    @PersistenceContext
    EntityManager em;
    @Autowired
    private DataRepositoryImp dataRepositoryImp;

    @Autowired
    private IndexRepository ındexRepository;

    @Autowired
    private QueueRepo queueRepo;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    Scheduler scheduler;

    @Autowired
    Queue queue;

   public List<Data> getSqlQuery(String sql) {

        Session session = em.unwrap(Session.class);
        SQLQuery s = session.createSQLQuery(sql);
        s.addEntity(Data.class);
        return s.list();
    }

    public void updateVknTable(String sql) {
        try{
            //System.out.println("(VKN)İşleme alınan sql=> "+ sql);

            List<Data> newList = getSqlQuery(sql);
            GetHttpResponse getHttpResponse = new GetHttpResponse();

            List<Data> myDatas = getHttpResponse.getResponseVkn(newList);

            for(int i=0; i < myDatas.size(); i++){

                System.out.println("************");
                ındexRepository.updateVkn(myDatas.get(i).getVd_vkn(),myDatas.get(i).getVd_unvan_donen(),myDatas.get(i).getVd_vdkodu(),myDatas.get(i).getVd_tc_donen(),myDatas.get(i).getVd_fiili_durum_donen(),myDatas.get(i).getOid());
            }

        }
        catch (Exception e){
            System.out.println("Catched exception on data repo implementer");
            e.printStackTrace();
        }
    }

    public void updateTable(String sql) {
        try{
            List<Data> newList = getSqlQuery(sql);

            GetHttpResponse getHttpResponse = new GetHttpResponse();

            List<Data> myDatas = getHttpResponse.getResponse(newList);

            for(int i=0; i < myDatas.size(); i++){

                ındexRepository.update(myDatas.get(i).getTckn(),myDatas.get(i).getUnvan(),myDatas.get(i).getVdkodu(),myDatas.get(i).getVkn(),myDatas.get(i).getDurum_text(),myDatas.get(i).getOid());

            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void printQueues(){
        List<Queue> queues = em.createQuery("SELECT q FROM Queue q").getResultList();
        queues.stream().forEach(q -> System.out.println(q.getSql_string()));
    }

}
