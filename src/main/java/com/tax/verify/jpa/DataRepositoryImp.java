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

import javax.mail.MessagingException;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;

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

            List<Data> newList = getSqlQuery(sql);
            GetHttpResponse getHttpResponse = new GetHttpResponse();

            newList.parallelStream().forEach( d ->{
                try {
                    List<Data> list_for_parallel = new ArrayList<>();
                    list_for_parallel.add(d);
                    /*if( d.getVd_vkn() != null){
                        list_for_parallel.add(d);
                    }*/
                    //List<Data> myDatas = getHttpResponse.getResponseVkn(list_for_parallel);
                    try{
                        //myResultList.addAll(getHttpResponse.getResponseVkn(list_for_parallel));
                        Data respData = new Data();
                        for(int i=0; i < list_for_parallel.size(); i++){
                            respData = getHttpResponse.getResponseVkn(list_for_parallel).get(i);
                        }

                        ındexRepository.updateVkn(respData.getVd_vkn(),respData.getVd_unvan_donen(),
                                respData.getVd_vdkodu(), respData.getVd_tc_donen(),
                                respData.getVd_fiili_durum_donen(),respData.getOid());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    /*for(int i=0; i < myDatas.size(); i++){

                        ındexRepository.updateVkn(myDatas.get(i).getVd_vkn(),myDatas.get(i).getVd_unvan_donen(),myDatas.get(i).getVd_vdkodu(),myDatas.get(i).getVd_tc_donen(),myDatas.get(i).getVd_fiili_durum_donen(),myDatas.get(i).getOid());
                     }*/
                } catch (Exception e) {
                e.printStackTrace();
                }
            });
            /*for(int i=0; i < myResultList.size(); i++){

                ındexRepository.updateVkn(myResultList.get(i).getVd_vkn(),myResultList.get(i).getVd_unvan_donen(),
                        myResultList.get(i).getVd_vdkodu(), myResultList.get(i).getVd_tc_donen(),
                        myResultList.get(i).getVd_fiili_durum_donen(),myResultList.get(i).getOid());
            }*/
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateTable(String sql) {
        try{

            List<Data> newList = getSqlQuery(sql);
            GetHttpResponse getHttpResponse = new GetHttpResponse();

            newList.parallelStream().forEach( d ->{
                try {
                    List<Data> list_for_parallel = new ArrayList<>();
                    list_for_parallel.add(d);
                    /*if(d.getTckn() != null){
                        list_for_parallel.add(d);
                    }*/
                    //List<Data> myDatas = getHttpResponse.getResponseVkn(list_for_parallel);
                    try{
                        //myResultList.addAll(getHttpResponse.getResponse(list_for_parallel));
                        Data respData = new Data();
                        for(int i=0; i < list_for_parallel.size(); i++){
                            respData = getHttpResponse.getResponse(list_for_parallel).get(i);
                        }
                        ındexRepository.update(respData.getTckn(),respData.getUnvan(),respData.getVdkodu(),
                                respData.getVkn(),respData.getDurum_text(),respData.getOid());

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    /*for(int i=0; i < myDatas.size(); i++){

                        ındexRepository.updateVkn(myDatas.get(i).getVd_vkn(),myDatas.get(i).getVd_unvan_donen(),myDatas.get(i).getVd_vdkodu(),myDatas.get(i).getVd_tc_donen(),myDatas.get(i).getVd_fiili_durum_donen(),myDatas.get(i).getOid());
                     }*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            /*for(int i=0; i < myResultList.size(); i++){

                ındexRepository.update(myResultList.get(i).getTckn(),myResultList.get(i).getUnvan(),myResultList.get(i).getVdkodu(),myResultList.get(i).getVkn(),myResultList.get(i).getDurum_text(),myResultList.get(i).getOid());

            }*/
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
