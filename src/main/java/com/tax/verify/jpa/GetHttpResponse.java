package com.tax.verify.jpa;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tax.verify.dto.Data;
import com.tax.verify.dto.VD;
import com.tax.verify.mailSender.EmailSender;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import static com.tax.verify.mailSender.EmailSender.gmail_config;

@Service
public class GetHttpResponse {
    private static EmailSender mailer;

    static {
        try {
            mailer = new EmailSender(gmail_config, ImmutablePair.of("errorverifyvkn@gmail.com","gvgGroup!!*"));
        } catch (AddressException e) {
            e.printStackTrace();
        }
    }

    public List<Data> getResponseNullPlate(List<Data> newList) throws MessagingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<Data> myDatas = new ArrayList<>();
        VD vd = new VD();

        for (int i = 0; i < newList.size(); i++) {
            Data myData = new Data();
            try {
                String governmentNum = newList.get(i).getTckn();
                governmentNum = governmentNum.replace(" ","");
                String plate = "";
                String responseString = "";
                String vd_vdkodu = "";

                AtomicReference<Boolean> isFound = new AtomicReference<>(false);

                while (true){
                    for(int j = 1; j < 82; j++) {
                        plate = String.valueOf(j);

                        HttpResponse jsonResponse = Unirest.get("http://192.168.1.31:8687/vd?tc=" + governmentNum.trim() + "&plate=" + plate )
                                .header("accept", "application/json")
                                .header("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                                .header("Connection", "keep-alive")
                                .socketTimeout(120000)
                                .asJson();

                        responseString = jsonResponse.getBody().toString();

                        vd = mapper.readValue(responseString, new TypeReference<VD>() {
                        });
                        if( vd.getData().getDurum_text().equals("FAAL") || vd.getData().getDurum_text().equals("TERK")){
                            newList.get(i).setPlaka(plate);
                            newList.get(i).setVd_vdkodu(vd_vdkodu);
                        }
                        continue;
                    }
                    break;
                }

                if( vd.getData().getTckn().length() == 0 || vd.getData().getTckn().length() < 11 || vd == null || vd.getData().getTckn() == null || vd.getData().getVdkodu() == null || vd.getData().getVdkodu().length() == 0){

                    if(!isFound.get()){
                        vd = new VD();
                        myData.setDurum_text("N/A");
                        myData.setTckn(governmentNum);
                        myData.setUnvan("N/A");
                        myData.setVdkodu("N/A");
                        myData.setVkn("N/A");
                        myData.setOid(newList.get(i).getOid());
                        myData.setPlaka("N/A");

                        vd.setData(myData);

                        myDatas.add(myData);
                        continue;
                    }
                }
                isFound.set(true);
                vd.getData().setPlaka(newList.get(i).getPlaka());
                vd.getData().setOid(newList.get(i).getOid());
                vd.getData().setVkn(vd.getData().getVkn());
                vd.getData().setVd_vdkodu(vd_vdkodu);
                myData = vd.getData();

            } catch (Exception e) {

                e.printStackTrace();

                mailer.sendEmail("gizemelif.atalay@gvg.com.tr", "HTTP Response hatası", e.toString());

                System.out.println(e.toString());
            }

            myDatas.add(myData);
        }
        return myDatas;
    }
    public List<Data> getResponseVknNullPlate(List<Data> newList) throws MessagingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<Data> myDatas = new ArrayList<>();
        VD vd = new VD();

        for(int i = 0; i < newList.size(); i++){
            Data myData = new Data();

            try{
                String taxNumber = newList.get(i).getVd_vkn();
                taxNumber = taxNumber.replace(" ","");
                String plate = "";
                String responseString = "";
                String vd_vdkodu = "";

                AtomicReference<Boolean> isFound = new AtomicReference<>(false);

                while (true){
                    for(int j = 1; j < 82; j++) {
                        plate = String.valueOf(j);

                        HttpResponse jsonResponse = Unirest.get("http://192.168.1.31:8687/vd?vkn=" + taxNumber.trim() + "&plate=" + plate)
                                .header("accept", "application/json")
                                .header("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                                .header("Connection", "keep-alive")
                                .socketTimeout(120000)
                                .asJson();

                            responseString = jsonResponse.getBody().toString();
                            vd = mapper.readValue(responseString, new TypeReference<VD>() {
                            });
                        if( vd.getData().getDurum_text().equals("FAAL") || vd.getData().getDurum_text().equals("TERK") ){
                            newList.get(i).setPlaka(plate);
                            newList.get(i).setVd_vdkodu(vd_vdkodu);
                        }
                        continue;
                    }
                    break;
                }
                if( vd.getData().getVkn().length() == 0 || vd.getData().getVkn().length() < 10 || vd == null || vd.getData().getVkn() == null || vd.getData().getVdkodu() == null || vd.getData().getVdkodu().length() == 0 || vd.getData().getDurum_text() == null || vd.getData().getDurum_text().length() == 0 ){

                    if(!isFound.get()){
                        vd = new VD();
                        myData.setVd_vkn(taxNumber);
                        myData.setVd_unvan_donen("N/A");
                        myData.setVd_tc_donen("N/A");
                        myData.setVd_fiili_durum_donen("N/A");
                        myData.setVd_vdkodu("N/A");
                        myData.setOid(newList.get(i).getOid());
                        myData.setPlaka("N/A");

                        vd.setData(myData);

                        myDatas.add(myData);
                        continue;
                    }
                }
                isFound.set(true);

                vd.getData().setOid(newList.get(i).getOid());
                vd.getData().setPlaka(newList.get(i).getPlaka());
                vd.getData().setVd_tc_donen(vd.getData().getTckn());
                vd.getData().setVd_vkn(newList.get(i).getVd_vkn().trim());
                vd.getData().setVd_fiili_durum_donen(vd.getData().getDurum_text());
                vd.getData().setVd_unvan_donen(vd.getData().getUnvan());
                vd.getData().setVd_vdkodu(newList.get(i).getVd_vdkodu());

                myData = vd.getData();

            }catch (Exception e){
                e.printStackTrace();
                System.out.println(e.toString());
                mailer.sendEmail("gizemelif.atalay@gvg.com.tr", "HTTP Response hatası", e.toString());
            }
            myDatas.add(myData);
        }
        return myDatas;
    }

