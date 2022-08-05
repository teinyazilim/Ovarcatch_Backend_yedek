package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.dto.redis.OnlineUserDto;
import com.tein.overcatchbackend.domain.dto.redis.SearchDTO;
import com.tein.overcatchbackend.domain.vm.OnlineUser;
import com.tein.overcatchbackend.repository.RedisRepository;
import com.tein.overcatchbackend.service.CurrentUserService;
import com.tein.overcatchbackend.service.RedisService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/redis")
@AllArgsConstructor
public class RedisResource {

    private final RedisRepository redisRepository;
    private final RedisService redisService;
    private final CurrentUserService currentUserService;

    @RequestMapping("/") public String index() { return "index"; }

    @RequestMapping("/keys")
    public @ResponseBody
    Map<Object, Object> keys() {
        return redisRepository.findAllOnlineUser();
    }


    @RequestMapping("/values")
    public @ResponseBody Map<String, String> findAll() {

        System.out.println(currentUserService.getCurrentUser());

        return redisService.findAll();
    }

    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody OnlineUserDto cacheDTO) {

        redisService.addOnlineUser(cacheDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResponseEntity<String> get(@RequestBody SearchDTO searchDTO) {
        OnlineUser movie =redisRepository.findByName(searchDTO.getName());

        return new ResponseEntity(movie,HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@RequestParam("key") String key) {
        redisRepository.delete(key);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
