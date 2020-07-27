package com.ilife.jingdongservice.service.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ilife.jingdongservice.dao.ItemDao;
import com.ilife.jingdongservice.dao.OrderDao;
import com.ilife.jingdongservice.dao.UserDao;
import com.ilife.jingdongservice.entity.Item;
import com.ilife.jingdongservice.entity.Order;
import com.ilife.jingdongservice.entity.User;
import com.ilife.jingdongservice.service.CrawlerService;
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
import java.sql.Date;
import java.sql.SQLSyntaxErrorException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;


@Service
public class CrawlerServiceImpl implements CrawlerService {

    public static  String HTTP_SCHEME = "http";
    public static  String HOST_IP = "47.97.206.169:8102";
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
                    .build();
            System.out.println(uri.toString());
            HttpGet get = new HttpGet(uri);
            CloseableHttpResponse response = httpClient.execute(get);
            HttpEntity contentEntity = response.getEntity();
            String resp = EntityUtils.toString(contentEntity);
            System.out.println("response: " + resp);
            return resp;
        }catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }
        return null;
    }
    public String loginRequest(String username)  {
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("username", username));
        return getRequest("/login",parameters);
    }
    public String loginCheckRequest(String username)  {
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("username", username));
        return getRequest("/login/check",parameters);
    }
    public String fetchAllHistoryRequest(String username)  {
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("username", username));
        return getRequest("/history",parameters);
    }
    public String fetchHistoryAfterRequest(String username,Date date)  {
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("username", username));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        String year = simpleDateFormat.format(date);
        System.out.println(year);
        parameters.add(new BasicNameValuePair("year",year));
        return getRequest("/history",parameters);
    }
    @Override
    public String login(String username) {
        String response = loginRequest(username);
        return response;
    }

    @Override
    public Boolean loginCheck(String username) {
        String response = loginCheckRequest(username);
        return response.equals("login");
    }

    private Order objectToOrder(Object object){
        JSONObject orderObject = (JSONObject) object;
        Order order = new Order();
        order.setId(orderObject.getLong("orderId"));
        order.setDate(orderObject.getSqlDate("date"));
        order.setTotal(orderObject.getDouble("price"));
        order.setShop(orderObject.getString("shop"));
        return order;
    }
    private Item objectToItem(Object object){
        JSONObject itemObject = (JSONObject) object;
        Item item = new Item();
        item.setProduct(itemObject.getString("name"));
        item.setPrice(itemObject.getDouble("price"));
        item.setImgUrl(itemObject.getString("img_url"));
        item.setNumber(itemObject.getInteger("number"));
        return item;
    }

    private Integer saveFetchedHistory(String username, String response){
        User user = userDao.findByUsername(username);

        JSONArray array = JSON.parseArray(response);
        int count = 0;
        Date lastDate = new Date(0L);
        for (Object object: array){
//            System.out.println(JSON.toJSONString(object));
            Order order = objectToOrder(object);
            if (orderDao.findById(order.getId()) != null){
                continue;
            }
            if (lastDate.before(order.getDate())){
                lastDate.setTime(order.getDate().getTime());
            }
            order.setUser(user);
            Order savedOrder = orderDao.save(order);
            JSONArray itemArray = ((JSONObject) object).getJSONArray("products");
            for (Object itemArrayObject: itemArray){
                JSONObject itemObject = (JSONObject) itemArrayObject;
                Item item = objectToItem(itemObject);
                item.setOrder(savedOrder);
                itemDao.save(item);
            }
            count ++;
        }
        if (user.getLastUpdateDate().before(lastDate)) {
            user.setLastUpdateDate(lastDate);
            userDao.save(user);
        }
        return count;
    }
    @Override
    public Integer fetchHistory(String username) {
        String response  = fetchAllHistoryRequest(username);
        return saveFetchedHistory(username, response);
    }
    @Override
    public Integer fetchHistoryAfter(String username, Date date) {
        String response = fetchHistoryAfterRequest(username,date);
        return saveFetchedHistory(username,response);
    }
}
