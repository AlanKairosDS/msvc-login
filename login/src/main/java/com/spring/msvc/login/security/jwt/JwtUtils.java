package com.spring.msvc.login.security.jwt;

import com.spring.msvc.login.security.services.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.util.Date;

@Component
public class JwtUtils {

  private static final Logger LOGGER = LogManager.getLogger("login");

  @Value("${msvclogin.app.jwtSecret}")
  private String jwtSecret;

  @Value("${msvclogin.app.jwtExpirationTime}")
  private int jwtExpirationMs;

  @Value("${msvclogin.app.jwtCookieName}")
  private String jwtCookie;

  public String getUserNameFromJwtToken (String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateJwtToken (String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    }
    catch (SignatureException e) {
      LOGGER.error("Invalid JWT signature: {}", e.getMessage());
    }
    catch (MalformedJwtException e) {
      LOGGER.error("Invalid JWT token: {}", e.getMessage());
    }
    catch (ExpiredJwtException e) {
      LOGGER.error("JWT token is expired: {}", e.getMessage());
    }
    catch (UnsupportedJwtException e) {
      LOGGER.error("JWT token is unsupported: {}", e.getMessage());
    }
    catch (IllegalArgumentException e) {
      LOGGER.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }

  public String generateTokenFromUsername (String username) {
    return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
  }

  public ResponseCookie generateJwtCookie (UserDetailsImpl userPrincipal) {
    String jwt = generateTokenFromUsername(userPrincipal.getUsername());

    return ResponseCookie.from(jwtCookie, jwt)
            .httpOnly(true)
            .path("/api")
            .maxAge(24 * 60 * 60)
            .build();
  }

  public String getJwtFromCookies (HttpServletRequest request) {
    Cookie cookie = WebUtils.getCookie(request, jwtCookie);
    if (cookie != null) {
      return cookie.getValue();
    }
    else {
      return null;
    }
  }

  public ResponseCookie getCleanJwtCookie () {
    return ResponseCookie.from(jwtCookie, null)
            .path("/api")
            .build();
  }
}
