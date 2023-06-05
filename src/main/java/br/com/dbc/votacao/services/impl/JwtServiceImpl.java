package br.com.dbc.votacao.services.impl;

import br.com.dbc.votacao.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class JwtServiceImpl implements JwtService {

    @Value("{apllication.auth.jwtSecret}")
    private String SECRET_KEY;
    @Value("{apllication.auth.jwtExpiration}")
    private String EXPIRATION_TIME;

    public String gerarToken(String nomeDoUsuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("nomeDoUsuario", nomeDoUsuario);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public boolean validarToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsuarioPeloToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return (String) claims.get("nomeDoUsuario");
    }

}
