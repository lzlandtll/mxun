package com.mxun.art.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.Valid;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;

@Configuration
public class ElasticsearchConfig {

    @Value("${custom.elasticsearch.username}")
    private String username;

    @Value("${custom.elasticsearch.password}")
    private String password;

    @Value("${custom.elasticsearch.host}")
    private String host;

    @Value("${custom.elasticsearch.port}")
    private Integer port;

    @Value("${custom.elasticsearch.protocol}")
    private String protocol;


    @Bean
    public ElasticsearchClient elasticsearchClient2() throws Exception {
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(username, password));

        // 创建一个信任所有证书的 SSLContext
        SSLContext sslContext = SSLContextBuilder.create()
                .loadTrustMaterial((chain, authType) -> true) // 信任所有证书（仅用于开发环境）
                .build();

        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule()) // 注册 Java 8 时间模块
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        JacksonJsonpMapper mapper = new JacksonJsonpMapper(objectMapper);

        // 配置 RestClient 并应用自定义的 HttpClient
        RestClientBuilder builder = RestClient.builder(
                new HttpHost(host, port, protocol)
        ).setHttpClientConfigCallback(httpClientBuilder -> {
            // 使用我们自定义的 HttpClient
            return httpClientBuilder.setSSLContext(sslContext)
                    .setDefaultCredentialsProvider(credentialsProvider)
                    .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
        });

        // 创建 Elasticsearch 客户端
        return new ElasticsearchClient(
                new RestClientTransport(builder.build(), mapper)
        );
    }
}