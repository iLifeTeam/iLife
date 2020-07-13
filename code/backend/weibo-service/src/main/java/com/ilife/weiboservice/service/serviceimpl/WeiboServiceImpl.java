package com.ilife.weiboservice.service.serviceimpl;

import com.google.common.base.Utf8;
import com.ilife.weiboservice.dao.WeiboDao;
import com.ilife.weiboservice.entity.Weibo;
import com.ilife.weiboservice.service.WeiboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class WeiboServiceImpl implements WeiboService {

    @Autowired
    private WeiboDao weiboDao;

    @Override
    public List<Weibo> findAllByUid(Integer uid) {
        return weiboDao.findAllByUid(uid);
    }

    @Override
    public ResponseEntity<?> deleteByUid(Integer uid){
        weiboDao.deleteByUid(uid);
        return new ResponseEntity<>("delete all Weibos of "+uid.toString(), HttpStatus.OK);
    }

    @Override
    public Weibo findById(String id){
        return weiboDao.findById(id);
    }

    @Override
    public void crawlWeibo(Long uid) {
        try {
            // TODO:should go to crawl.py and modify some parameter
            String[] args = new String[]{"python", "crawler\\weiboSpider\\weibo_spider\\crawl.py", uid.toString()};
            Process pr = Runtime.getRuntime().exec(args);
            //Runtime.exec 方法创建一个本机进程，并返回 Process 子类的一个实例，该实例可用来控制进程并获取相关信息。
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream(), StandardCharsets.UTF_8));//建立一个BufferedReader对象，从字符输入流中读取文本即读取python脚本
            String line;
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");//设置日期格式
            String date=df.format(new Date());
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("log/crawler_log/"+date+".log"), StandardCharsets.UTF_8));
            while ((line = in.readLine()) != null) {
                out.write(line);
                out.write("\n");
            }
            in.close();
            out.flush();
            out.close();
            int r2 = pr.waitFor();//等待进程运行完成再向下执行
            System.out.println("end" + r2);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public ResponseEntity<?> deleteById(Integer id){
        weiboDao.deleteById(id);
        return ResponseEntity.ok("delete Weibo "+id.toString());
    }
}
