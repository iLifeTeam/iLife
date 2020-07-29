package com.ilife.taobaoservice.service.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ilife.taobaoservice.dao.ItemDao;
import com.ilife.taobaoservice.entity.Item;
import com.ilife.taobaoservice.service.AnalyzeService;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnalyzeServiceImpl implements AnalyzeService {

    public static  String HTTP_SCHEME = "http";
    public static  String HOST_IP = "18.162.168.229:7002";
    CloseableHttpClient httpClient = HttpClientBuilder.create().build();

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
//    public String getSegmentRequest(String name)  {
//        List<NameValuePair> parameters = new ArrayList<>();
//        parameters.add(new BasicNameValuePair("name", name));
//        return getRequest("/api/categories/segment",parameters);
//    }
    public String getCategoryRequest(String name){
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("name", name));
        return getRequest("/api/categories/predict",parameters);
    }
    @Override
    public Item setCategory(Item item) {
        String response = getCategoryRequest(item.getProduct());
        JSONObject object = JSON.parseObject(response);
        item.setFirstCategory(object.getString("firstCate"));
        item.setSecondCategory(object.getString("secondCate"));
        item.setThirdCategory(object.getString("thirdCate"));
        return item;
    }
}
