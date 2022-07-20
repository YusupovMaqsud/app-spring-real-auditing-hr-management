package uz.pdp.appspringrealauditinghrmanagement.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.pdp.appspringrealauditinghrmanagement.entity.Role;

import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {

    //TOKEN YASASH
    private static final long expireTime=1000*60*60*24;
    private static final String kalitSuz="XechkimBilmasin";

    public String generateToken(String username, Set<Role> roles){
        Date expireDate=new Date(System.currentTimeMillis()+expireTime);

        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .claim("roles", roles)
                .signWith(SignatureAlgorithm.HS512, kalitSuz)
                .compact();
        return token;
    }



    //TOKENDAN USERNAMENI OLIB BERISH
    public String getEmailFromToken(String token){
        try {
            String email = Jwts
                    .parser()
                    .setSigningKey(kalitSuz)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return email;
        }catch (Exception e){
            return null;
        }
    }
}
