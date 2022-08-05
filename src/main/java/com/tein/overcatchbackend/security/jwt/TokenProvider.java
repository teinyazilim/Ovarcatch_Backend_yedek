package com.tein.overcatchbackend.security.jwt;


import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;

import com.tein.overcatchbackend.domain.dto.redis.OnlineUserDto;
import com.tein.overcatchbackend.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;

@Component
public class TokenProvider  {

    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";
    private static final String USER_ID = "userId";

    private String secretKey;

    private long tokenValidityInMilliseconds;


    @Autowired
    private RedisService redisService;

    @PostConstruct
    public void init() {
        this.secretKey = SecurityConstants.SECRET;

        this.tokenValidityInMilliseconds = SecurityConstants.EXPIRATION_TIME;
//        this.tokenValidityInMillisecondsForRememberMe =
//            1000 * jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSecondsForRememberMe();
    }

    public String createToken(org.springframework.security.core.Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);
//        if (rememberMe) {
////            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
//        } else {
//            validity = new Date(now + this.tokenValidityInMilliseconds);
//        }
        redisService.addOnlineUser(new OnlineUserDto(authentication.getName(),"online"));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .claim(USER_ID, ((User)authentication.getPrincipal()).getUsername())
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setExpiration(validity)
                .compact();
    }

    public org.springframework.security.core.Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        Map<String,Object> details=new HashMap<>();

        if(claims.containsKey(USER_ID)){
            details.put(USER_ID,claims.get(USER_ID));
        }

        User principal =(details.containsKey(USER_ID))?
                new User(details.get(USER_ID).toString(), "", authorities):
                new User(claims.getSubject(), "", authorities);

        redisService.addOnlineUser(new OnlineUserDto(principal.getUsername(),"online"));

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(principal, token, authorities);
        usernamePasswordAuthenticationToken.setDetails(details);
        return usernamePasswordAuthenticationToken;
    }
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: {}", e);
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace: {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }

    public Claims getPayload(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}
