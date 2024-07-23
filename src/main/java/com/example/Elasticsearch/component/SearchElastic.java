package com.example.Elasticsearch.component;

import com.example.Elasticsearch.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "/users")
public interface SearchElastic {

    @GetMapping(value = "/{searchTerm}")
    ResponseEntity<List<User>> searchIndex(@PathVariable(value = "searchTerm") String searchTerm);

    @PostMapping(value = "/adduser")
    ResponseEntity<Long> addUser(@RequestParam User user);
}
