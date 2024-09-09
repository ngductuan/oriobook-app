package com.project.oriobook.common.configs;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class ElasticConfig {
    @Value(("${spring.elasticsearch.rest.uris}"))
    private String elasticsearchUri;

    @Bean
    public ElasticsearchClient elasticsearchClient() throws URISyntaxException {
        URI uri = new URI(elasticsearchUri);

        // Create RestClientBuilder using the parsed URI
        RestClientBuilder restClientBuilder = RestClient.builder(
            new HttpHost(uri.getHost(), uri.getPort() == -1 ? getDefaultPort(uri.getScheme()) : uri.getPort(), uri.getScheme())
        );

        // Create transport with JacksonJsonpMapper
        RestClientTransport transport = new RestClientTransport(
            restClientBuilder.build(),
            new JacksonJsonpMapper()
        );

        return new ElasticsearchClient(transport);
    }

    private int getDefaultPort(String scheme) {
        return "https".equalsIgnoreCase(scheme) ? 443 : 80;
    }
}
