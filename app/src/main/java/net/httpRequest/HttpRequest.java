package net.httpRequest;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;

import model.user.Global;

/**
 * Created by kisstheraik on 15/10/9.
 */
public class HttpRequest {

    private static HttpRequest instance;

    public static HttpRequest getInstance(){

        if(instance==null){



            instance=new HttpRequest();
            return instance;

        }else{

            return instance;
        }



    }

    public synchronized  String sentPostRequest(String url,HashMap<String,String> para){

        PrintWriter writer=null;
        BufferedReader reader=null;
        String result=null;

        try {

            URL urlObject = new URL(url);
            URLConnection connection=urlObject.openConnection();
            connection.setRequestProperty("accept","*/*");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Cookie", (String) Global.getObjectByName("cookie"));
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.connect();

            writer=new PrintWriter(connection.getOutputStream());
            JSONObject jsonObject=new JSONObject(para);
            //System.out.println("json :"+jsonObject.toString());
            writer.write(jsonObject.toString());

            writer.flush();

            reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String temp=null;
            String cookie = connection.getHeaderField("Set-cookie");

            //把cookie添加到全局变量中
            if(cookie!=null){

                Global.addGlobalItem("cookie",cookie);

            }

            while((temp=reader.readLine())!=null){

                result+=temp;

            }

            System.out.println("result :" + result);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Post 请求出错!");
            return null;
        }
        finally {

            try {

                if (reader != null) {

                    reader.close();

                }
                if (writer != null) {

                    writer.close();
                }

            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        return result;






    }
    public synchronized  String sentPostJSON(String url,JSONObject para){

        PrintWriter writer=null;
        BufferedReader reader=null;
        String result=null;

        try {

            URL urlObject = new URL(url);
            URLConnection connection=urlObject.openConnection();
            connection.setRequestProperty("accept","*/*");
            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("connection","Keep-Alive");
            connection.setRequestProperty("Cookie",(String)Global.getObjectByName("cookie"));
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.connect();

            writer=new PrintWriter(connection.getOutputStream());
            writer.write(para.toString());
            writer.flush();

            reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String temp=null;
            String cookie = connection.getHeaderField("Set-cookie");

            //把cookie添加到全局变量中
            if(cookie!=null){

                Global.addGlobalItem("cookie",cookie);

            }

            while((temp=reader.readLine())!=null){

                result+=temp;

            }

        }catch (Exception e){
            System.out.println("Post 请求出错!");
            return null;
        }
        finally {

            try {

                if (reader != null) {

                    reader.close();

                }
                if (writer != null) {

                    writer.close();
                }

            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        return result;






    }

    public synchronized  String sentGetRequest(String url,HashMap<String,String> para){

        String getUrl=url+mapToString(para);
        System.out.println(getUrl);
        BufferedReader input=null;
        String result="";

        try {

            URL urlObject = new URL(getUrl);
            URLConnection connection=urlObject.openConnection();
            connection.setRequestProperty("accept", "*/*");//接受什么类型的介质，此处为任何类型，*为通配符
            connection.setRequestProperty("connection","Keep-Alive");//
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");//表明客户端是哪种浏览器
            Object cookieObject = Global.getObjectByName("cookie");
            if(cookieObject != null){
                connection.setRequestProperty("cookie",(String)Global.getObjectByName("cookie"));
            }
            connection.connect();//建立连接

            input=new BufferedReader(new InputStreamReader(connection.getInputStream()));//包装成文本字符流

            String cookie = connection.getHeaderField("Set-cookie");

            //把cookie添加到全局变量中
            if(cookie!=null){

                Global.addGlobalItem("cookie",cookie);

            }

            String temp=null;
            while((temp=input.readLine())!=null){

                result=result+temp;
            }

            System.out.println("http result:"+result);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {

            try {
                if (input != null) {

                    input.close();

                }

            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        return result;


    }

    private String mapToString(HashMap<String,String> map){

        if(map==null||map.size()==0){

            return "";

        }

        Iterator iterator=map.keySet().iterator();
        String paraString="?";

        while(iterator.hasNext()){

            String key=(String)iterator.next();
            String value=(String)map.get(key);

            paraString+=key+"="+value+"&";

        }

        return paraString.substring(0,paraString.length()-1);
    }

    public static void main(String[] args){

        HttpRequest httpRequest=HttpRequest.getInstance();
        HashMap hashMap=new HashMap();
        hashMap.put("id","t");
        hashMap.put("i","r");

        System.out.println(httpRequest.sentPostRequest("http://112.124.101.41:80/mike_server_v02/index.php/Home/CourseQuestion/signChoiceQuestion/",hashMap));
    }

}
