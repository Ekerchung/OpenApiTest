package com.openapitest.config;

/**
 * @Description: 處理網頁SSL驗證
 * @author: Eker
 * @date: 2023/5/28 上午 10:54
 * @version: V1.0
 */
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

@Configuration
public class SSLConfig {

    @Bean
    public SSLContext trustAllSslContext() throws Exception {
        return SSLContextBuilder
                .create()
                .loadTrustMaterial(new TrustAllStrategy())
                .build();
    }

    @Bean
    public RestTemplate restTemplate() throws Exception {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(HttpClients.custom()
                .setSSLContext(trustAllSslContext())
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build());
        return new RestTemplate(requestFactory);
    }
}

