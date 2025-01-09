package com.qbitum.template.security;
/**
 * Validated incoming api request to check availability and validity of jwt
 */
//import io.jsonwebtoken.JwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.SignatureException;
//@Component
//public class AccessTokenFilter implements WebFilter {
//
//    private static final String ACCESS_TOKEN_HEADER = "Authorization";
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        // Check if the access token is present in the header
//        String accessToken = exchange.getRequest().getHeaders().getFirst(ACCESS_TOKEN_HEADER);
//
//        if (accessToken == null || accessToken.isEmpty() || !isJwt(accessToken)) {
//            // If no access token is present or the format is wrong return unauthorized response
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//
//        // If the access token is present, proceed to the next filter or endpoint
//        return chain.filter(exchange);
//    }
//
//    private boolean isJwt(String accesstoken) {
//        //  For checking a given token is of JWT format
//        //  Note that we are not checking validity with the signature
//        if (!accesstoken.startsWith("Bearer "))
//            return false;
//        String token = accesstoken.substring("Bearer ".length());
//        String[] jwtSplitted = token.split("\\.");
//        if (jwtSplitted.length != 3) // A JWT is composed of three parts
//            return false;
//        try {
//            String jsonFirstPart = new String(Base64.getDecoder().decode(jwtSplitted[0]));
//            JSONObject firstPart = new JSONObject(jsonFirstPart);       // The first part of the JWT is a JSON
//            if (!firstPart.has("alg"))      // The first part has the attribute "alg"
//                return false;
//            String jsonSecondPart = new String(Base64.getDecoder().decode(jwtSplitted[1]));
//            JSONObject secondPart = new JSONObject(jsonSecondPart);     // The first part of the JWT is a JSON
//            if (!secondPart.has("typ"))
//                return false;
//            //  Add more validations if necessary
//        }catch (JSONException err){
//            return false;
//        }
//        return true;
//    }
//}
