package ru.itmo.blps.DAO.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import ru.itmo.blps.services.Exceptions.ApiException;

import java.util.Date;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JWTUtils {

    public static String header;

    public static String tokenPrefix;

    public static String secret;

    public static long expireTime;

    public static final String USER_LOGIN_TOKEN = "USER_LOGIN_TOKEN";

    public void setHeader(String header) {
        JWTUtils.header = header;
    }

    public void setTokenPrefix(String tokenPrefix) {
        JWTUtils.tokenPrefix = tokenPrefix;
    }

    public void setSecret(String secret) {
        JWTUtils.secret = secret;
    }

    public void setExpireTime(int expireTimeInt) {
        JWTUtils.expireTime = expireTimeInt*1000L*60;
    }

    /**
     * create token
     * @param sub
     * @return
     */
    public static String createToken(String sub){
        return tokenPrefix + JWT.create()
                .withSubject(sub)
                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
                .sign(Algorithm.HMAC512(secret));
    }


    /**
     * Check token
     * @param token
     */
    public static String validateToken(String token){
        try {
            return JWT.require(Algorithm.HMAC512(secret))
                    .build()
                    .verify(token.replace(tokenPrefix, ""))
                    .getSubject();
        } catch (TokenExpiredException e){
            throw new ApiException("Token is useless now. You need a new one.");
        } catch (Exception e){
            throw new ApiException("Fail to check token");
        }
    }

    /**
     * Check if the token should be newed
     * @param token
     * @return
     */
    public static boolean isNeedUpdate(String token){
        Date expiresAt = null;
        try {
            expiresAt = JWT.require(Algorithm.HMAC512(secret))
                    .build()
                    .verify(token.replace(tokenPrefix, ""))
                    .getExpiresAt();
        } catch (TokenExpiredException e){
            return true;
        } catch (Exception e){
            throw new ApiException("Fail to check token");
        }
        return (expiresAt.getTime()-System.currentTimeMillis()) < (expireTime>>1);
    }
}
