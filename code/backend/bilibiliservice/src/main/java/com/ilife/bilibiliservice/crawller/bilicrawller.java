package com.ilife.bilibiliservice.crawller;

//import org.apache.commons.httpclient.NameValuePair;
//import org.apache.commons.httpclient.methods.PostMethod;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ilife.bilibiliservice.dao.BiliUserDao;
import com.ilife.bilibiliservice.dao.HistoryDao;
import com.ilife.bilibiliservice.dao.VideoDao;
import com.ilife.bilibiliservice.entity.biliuser;
import org.apache.http.HttpEntity;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.admin.SpringApplicationAdminMXBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class bilicrawller {
    @Autowired
    private BiliUserDao biliUserDao;
    @Autowired
    private HistoryDao historyDao;
    @Autowired
    private VideoDao videoDao;
    public String getloginurl() {
//    httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("http://passport.bilibili.com/qrcode/getLoginUrl");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            return EntityUtils.toString(responseEntity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public List<Cookie> loginconfirm(String oauthKey) throws IOException {
//        PostMethod postMethod = new PostMethod("http://passport.bilibili.com/qrcode/getLoginInfo") ;
//        HttpPost httpPost = new HttpPost("http://passport.bilibili.com/qrcode/getLoginInfo");
//        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
//
//
//
//        NameValuePair[] data = {
//                new NameValuePair("oauthKey",oauthKey)
//        };
//        postMethod.setRequestBody(data);
//        org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
//        httpClient.executeMethod(postMethod);
//        String result = postMethod.getResponseBodyAsString() ;
//        return result;
//    httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
        HttpPost httpPost = new HttpPost("http://passport.bilibili.com/qrcode/getLoginInfo");

        List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
        parameters.add(new BasicNameValuePair("oauthKey", oauthKey));
        // 构造一个form表单式的实体
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(formEntity);

        CloseableHttpResponse response = null;
        response = httpClient.execute(httpPost);

//        HttpGet httpGet = new HttpGet("http://api.bilibili.com/x/web-interface/history/cursor");
//        response = httpClient.execute(httpGet);
//        HttpEntity contentEntity = response.getEntity();
//        System.out.println(EntityUtils.toString(contentEntity));
        List<Cookie> cookies = cookieStore.getCookies();
        if (httpClient != null) {
            httpClient.close();
        }
        return cookies;
    }

    public JSONObject gethistory(String SessData) throws IOException {
        CloseableHttpResponse response = null;
        String SESSDATA = "SESSDATA="+SessData;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("http://api.bilibili.com/x/web-interface/history/cursor");
        httpGet.setHeader("Cookie",SESSDATA);
        response = httpClient.execute(httpGet);
        HttpEntity contentEntity = response.getEntity();
        JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(contentEntity));
        return jsonObject;
    }
    public JSONObject gethistory(String SessData,Long view_at) throws IOException {
        String uri= "http://api.bilibili.com/x/web-interface/history/cursor?view_at=" + view_at.toString();
        CloseableHttpResponse response = null;
        String SESSDATA = "SESSDATA="+SessData;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setHeader("Cookie",SESSDATA);
        response = httpClient.execute(httpGet);
        HttpEntity contentEntity = response.getEntity();
        JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(contentEntity));
        return jsonObject;
    }

    @Transactional
    public void updatehistory(String SessData) throws IOException {
//        JSONObject jsonObject = gethistory(SessData).getJSONObject("data");
//        JSONArray jsonArray = jsonObject.getJSONArray("list");
//        JSONArray jsonArray1 = gethistory(SessData,jsonObject.getJSONObject("cursor").getLong("view_at")).getJSONObject("data").getJSONArray("list");
//        jsonArray.addAll(jsonArray1);
        biliuser biliuser = getuserinform(SessData);
        Long mid = biliuser.getMid();
        biliUserDao.addUser(biliuser);
        historyDao.deleteAllbymid(mid);
        JSONObject tmp = gethistory(SessData).getJSONObject("data");
        JSONArray jsonArraytotal = tmp.getJSONArray("list");
        for(int i = 0;i<4;++i){
            Long view_at = tmp.getJSONObject("cursor").getLong("view_at");
            tmp = gethistory(SessData,view_at).getJSONObject("data");
            jsonArraytotal.addAll(tmp.getJSONArray("list"));
        }

        for (int j =0;j<jsonArraytotal.size();++j)
        {
            JSONObject tmp1 = jsonArraytotal.getJSONObject(j);
            Long oid = tmp1.getJSONObject("history").getLong("oid");
            String type = tmp1.getJSONObject("history").getString("business");
            String auther_name= tmp1.getString("author_name");
            Long auther_id = tmp1.getLong("author_mid");
            String tag_name = tmp1.getString("tag_name");
            String title = tmp1.getString("title");
            Boolean is_fav = tmp1.getBoolean("is_fav");
            videoDao.addvideo(oid,type,auther_name,auther_id,tag_name,title);
            historyDao.addhistory(mid,oid,type,is_fav);
        }
    }
    public biliuser getuserinform(String SessData) throws IOException {
        CloseableHttpResponse response = null;
        String SESSDATA = "SESSDATA="+SessData;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("http://api.bilibili.com/x/member/web/account");
        httpGet.setHeader("Cookie",SESSDATA);
        response = httpClient.execute(httpGet);
        HttpEntity contentEntity = response.getEntity();
        JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(contentEntity));
        biliuser biliuser = new biliuser();
        biliuser.setMid(jsonObject.getJSONObject("data").getLong("mid"));
        biliuser.setUname(jsonObject.getJSONObject("data").getString("uname"));
        return biliuser;
    }

}
