package com.xming.gymclubsystem.auth.jwt;

import com.xming.gymclubsystem.auth.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Jwt Token生成
 * JWT Token 组成：header.payload.signature
 * header组成：Token类型和算法名称，
 * {"alg": "HS265","typ": "JWT"}
 * payload的格式（用户名、创建时间、生成时间）：
 * {"sub":"ming","created":,"exp":}
 *
 * @author Xiaoming.
 * Created on 2019/03/12 23:29.
 */
@Component
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "iat";

    // the part after "Bearer "
    public static final int TOKEN_HEAD_LENGTH = 7;

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    private Date generateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }

    private String doGenerateToken(Map<String, Object> claims) {

        final Date createdDate = new Date(System.currentTimeMillis());
        final Date expirationDate = generateExpirationDate(createdDate);

        return Jwts.builder()
                .setClaims(claims)
                /*.setSubject(subject)*/
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claim = new HashMap<>();
        claim.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        return doGenerateToken(claim);
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        //Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

        MyUserDetails user = (MyUserDetails) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);
        return (
                username.equals(user.getUsername())
                        && !isTokenExpired(token)
                        && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate())
        );
    }

    private boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private boolean ignoreTokenExpiration(String token) {
        //TODO
        return false;
    }

    public boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getIssuedAtDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset) && !isTokenExpired(token);
    }

    public String refreshToken(String token) {
/*        final Date createdDate = new Date();
        final Date expirationDate = generateExpirationDate(createdDate);*/

        final Claims claims = getClaimsFromToken(token);
/*        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);*/

        return doGenerateToken(claims);
    }
}
