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
    public String getPlayListRequest(Long uid) {
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
    public String getUserPlayHistory(Long uid, boolean weekData){
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("uid", uid.toString()));
        int flag = weekData ? 1 : 0 ;
        parameters.add(new BasicNameValuePair("type", Integer.toString(flag)));
        return getRequest("/user/record",parameters);
    }
    public static Long ParseUid (String response) {
        JSONObject object = JSON.parseObject(response);
        JSONObject account = JSON.parseObject((object.getString("account")));
        Long uid = account.getLong("id");
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

        Long uid = ParseUid(response);
        return uid;
    }


    public JSONArray getsimiSongs(Long uid) {
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("id", uid.toString()));
        JSONArray jsonArray;
        JSONObject object = JSON.parseObject(getRequest("/simi/song", parameters));
        jsonArray = object.getJSONArray("songs");

//        [{
//            name:
//            artlists:[{
//              name:
//              id:
//            }]
//            album:{
//              name:
//              type:
//              picUrl:
//            }
//        }]
        JSONArray jsonArray1 = new JSONArray();
        for(int i = 0; i < jsonArray.size();++i){
            JSONObject jsonObject = new JSONObject();
            JSONObject artlist = new JSONObject();
            JSONObject album = new JSONObject();
            JSONArray artlists = new JSONArray();
            jsonObject.put("name",jsonArray.getJSONObject(i).getString("name"));
            album.put("name",jsonArray.getJSONObject(i).getJSONObject("album").getString("name"));
            album.put("type",jsonArray.getJSONObject(i).getJSONObject("album").getString("type"));
            album.put("picUrl",jsonArray.getJSONObject(i).getJSONObject("album").getString("picUrl"));
            jsonObject.put("album",album);
            JSONArray art = jsonArray.getJSONObject(i).getJSONArray("artists");
            for(int j = 0;j<art.size();j++){
                artlist.put("name",art.getJSONObject(j).getString("name"));
                artlist.put("id",art.getJSONObject(j).getString("id"));
                artlists.add(artlist);
            }
            jsonObject.put("artlists",artlists);
            jsonArray1.add(jsonObject);
        }
        return jsonArray1;
    }
//    @Transactional
//    public void  test(String ph,String pw)
//    {
//
//
//        NetEaseCrawler netEaseCrawler = new NetEaseCrawler(null, null);
//
//
//        Integer uid = getuid(ph,pw);
//
//
//
//        Long wid = (long) uid;
//        wyyhistoryDao.deletebywyyid(wid);
//
//        String playHistoryRaw = netEaseCrawler.getUserPlayHistory(uid,false);
//        JSONArray jsonArray;
//        jsonArray = JSONObject.parseObject(playHistoryRaw).getJSONArray("allData");
//        for(int i=0;i<jsonArray.size();i++) {
//
//
//            Long mid = jsonArray.getJSONObject(i).getJSONObject("song").getLong("id");
//            String name = jsonArray.getJSONObject(i).getJSONObject("song").getString("name");
//            Integer playcount = jsonArray.getJSONObject(i).getInteger("playCount");
//            Integer score = jsonArray.getJSONObject(i).getInteger("score");
//            jsonArray.getJSONObject(i).getInteger("score");
//            musicsDao.addmusic(mid,name);
//            JSONArray jsonArray1 = jsonArray.getJSONObject(i).getJSONObject("song").getJSONArray("ar");
//            for(int j=0;j<jsonArray1.size();j++){
//                String sname = jsonArray1.getJSONObject(j).getString("name");
//                Long sid = jsonArray1.getJSONObject(j).getLong("id");
//                singDao.addsing(mid,sid,sname);
//            }
//            wyyhistoryDao.addhistory(wid,mid,playcount,score);
//
//        }
//    }
    @Transactional
    public void  crawlbyid(Long uid)
    {


        NetEaseCrawler netEaseCrawler = new NetEaseCrawler(null, null);





        Long wid =  uid;
        wyyhistoryDao.deletebywyyid(wid);

        String playHistoryRaw = netEaseCrawler.getUserPlayHistory(uid,false);
        JSONArray jsonArray;
        jsonArray = JSONObject.parseObject(playHistoryRaw).getJSONArray("allData");

        for(int i=0;i<jsonArray.size();i++) {


            Long mid = jsonArray.getJSONObject(i).getJSONObject("song").getLong("id");
            String name = jsonArray.getJSONObject(i).getJSONObject("song").getString("name");
            Integer playcount = jsonArray.getJSONObject(i).getInteger("playCount");
            Integer score = jsonArray.getJSONObject(i).getInteger("score");
            musicsDao.addmusic(mid,name);
            JSONArray jsonArray1 = jsonArray.getJSONObject(i).getJSONObject("song").getJSONArray("ar");
            for(int j=0;j<jsonArray1.size();j++){
                String sname = jsonArray1.getJSONObject(j).getString("name");
                Long sid = jsonArray1.getJSONObject(j).getLong("id");
                singDao.addsing(mid,sid,sname);
            }
            wyyhistoryDao.addhistory(wid,mid,playcount,score);

        }
    }




}