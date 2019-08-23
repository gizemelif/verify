package com.tax.verify.dto;

import java.util.List;

public class VD {
    private Metadata metadata;
    private Data data;
    private List<Message> messages = null;
    private String error;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

   /* @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(this.data != null){
            sb.append().append("$").append(this.getData().getDurum_text()).append("$").append(this.getData().getTckn()).append("$").append(this.getData().getUnvan()).append("$").append(this.getData().getVdkodu()).append("$").append(this.getData().getVkn()).append(this.getData().getOid()).append(this.getData().getPlaka());
        }
        else{
            sb.append(getData().getVkn());
        }
        return sb.toString();
    }*/
}
