package com.ilife.musicservice.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ilife.musicservice.dao.MusicsDao;
import com.ilife.musicservice.dao.SingDao;
import com.ilife.musicservice.dao.WyyhistoryDao;
import com.ilife.musicservice.entity.musics;
import com.ilife.musicservice.entity.sing;
import com.ilife.musicservice.repository.MusicsRepository;
import com.ilife.musicservice.repository.SingReposittory;
import com.ilife.musicservice.repository.WyyhistoryRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


/**
 * NetEaseCrawler
 */
@Service
public class
NetEaseCrawler {
    @Autowired
    private MusicsDao musicsDao;

    @Autowired
    private WyyhistoryDao wyyhistoryDao;

    @Autowired
    private SingDao singDao;

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


    public Long getuid(String ph,String pw)
    {
        String phone = ph;
        String password = pw; // don't push this to remote

        NetEaseCrawler netEaseCrawler = new NetEaseCrawler(null, null);
        String response = netEaseCrawler.loginRequest(phone, password);
        JSONObject object = JSON.parseObject(response);
        Integer code = object.getInteger("code");
        if (code != 200) {
            return (long)-1;
        }
        String cookies = (String) object.get("cookie");
        System.out.println("cookie: " + cookies);

        Integer uid = ParseUid(response);
        return uid.longValue();
    }
    @Transactional
    public void  test(String ph,String pw)
    {
        String phone = ph;
        String password = pw; // don't push this to remote

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
        JSONArray jsonArray;
        jsonArray = JSONObject.parseObject(playHistoryRaw).getJSONArray("allData");
        for(int i=0;i<jsonArray.size();i++) {
//            System.out.println("play history: "+jsonArray.getJSONObject(i).getJSONObject("song").getString("name"));
//            System.out.println("play history: "+jsonArray.getJSONObject(i).getJSONObject("song").getBigInteger("id"));
//            System.out.println("play history: "+jsonArray.getJSONObject(i).getInteger("playCount"));
//            System.out.println("play history: "+jsonArray.getJSONObject(i).getInteger("score"));
            Long wid = (long) uid;
            Long mid = jsonArray.getJSONObject(i).getJSONObject("song").getLong("id");
            String name = jsonArray.getJSONObject(i).getJSONObject("song").getString("name");
            Integer playcount = jsonArray.getJSONObject(i).getInteger("playCount");
            Integer score = jsonArray.getJSONObject(i).getInteger("score");
            jsonArray.getJSONObject(i).getInteger("score");
            musicsDao.addmusic(mid,name);
            JSONArray jsonArray1 = jsonArray.getJSONObject(i).getJSONObject("song").getJSONArray("ar");
            for(int j=0;j<jsonArray1.size();j++){
                String sname = jsonArray1.getJSONObject(j).getString("name");
                Long sid = jsonArray1.getJSONObject(j).getLong("id");
                singDao.addsing(mid,sid,sname);
            }
            wyyhistoryDao.addhistory(wid,mid,playcount,score);

        }




//        musicsDao.addmusic((long)6,"其实都没有");
//        wyyhistoryDao.addhistory((long)41778610,(long)3,5,5);
    }

    public static void main(String[] args) throws IOException, URISyntaxException {

        String phone = "18679480337";
        String password = "Xiong0608"; // don't push this to remote

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
        JSONArray jsonArray;
        jsonArray = JSONObject.parseObject(playHistoryRaw).getJSONArray("allData");
        System.out.println("play history: "+jsonArray.size());

    }


}