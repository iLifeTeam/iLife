import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.jdi.event.StepEvent;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * ZhihuCrawller
 */
public class
ZhihuCrawller {

    /* API service was deployed on own machine. simplify API
    *  and batching */
    public static  String HTTP_SCHEME = "https";
    public static  String HOST_IP = "www.zhihu.com";

    CloseableHttpClient httpClient = HttpClientBuilder.create().build();

    public ZhihuCrawller(){ }
    String userInfoRequest(String username){
        try {
            URI uri = new URIBuilder()
                    .setScheme(HTTP_SCHEME)
                    .setHost(HOST_IP)
                    .setPath("people/" + username)
                    .build();
            HttpGet get = new HttpGet(uri);

            CloseableHttpResponse response = httpClient.execute(get);

            HttpEntity contentEntity = response.getEntity();
            return EntityUtils.toString(contentEntity);

        }catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }
        return null;
    }

    ZhihuUser parseUser(Document document){
         Elements userData = document.select("main > div > meta");
        ZhihuUser user = new ZhihuUser();
        user.gender = userData.get(1).attr("content");
        user.image = userData.get(2).attr("content");
        user.voteUpCount = Integer.parseInt(userData.get(3).attr("content"));
        user.thankCount = Integer.parseInt(userData.get(4).attr("content"));
        user.followerCount = Integer.parseInt(userData.get(5).attr("content"));
        user.answerCount = Integer.parseInt(userData.get(6).attr("content"));
        user.articleCount = Integer.parseInt(userData.get(7).attr("content"));
        return user;
    }
    public static void main(String[] args) throws IOException, URISyntaxException {


        ZhihuCrawller zhihuCrawller = new ZhihuCrawller();
        String response = zhihuCrawller.userInfoRequest("jin-chuan-yi-yuan-41");
        System.out.println(response);

        Document document = Jsoup.parse(response);  //  将字符串解析成Document对象
        System.out.println(document);

        ZhihuUser user = zhihuCrawller.parseUser(document);
        System.out.println(user);



    }


}