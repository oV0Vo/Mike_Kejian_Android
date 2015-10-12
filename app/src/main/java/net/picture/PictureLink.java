package net.picture;

/**
 * Created by showjoy on 15/10/11.
 */
public class PictureLink {
    private String linkurl;                   //原图的链接
    private String t_url;                     //缩略图的链接
    private String s_url;                     //展示图的链接

    public PictureLink(String linkurl, String t_url, String s_url) {
        this.linkurl = linkurl;
        this.t_url = t_url;
        this.s_url = s_url;
    }

    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }

    public String getT_url() {
        return t_url;
    }

    public void setT_url(String t_url) {
        this.t_url = t_url;
    }

    public String getS_url() {
        return s_url;
    }

    public void setS_url(String s_url) {
        this.s_url = s_url;
    }
}
