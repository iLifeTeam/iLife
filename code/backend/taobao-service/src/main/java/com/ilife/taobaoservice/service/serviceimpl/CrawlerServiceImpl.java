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
import com.ilife.taobaoservice.service.CrawlerService;
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
import java.util.ArrayList;
import java.util.List;


@Service
public class CrawlerServiceImpl implements CrawlerService {


    public static  String HTTP_SCHEME = "http";
    public static  String HOST_IP = "0.0.0.0:8101";
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
            return EntityUtils.toString(contentEntity);
        }catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }
        return null;
    }
    public String fetchSmsRequest(String phone)  {
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("phone", phone));
        return getRequest("/login/sms/fetch",parameters);
    }
    public String loginWithSmsRequest(String phone, String code)  {
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("phone", phone));
        parameters.add(new BasicNameValuePair("code", code));
        return getRequest("/login/sms",parameters);
    }
    public String fetchAllHistoryRequest(String username)  {
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("username", username));
        return getRequest("/history/all",parameters);
    }
    public String fetchHistoryAfterRequest(String username,Date date)  {
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("username", username));
        parameters.add(new BasicNameValuePair("date",date.toString()));
        return getRequest("/history/after",parameters);
    }
//    @Override
//    public String login(String username, String password) {
//        String response = loginRequest(username, password);
//        return response;
//    }

    private Order objectToOrder(Object object){
        JSONObject orderObject = (JSONObject) object;
        Order order = new Order();
        order.setId(orderObject.getLong("orderID"));
        order.setDate(orderObject.getSqlDate("date"));
        order.setTotal(orderObject.getDouble("total"));
        order.setShop(orderObject.getString("shop"));
        return order;
    }
    private Item objectToItem(Object object){
        JSONObject itemObject = (JSONObject) object;
        Item item = new Item();
        item.setProduct(itemObject.getString("product"));
        item.setPrice(itemObject.getDouble("price"));
        item.setImgUrl(itemObject.getString("imgUrl"));
        item.setNumber(itemObject.getInteger("number"));
        return item;
    }

    private Integer saveFetchedHistory(String username, String response){
        User user = userDao.findByUsername(username);

        JSONArray array = JSON.parseArray(response);
        int count = 0;
        Date lastDate = new Date(0L);
        for (Object object: array){
            Order order = objectToOrder(object);
            if (orderDao.findById(order.getId()) != null){
                continue;
            }
            if (lastDate.before(order.getDate())){
                lastDate.setTime(order.getDate().getTime());
            }
            order.setUser(user);
            Order savedOrder = orderDao.save(order);
            JSONArray itemArray = ((JSONObject) object).getJSONArray("items");
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
    public String loginWithSms(String phone, String code) {
        return loginWithSmsRequest(phone, code);
    }

    @Override
    public String fetchSms(String phone) {
        return fetchSmsRequest(phone);
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
