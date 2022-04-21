package com.ldblock.carid.config.security.jwt.token;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.ldblock.carid.config.security.jwt.exception.JwtExpiredTokenException;


public class RawAccessJwtToken implements JwtToken {
    private static Logger logger = LoggerFactory.getLogger(RawAccessJwtToken.class);
            
    private String token;
    
    public RawAccessJwtToken(String token) {
        this.token = token;
    }

    /**
     * Parses and validates JWT Token signature.
     * 
     * @throws BadCredentialsException
     * @throws JwtExpiredTokenException
     * 
     */
    public Map<String, Claim> parseClaims(String signingKey) {
    	try {
        	JWTVerifier verifier = JWT.require(Algorithm.HMAC256(signingKey)).build();
        	DecodedJWT decodeJWT = verifier.verify(this.token);
        	return decodeJWT.getClaims();
    	} catch (JWTVerificationException e) {
			throw new JwtExpiredTokenException(e.getMessage());
		}	
    }

    @Override
    public String getToken() {
        return token;
    }
}
