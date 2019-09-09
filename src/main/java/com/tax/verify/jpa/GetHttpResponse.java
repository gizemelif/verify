package com.tax.verify.jpa;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tax.verify.dto.Data;
import com.tax.verify.dto.VD;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class GetHttpResponse {

    public List<Data> getResponseVkn(List<Data> newList) {
        ObjectMapper mapper = new ObjectMapper();
        List<Data> myDatas = new ArrayList<>();
        for (int i = 0; i < newList.size(); i++) {
            Data myData = new Data();
            try {

                AtomicReference<Boolean> isFound = new AtomicReference<>(false);

                HttpResponse jsonResponse = Unirest.get("http://192.168.1.31:8687/vd?vkn=" + newList.get(i).getVd_vkn() + "&plate=" + newList.get(i).getPlaka())
                        .header("Accept-Encoding", "gzip, deflate, br")
                        .header("accept", "application/json")
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.157 Safari/537.36")
                        .header("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .header("Accept", "application/json, text/javascript, */*; q=0.01")
                        .header("Connection", "keep-alive")
                        .socketTimeout(120000)
                        .asJson();

                String responseString = jsonResponse.getBody().toString();

                VD vd = mapper.readValue(responseString, new TypeReference<VD>() {
                });

                if( vd == null || vd.getData().getVdkodu() == null || vd.getData().getVdkodu().length() == 0){

                    if(!isFound.get()){
                        vd = new VD();
                        myData.setVd_fiili_durum_donen("N/A");
                        myData.setVd_tc_donen("N/A");
                        myData.setVd_unvan_donen("N/A");
                        myData.setVd_vdkodu("N/A");
                        myData.setVd_vkn(newList.get(i).getVd_vkn());
                        myData.setOid(newList.get(i).getOid());
                        myData.setPlaka(newList.get(i).getPlaka());

                        vd.setData(myData);
                    }
                }
                isFound.set(true);

                vd.getData().setOid(newList.get(i).getOid());
                vd.getData().setPlaka(newList.get(i).getPlaka());
                //myData.setTckn(newList.get(i).getTckn());
                vd.getData().setVd_tc_donen(vd.getData().getTckn());
                vd.getData().setVd_vkn(vd.getData().getVkn());
                vd.getData().setVd_fiili_durum_donen(vd.getData().getDurum_text());
                vd.getData().setVd_unvan_donen(vd.getData().getUnvan());
                vd.getData().setVd_vdkodu(vd.getData().getVdkodu());

                myData = vd.getData();

                System.out.println("!!updated!!");

            } catch (Exception e) {
                e.printStackTrace();
            }
            myDatas.add(myData);
        }
        return myDatas;
    }

    public List<Data> getResponse(List<Data> newList) {
        ObjectMapper mapper = new ObjectMapper();
        List<Data> myDatas = new ArrayList<>();
        for (int i = 0; i < newList.size(); i++) {
            Data myData = new Data();
            try {

                AtomicReference<Boolean> isFound = new AtomicReference<>(false);

                HttpResponse jsonResponse = Unirest.get("http://192.168.1.31:8687/vd?tc=" + newList.get(i).getTckn() + "&plate=" + newList.get(i).getPlaka())
                        .header("Accept-Encoding", "gzip, deflate, br")
                        .header("accept", "application/json")
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.157 Safari/537.36")
                        .header("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .header("Accept", "application/json, text/javascript, */*; q=0.01")
                        .header("Connection", "keep-alive")
                        .socketTimeout(120000)
                        .asJson();

                String responseString = jsonResponse.getBody().toString();

                VD vd = mapper.readValue(responseString, new TypeReference<VD>() {
                });

                if( vd == null || vd.getData().getVdkodu() == null || vd.getData().getVdkodu().length() == 0){

                    if(!isFound.get()){
                        vd = new VD();
                        myData.setDurum_text("N/A");
                        myData.setTckn(newList.get(i).getTckn());
                        myData.setUnvan("N/A");
                        myData.setVdkodu("N/A");
                        myData.setVkn("N/A");
                        myData.setOid(newList.get(i).getOid());
                        myData.setPlaka(newList.get(i).getPlaka());

                        vd.setData(myData);
                    }
                }
                isFound.set(true);
                vd.getData().setOid(newList.get(i).getOid());
                vd.getData().setPlaka(newList.get(i).getPlaka());
                //myData.setTckn(newList.get(i).getTckn());
                vd.getData().setVkn(vd.getData().getVkn());
                myData = vd.getData();

                System.out.println("!!updated!!");

            } catch (Exception e) {
                e.printStackTrace();
            }
            myDatas.add(myData);
        }
        return myDatas;
    }
}
//--->DENEME--->
                /*if (vd != null && (vd.getData().getVdkodu() != null && vd.getData().getVdkodu().length() > 0)) {
                    isFound.set(true);
                    myData = vd.getData();
                } else {
                    myData.setDurum_text("N/A");
                    myData.setUnvan("N/A");
                    myData.setVdkodu("N/A");
                    myData.setVkn("N/A");


                    String jsonBody = jsonResponse.getBody().toString();
                    System.out.println(jsonBody);
                }

                myData.setOid(newList.get(i).getOid());
                myData.setPlaka(newList.get(i).getPlaka());
                myData.setTckn(newList.get(i).getTckn());*/