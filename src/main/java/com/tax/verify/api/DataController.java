package com.tax.verify.api;


import com.tax.verify.dto.Data;
import com.tax.verify.jpa.DataRepositoryImp;
import com.tax.verify.jpa.GetHttpResponse;
import com.tax.verify.jpa.IndexRepository;
import com.tax.verify.jpa.pojo.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("api")
public class DataController {

    @Autowired
    private DataRepositoryImp dataRepositoryImp;
    @Autowired
    private IndexRepository dataRepository;

    @GetMapping("/datas")
    @ResponseBody
    public List<Data> all(@RequestParam String sql) {
       // String sql = "select * from vd_tc_index where tc_sorulan = '42923007322' and point_status_name = 'ACIK' and il_sorulan = 'Tekirdağ'";

        List<Data> newList = dataRepositoryImp.getSqlQuery(sql);
        GetHttpResponse getHttpResponse = new GetHttpResponse();
        List<Data> myDatas = getHttpResponse.getResponse(newList);
        for(int i=0; i < myDatas.size(); i++){
            dataRepository.update(myDatas.get(i).getOid(),myDatas.get(i).getTckn(),myDatas.get(i).getUnvan(),myDatas.get(i).getVdkodu(),myDatas.get(i).getVkn(),myDatas.get(i).getDurum_text());
        }

        return newList;
    }

//post servisi parametre olarak sql ve email alıcak.. QueueJob tablosunun oid,sql,process



}
