import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


/**
 * NetEaseCrawler
 */
public class
NetEaseCrawler {

    /* API service was deployed on own machine. simplify API
    *  and batching */
    public static  String HTTP_SCHEME = "http";
    public static  String HOST_IP = "47.97.206.169:3000";

    CloseableHttpClient httpClient = HttpClientBuilder.create().build();

    public NetEaseCrawler(String http_scheme, String hostname){
        if (http_scheme != null) {
            HTTP_SCHEME = http_scheme;
        }
        if (hostname != null){
            HOST_IP = hostname;
        }
    }
    public NetEaseCrawler(){ }
    String postRequest(String path, List<NameValuePair> parameters){
        try {
            URI uri = new URIBuilder()
                    .setScheme(HTTP_SCHEME)
                    .setHost(HOST_IP)
                    .setPath(path)
                    .setParameters(parameters)
                    .build();
            HttpPost post = new HttpPost(uri);

            CloseableHttpResponse response = httpClient.execute(post);

            HttpEntity contentEntity = response.getEntity();
            return EntityUtils.toString(contentEntity);

        }catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public String loginRequest(String phone, String password)  {
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("phone", phone));
        parameters.add(new BasicNameValuePair("password", password));
        return postRequest("/login/cellphone",parameters);
    }

    String getRequest(String path, List<NameValuePair> parameters){
        try {
            URI uri = new URIBuilder()
                    .setScheme(HTTP_SCHEME)
                    .setHost(HOST_IP)
                    .setPath(path)
                    .setParameters(parameters)
                    .build();
            System.out.println(uri.toString());
            HttpGet get = new HttpGet(uri);
            CloseableHttpResponse response = httpClient.execute(get);
            HttpEntity contentEntity = response.getEntity();
            return EntityUtils.toString(contentEntity);

        }catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }
        return null;
    }
    public String getPlayListRequest(Integer uid) {
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("uid", uid.toString()));
        return getRequest("/user/playlist",parameters);
    }
    public String getUserSubCount() {
        List<NameValuePair> parameters = new ArrayList<>();
        return getRequest("/user/subcount",parameters);
    }
    /*
     * weekData = true : only return week data
     * */
     public String getUserPlayHistory(Integer uid, boolean weekData){
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("uid", uid.toString()));
        int flag = weekData ? 1 : 0 ;
        parameters.add(new BasicNameValuePair("type", Integer.toString(flag)));
        return getRequest("/user/record",parameters);
    }
    public static Integer ParseUid (String response) {
        JSONObject object = JSON.parseObject(response);
        JSONObject account = JSON.parseObject((object.getString("account")));
        Integer uid = account.getInteger("id");
        System.out.println("uid: " + uid);
        return uid;
    }


    public static void main(String[] args) throws IOException, URISyntaxException {

        String phone = "your phone";
        String password = "your password"; // don't push this to remote

        NetEaseCrawler netEaseCrawler = new NetEaseCrawler(null, null);
        String response = netEaseCrawler.loginRequest(phone, password);
        JSONObject object = JSON.parseObject(response);
        String cookies = (String) object.get("cookie");
        System.out.println("cookie: " + cookies);

        Integer uid = ParseUid(response);


        String subCountRaw = netEaseCrawler.getUserSubCount();
        System.out.println("sub count " + subCountRaw);

        String playListRaw = netEaseCrawler.getPlayListRequest(uid);
        System.out.println("playlist: " + playListRaw);



        String playHistoryRaw = netEaseCrawler.getUserPlayHistory(uid,false);
        System.out.println("play history: " + playHistoryRaw);

    }


}