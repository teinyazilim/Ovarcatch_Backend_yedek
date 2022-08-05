package com.tein.overcatchbackend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tein.overcatchbackend.domain.dto.redis.OnlineUserDto;
import com.tein.overcatchbackend.domain.vm.OnlineUser;
import com.tein.overcatchbackend.repository.RedisRepository;
import com.tein.overcatchbackend.util.DateUtil;
/*import lombok.AllArgsConstructor;*/
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class RedisService {

    private final RedisRepository redisRepository;

    public void addOnlineUser(OnlineUserDto cacheDTO){
        OnlineUser onlineUser=redisRepository.findByName(cacheDTO.getEmail());
        if(onlineUser!=null){
            redisRepository.delete(cacheDTO.getEmail());
        }
        OnlineUser user = new OnlineUser(
            cacheDTO.getEmail(),
            cacheDTO.getStatus(),
            DateUtil.toString2(LocalDateTime.now())
        );
        redisRepository.add(user);
    }

    public Map<String, String> findAll(){
        Map<Object, Object> aa = redisRepository.findAllOnlineUser();
        Map<String, String> map = new HashMap<String, String>();
        for(Map.Entry<Object, Object> entry : aa.entrySet()){
            String key = (String) entry.getKey();

            map.put(key, aa.get(key).toString());
        }
        return map;
    }

    @Async
    @Scheduled(fixedRate = 5000)
    public void updateOnlineUserExpiredDate(){
        Map<Object, Object> aa = redisRepository.findAllOnlineUser();

        for(Map.Entry<Object, Object> entry : aa.entrySet()){
            String key = (String) entry.getKey();
            OnlineUser value = new ObjectMapper().convertValue(aa.get(key), OnlineUser.class);
            LocalDateTime localDateTime=DateUtil.toDateTime(value.getTime());
            LocalDateTime current=LocalDateTime.now();
            if(localDateTime.plusMinutes(2).isBefore(current)){
                redisRepository.delete(value.getEmail());
                value.setStatus("away");
                redisRepository.add(value);
            }
            if(localDateTime.plusMinutes(4).isBefore(current)){
                redisRepository.delete(value.getEmail());
                value.setStatus("do_not_disturb");
                redisRepository.add(value);
            }
            if(localDateTime.plusMinutes(7).isBefore(current)){
                redisRepository.delete(value.getEmail());
                value.setStatus("offline");
                redisRepository.add(value);
            }
        }
    }


}
