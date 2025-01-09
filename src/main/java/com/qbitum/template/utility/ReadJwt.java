package com.qbitum.template.utility;

/**
 * Actions specific to JWTs
 * To be used in all apis to get user data
 */

import java.util.Date;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.json.JSONObject;

@UtilityClass
@Slf4j
public class ReadJwt {

    public static JSONObject parseJWT(String accessToken) {
        String token = accessToken.substring("Bearer ".length());
        JSONObject jsonObject = null;

        try {
            JwtConsumer consumer = new JwtConsumerBuilder()
                .setSkipAllValidators()
                .setDisableRequireSignature()
                .setSkipSignatureVerification()
                .build();
            JwtClaims claims = consumer.processToClaims(token);

            // Convert JwtClaims to JSON Object
            jsonObject = new JSONObject(claims.toJson());

            log.debug("* Parsed token: {}", claims.getRawJson());
            log.debug(
                "* Expiration date: {}",
                new Date(claims.getExpirationTime().getValueInMillis())
            );
            log.debug(
                "* Claim Names: {}",
                claims.getClaimValueAsString("realm_access")
            );

            return jsonObject;
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
        return jsonObject;
    }
}
