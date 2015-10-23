package net.picture;

/**
 * Created by showjoy on 15/10/11.
 */
import android.util.Base64;

import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

//import org.apache.commons.codec.binary.Base64;

public class TokenTool {

    private static final String accessKey = "c263bfb529336e4b7fb28466b647fc46ec1053a2";
    private static final String secretKey = "da39a3ee5e6b4b0d3255bfef95601890afd80709";
    private static final long albumId = 1123248;

    private static String base64(byte[] target) {

      //b System.out.println("base64 runtime path:"+Base64.class.getProtectionDomain().getCodeSource().getLocation());

        //android.util.Base64 base64=new android.util.Base64();
       // Base64 base=Base64.;

        //System.out.println("base64 runtime path:"+Base64.class.getProtectionDomain().getCodeSource().getLocation());


        System.out.println("base");

        // return null;

        //return "hello";

        return com.ta.utdid2.android.utils.Base64.encodeToString(target,Base64.DEFAULT).replace('+', '-').replace('/', '_');

        //return Base64.encodeToString(target,Base64.DEFAULT).replace('+', '-').replace('/', '_');
       // return base64.encodeToString(target).replace('+', '-').replace('/', '_');
    }

    private static String hmac_sha1(String value, String key) {
        try {
            byte[] keyBytes = key.getBytes();
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(value.getBytes());
            return new String(base64(rawHmac)).trim();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String createToken() {
        long deadlineTime = new Date().getTime() + 3600;
        String json = "{\"deadline\":%s,\"album\":\"%s\"}";
        json = String.format(json, deadlineTime, albumId);
        String base64param = base64(json.getBytes()).trim();
        String sign = hmac_sha1(base64param, secretKey);
        String token = accessKey + ":" + sign + ":" + base64param;
        return token;
    }
}





