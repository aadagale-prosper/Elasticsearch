package com.example.Elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.TransportUtils;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.net.ssl.SSLContext;
import java.io.IOException;

@SpringBootApplication
public class ElasticsearchApplication {

	private static Logger logger = LoggerFactory.getLogger(ElasticsearchApplication.class);

	// URL and API key
	static String serverUrl = "https://localhost:9200";
	static String apiKey = "MTdKbTRaQUJoT21aZ1REXzJIZjM6dmNybENtcTdSV0d3MmpJZkFQdHZKUQ==";
	static String fingerprint = "b83ecee04fddd1dc9abb1c834e7758065a7cdab5cf10ea09a44c752b73afec2a";

	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchApplication.class, args);

		SSLContext sslContext = TransportUtils
				.sslContextFromCaFingerprint(fingerprint);

		BasicCredentialsProvider credsProv = new BasicCredentialsProvider();
		credsProv.setCredentials(
				AuthScope.ANY, new UsernamePasswordCredentials("elastic", "jxlQcyA4xyp7WIKyDAbf")
		);

		// Create the low-level client
		RestClient restClient = RestClient
				.builder(new HttpHost("localhost", Integer.parseInt("9200"), "https"))
				.setHttpClientConfigCallback(hc -> hc
						.setSSLContext(sslContext)
						.setDefaultCredentialsProvider(credsProv)
				).setDefaultHeaders(new Header[]{
						new BasicHeader("Authorization", "ApiKey " + apiKey)
				})
				.build();

		// Create the transport with a Jackson mapper
		ElasticsearchTransport transport = new RestClientTransport(
				restClient, new JacksonJsonpMapper());

		// And create the API client
//		ElasticsearchClient esClient = new ElasticsearchClient(transport);
//		try {
//			esClient.indices().create(c -> c
//					.index("users"+System.currentTimeMillis())
//			);
//		} catch (IOException e) {
////			throw new RuntimeException(e);
//		logger.info(e.getMessage());
//		}
	}

}
