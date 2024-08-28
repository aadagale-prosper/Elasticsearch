package com.example.Elasticsearch.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.TransportUtils;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.example.Elasticsearch.component.SearchElastic;
import com.example.Elasticsearch.model.User;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ElasticClient implements SearchElastic {
    private static Logger logger = LoggerFactory.getLogger(ElasticClient.class);



//    public void createIndex(String indexName){
//
//    }

    @Override
    public ResponseEntity<List<User>> searchIndex(String searchQuery){
        SearchResponse<User> search = null;
//        try {
//            search = esClient.search(s -> s
//                            .index("users")
//                            .query(q -> q
//                                    .term(t -> t
//                                            .field("name")
//                                            .value(v -> v.stringValue(searchQuery))
//                                    )),
//                    User.class);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        List<User> response = new ArrayList<>();

        for (Hit<User> hit: search.hits().hits()) {
            processProduct(hit.source());
            response.add(hit.source());
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<>());
    }


    public void processProduct(User source){
        System.out.println(source.toString());
    }

    @Override
    public ResponseEntity<Long> addUser(User user) {
        User user1 = new User("userName_1", "City bike");

        IndexResponse response = null;
        try {
            response = esClient.index(i -> i
                    .index("users")
                    .id(user1.getUsername())
                    .document(user1)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        logger.info("Indexed with version " + response.version());
        return ResponseEntity.status(HttpStatus.OK).body(1L);
    }
}
