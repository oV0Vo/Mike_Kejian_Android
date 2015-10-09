package net.httpRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by kisstheraik on 15/10/9.
 */
public class HttpRequest {

    private static HttpRequest instance;

    private static HttpRequest getInstance(){

        if(instance==null){

            synchronized(instance){

                instance=new HttpRequest();
                return instance;
            }
        }else{

            return instance;
        }



    }

    public synchronized  String sentPostRequest(String url,HashMap<String,String> para){

        PrintWriter writer=null;
        BufferedReader reader=null;
        String result=null;

        try {

            URL urlObject = new URL("");
            URLConnection connection=urlObject.openConnection();
            connection.setRequestProperty("accept","*/*");
            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("connection","Keep-Alive");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.connect();

            writer=new PrintWriter(connection.getOutputStream());
            writer.write(mapToString(para));
            writer.flush();

            reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String temp=null;

            while((temp=reader.readLine())!=null){

                result+=temp;

            }

        }catch (Exception e){

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

            }
        }

        return result;






    }

    public synchronized  String sentGetRequest(String url,HashMap<String,String> para){

        String getUrl=url+"?"+mapToString(para);
        BufferedReader input=null;
        String result="";

        try {

            URL urlObject = new URL(getUrl);
            URLConnection connection=urlObject.openConnection();
            connection.setRequestProperty("accept", "*/*");//接受什么类型的介质，此处为任何类型，*为通配符
            connection.setRequestProperty("connection","Keep-Alive");//
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");//表明客户端是哪种浏览器

            connection.connect();//建立连接

            input=new BufferedReader(new InputStreamReader(connection.getInputStream()));//包装成文本字符流


            String temp=null;
            while((temp=input.readLine())!=null){

                result=result+temp;
            }

        }catch (Exception e){

        }
        finally {

            try {
                if (input != null) {

                    input.close();

                }

            }catch (Exception e){

            }
        }

        return result;


    }

    private String mapToString(HashMap<String,String> map){

        Iterator iterator=map.keySet().iterator();
        String paraString=null;

        while(iterator.hasNext()){

            String key=(String)iterator.next();
            String value=(String)map.get(key);

            paraString+=key+"="+value+"&";

        }

        return paraString.substring(0,paraString.length()-1);
    }

}