    public List<Data> getResponseVkn(List<Data> newList) throws MessagingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<Data> myDatas = new ArrayList<>();
        for (int i = 0; i < newList.size(); i++) {
            Data myData = new Data();

            try {

                String taxNumber = newList.get(i).getVd_vkn();
                taxNumber = taxNumber.replace(" ","");

                if(newList.get(i).getPlaka() == null || newList.get(i).getPlaka().length() == 0){
                    getResponseVknNullPlate(newList);
                }else{
                    AtomicReference<Boolean> isFound = new AtomicReference<>(false);

                    HttpResponse jsonResponse = Unirest.get("http://192.168.1.31:8687/vd?vkn=" + taxNumber.trim() + "&plate=" + newList.get(i).getPlaka())
                            .header("accept", "application/json")
                            .header("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                            .header("Connection", "keep-alive")
                            .socketTimeout(120000)
                            .asJson();

                    String responseString = jsonResponse.getBody().toString();

                    VD vd = mapper.readValue(responseString, new TypeReference<VD>() {
                    });

                    if( vd.getData().getVkn().length() == 0 || vd.getData().getVkn().length() < 10 || vd == null || vd.getData().getVkn() == null || vd.getData().getVdkodu() == null || vd.getData().getVdkodu().length() == 0 || vd.getData().getDurum_text() == null || vd.getData().getDurum_text().length() == 0 ){

                        if(!isFound.get()){
                            vd = new VD();
                            myData.setVd_vkn(taxNumber);
                            myData.setVd_unvan_donen("N/A");
                            myData.setVd_tc_donen("N/A");
                            myData.setVd_fiili_durum_donen("N/A");
                            myData.setVd_vdkodu("N/A");
                            myData.setOid(newList.get(i).getOid());
                            myData.setPlaka(newList.get(i).getPlaka());

                            vd.setData(myData);

                            myDatas.add(myData);
                            continue;
                        }
                    }
                    isFound.set(true);

                    vd.getData().setOid(newList.get(i).getOid());
                    vd.getData().setPlaka(newList.get(i).getPlaka());
                    vd.getData().setVd_tc_donen(vd.getData().getTckn());
                    vd.getData().setVd_vkn(newList.get(i).getVd_vkn().trim());
                    vd.getData().setVd_fiili_durum_donen(vd.getData().getDurum_text());
                    vd.getData().setVd_unvan_donen(vd.getData().getUnvan());
                    vd.getData().setVd_vdkodu(vd.getData().getVdkodu());

                    myData = vd.getData();
                }

            } catch (Exception e) {

                e.printStackTrace();
                System.out.println(e.toString());
                mailer.sendEmail("gizemelif.atalay@gvg.com.tr", "HTTP Response hatası", e.toString());
            }
            myDatas.add(myData);
        }
        return myDatas;
    }

    public List<Data> getResponse(List<Data> newList) throws MessagingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<Data> myDatas = new ArrayList<>();
        for (int i = 0; i < newList.size(); i++) {
            Data myData = new Data();
            try {
                String governmentNum = newList.get(i).getTckn();
                governmentNum = governmentNum.replace(" ","");

                if(newList.get(i).getPlaka() == null || newList.get(i).getPlaka().length() == 0){
                    getResponseNullPlate(newList);
                }else{
                    AtomicReference<Boolean> isFound = new AtomicReference<>(false);

                    HttpResponse jsonResponse = Unirest.get("http://192.168.1.31:8687/vd?tc=" + governmentNum.trim() + "&plate=" + newList.get(i).getPlaka())
                            .header("accept", "application/json")
                            .header("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                            .header("Connection", "keep-alive")
                            .socketTimeout(120000)
                            .asJson();

                    String responseString = jsonResponse.getBody().toString();

                    VD vd = mapper.readValue(responseString, new TypeReference<VD>() {
                    });

                    if( vd.getData().getTckn().length() == 0 || vd.getData().getTckn().length() < 11 || vd == null || vd.getData().getTckn() == null || vd.getData().getVdkodu() == null || vd.getData().getVdkodu().length() == 0){

                        if(!isFound.get()){
                            vd = new VD();
                            myData.setDurum_text("N/A");
                            myData.setTckn(governmentNum);
                            myData.setUnvan("N/A");
                            myData.setVdkodu("N/A");
                            myData.setVkn("N/A");
                            myData.setOid(newList.get(i).getOid());
                            myData.setPlaka(newList.get(i).getPlaka());

                            vd.setData(myData);

                            myDatas.add(myData);
                            continue;
                        }
                    }
                    isFound.set(true);
                    vd.getData().setOid(newList.get(i).getOid());
                    vd.getData().setPlaka(newList.get(i).getPlaka());
                    vd.getData().setVkn(vd.getData().getVkn());
                    myData = vd.getData();
                }
            } catch (Exception e) {

                e.printStackTrace();

                mailer.sendEmail("gizemelif.atalay@gvg.com.tr", "HTTP Response hatası", e.toString());

                System.out.println(e.toString());

            }

            myDatas.add(myData);
        }
        return myDatas;
    }
}
