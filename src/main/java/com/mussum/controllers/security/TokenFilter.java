package com.mussum.controllers.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtHandlerAdapter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

public class TokenFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest sReq, ServletResponse sRes, FilterChain fc) throws IOException, ServletException {

//        fc.doFilter(sr, sr1); //IGNORANDO TOKEN ((TESTE))
        HttpServletRequest hReq = (HttpServletRequest) sReq;
        HttpServletResponse hRes = (HttpServletResponse) sRes;

        System.out.println(hReq.getMethod() + " - " + hReq.getRequestURI() + " - " + new Date() + " - ");

        final String[] GET_BLOQUEADOS = {};

        if (hReq.getMethod().equals("GET")) {
            if (!Arrays.asList(GET_BLOQUEADOS).contains(hReq.getRequestURI())) {
                System.out.println("GET liberado.");
                fc.doFilter(sReq, sRes);
                return;
            }
        }

        if (hReq.getMethod().equals("OPTIONS")) {
            System.out.println("OPTIONS liberado.");
            fc.doFilter(sReq, sRes);
            return;
        }

        System.out.println("Verificando TOKEN da requisição...");

        String header = hReq.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            hRes.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inexistente ou inválido!");
        }

        System.out.println("HEADER: " + header);

        String token = null;

        try {
            token = header.split(" ")[1];
        } catch (Exception e) {
            hRes.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inexistente ou inválido!");
        }

        String usuarioToken = null;

        try {
            Jwts.parser().setSigningKey("mussum").parseClaimsJws(token);
            usuarioToken = Jwts.parser().setSigningKey("mussm")
                    .parse(token, new JwtHandlerAdapter<String>() {
                        @Override
                        public String onClaimsJws(Jws<Claims> jws) {
                            return jws.getBody().getSubject();
                        }
                    });

            System.out.println("Request by " + usuarioToken);

        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
            if (e instanceof ExpiredJwtException) {
                System.out.println("Token expirado!" + e);
                hRes.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expirado!");
            } else {
                hRes.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido!");
            }
        }
        System.out.println("Usuario autenticado e salvo para a requisição.");
        hReq.setAttribute("requestUser", usuarioToken);

        fc.doFilter(sReq, sRes);

    }
}
