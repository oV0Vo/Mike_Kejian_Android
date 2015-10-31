package net;

import android.content.Context;

import net.httpRequest.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import model.user.Global;

/**
 * Created by gdr on 2015/10/29.
 */
public class UpdateNetService {
    public final static String updateBaseUrl = "http://112.124.101.41:80/mike_server_v02/index.php/Home/Version/getVersionInfo/";
    public static HttpRequest httpRequest = HttpRequest.getInstance();
    public static void update(){
        String versionResult = httpRequest.sentGetRequest(updateBaseUrl,null);

        System.out.println("vsersion "+versionResult);
        try {
            JSONObject result = new JSONObject(versionResult);
            Global.serverVersion = result.getInt("version");
            Global.downloadUrl = result.getString("url");

            System.out.println("service version :"+Global.serverVersion);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
