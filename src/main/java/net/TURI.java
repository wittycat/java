package net;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Administrator on 2017/4/22.
 */
public class TURI {
    public static void main(String[] args) {
        try {
            URI uri = new URI("https://www.baidu.com");
            System.out.println(uri.getHost());
            try {
                System.out.println(uri.toURL().getHost());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            System.out.println(uri.getHost());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
