package com.ilife.taobaoservice.service.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ilife.taobaoservice.dao.ItemDao;
import com.ilife.taobaoservice.dao.OrderDao;
import com.ilife.taobaoservice.dao.UserDao;
import com.ilife.taobaoservice.entity.Item;
import com.ilife.taobaoservice.entity.Order;
import com.ilife.taobaoservice.entity.User;
import com.ilife.taobaoservice.service.AnalyzeService;
import org.apache.commons.text.StringEscapeUtils;
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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnalyzeServiceImpl implements AnalyzeService {

    public static  String HTTP_SCHEME = "http";
    public static  String HOST_IP = "18.162.53.235:7002";
    CloseableHttpClient httpClient = HttpClientBuilder.create().build();

    @Autowired
    UserDao userDao;
    @Autowired
    ItemDao itemDao;
    @Autowired
    OrderDao orderDao;

    String getRequest(String path, List<NameValuePair> parameters){
        try {
            URI uri = new URIBuilder()
                    .setScheme(HTTP_SCHEME)
                    .setHost(HOST_IP)
                    .setPath(path)
                    .setParameters(parameters)
                    .setCharset(StandardCharsets.UTF_8)
                    .build();
//            URI uri = new Uri(HTTP_SCHEME + "://" + HOST_IP + path + "names" );
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
        parameters.add(new BasicNameValuePair("names", name));
        return getRequest("/api/categories/predict",parameters);
    }
    @Override
    public Item setCategory(Item item) {
        String productName = StringEscapeUtils.unescapeJava(item.getProduct());
        System.out.println(productName);
        String response = getCategoryRequest(productName);
        JSONObject object = JSON.parseArray(response).getJSONObject(0);
        item.setFirstCategory(object.getString("firstCate"));
        item.setSecondCategory(object.getString("secondCate"));
        item.setThirdCategory(object.getString("thirdCate"));
        return item;
    }

    @Override
    public int updateCategory(User user) {
        List<Order> orders = orderDao.findByUser(user);
        int counter = 0;
        for (Order order : orders){
            StringBuilder requestBuilder = new StringBuilder();
            String prefix = "";
            List<Item> items = order.getItems();
            if (items.get(0).getFirstCategory() != null)
                continue;
            else
                counter += items.size();
            for (Item item: items){
                requestBuilder.append(prefix);
                prefix = ",";
                requestBuilder.append(item.getProduct());
            }
            String response = getCategoryRequest(requestBuilder.toString());
            JSONArray array =  JSON.parseArray(response);
            int i = 0;
            for (Object object: array){
                items.get(i).setFirstCategory(((JSONObject) object).getString("firstCate"));
                items.get(i).setSecondCategory(((JSONObject) object).getString("secondCate"));
                items.get(i).setThirdCategory(((JSONObject) object).getString("thirdCate"));
                itemDao.save(items.get(i));
                i ++;
            }
        }
        return counter;
    }
//    @Override
//    public int updateCategory(User user) {
//        List<Order> orders = orderDao.findByUser(user);
//        int counter = 0;
//        for (Order order : orders){
//            for (Item item: order.getItems()){
//                if (item.getFirstCategory() == null) {
//                    itemDao.save(setCategory(item));
//                    counter ++;
//                }
//            }
//        }
//        return counter;
//    }

}
