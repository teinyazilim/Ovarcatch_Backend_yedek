package com.tein.overcatchbackend.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tein.overcatchbackend.domain.vm.OnlineUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;

@Repository
public class RedisRepository {
    private static final String KEY = "OnlineUser";

    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations hashOperations;

    @Autowired
    public RedisRepository(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }

    public void add(final OnlineUser movie)  {
        //hashOperations.put(KEY, movie.getId(), movie.getName());

        // Map<String, Object> userHash = new ObjectMapper().convertValue(movie, new TypeReference<Map<String, Object>>() {});
        Map userHash = new ObjectMapper().convertValue(movie, Map.class);
        hashOperations.put(KEY, movie.getEmail(),userHash);

       /* ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String json = null;
        try {
            json = mapper.writeValueAsString(movie);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        hashOperations.put(KEY, movie.getName(),json);*/
    }

    public void delete(final String id) {
        hashOperations.delete(KEY, id);
    }

    public OnlineUser findEmployee(final String id){
        return (OnlineUser) hashOperations.get(KEY, id);
    }

    public Map findAllOnlineUser(){
        return hashOperations.entries(KEY);
    }

    public OnlineUser findByName(String name) {
        Map userMap = (Map) hashOperations.get(KEY, name);
        OnlineUser mov = new ObjectMapper().convertValue(userMap, OnlineUser.class);
        return mov;
    }

}
