package com.tax.verify.mailSender;

import lombok.Data;

@Data
public class EmailHostConfig {
    private final String host;
    private final String port;

    public EmailHostConfig(String host, String port){
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }
}

