package net.picture;

/**
 * Created by showjoy on 15/10/11.
 */
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import net.sf.json.JSONObject;

public class PictureUploadUtil {

    public static PictureLink upload(File picture){

        try{
            String boundary = "Boundary-b1ed-4060-99b9-fca7ff59c113";
            String Enter = "\r\n";

            FileInputStream fis = new FileInputStream(picture);

            URL url = new URL("http://up.tietuku.com/");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            conn.setRequestProperty("Content-Type","multipart/form-data;boundary=" + boundary);

            conn.connect();

            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

            //token
            String token = "--" + boundary + Enter
                    + "Content-Disposition: form-data; name=\"Token\"" + Enter + Enter
                    + TokenTool.createToken() + Enter;

            //picture
            String picturePara =  "--" + boundary + Enter
                    + "Content-Type: application/octet-stream" + Enter
                    + "Content-Disposition: form-data; filename=\""+picture.getName()+"\"; name=\"file\"" + Enter + Enter;


            byte[] xmlBytes = new byte[fis.available()];
            fis.read(xmlBytes);

            dos.writeBytes(token);
            dos.writeBytes(picturePara);
            dos.write(xmlBytes);
            dos.writeBytes(Enter + "--" + boundary + "--" + Enter + Enter);

            dos.flush();
            dos.close();
            fis.close();

            InputStream in = conn.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            JSONObject obj = JSONObject.fromObject(new String(out.toByteArray()));
            PictureLink link = new PictureLink(obj.getString("linkurl"), obj.getString("t_url"), obj.getString("s_url"));
            conn.disconnect();
            return link;

            //return null;

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;

    }

}