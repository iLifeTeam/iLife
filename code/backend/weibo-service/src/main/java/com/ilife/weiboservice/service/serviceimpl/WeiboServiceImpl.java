package com.ilife.weiboservice.service.serviceimpl;

import com.ilife.weiboservice.dao.WeiboDao;
import com.ilife.weiboservice.entity.Weibo;
import com.ilife.weiboservice.service.WeiboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class WeiboServiceImpl implements WeiboService {

    @Autowired
    private WeiboDao weiboDao;

    public List<Weibo> findAllByUid(Integer uid) {
        return weiboDao.findAllByUid(uid);
    }

    public void crawlWeibo(Long uid) {
        try {
            // TODO:should go to crawl.py and modify some parameter
            String[] args = new String[]{"python", "crawler\\weiboSpider\\weibo_spider\\crawl.py", uid.toString()};
            Process pr = Runtime.getRuntime().exec(args);
            //Runtime.exec 方法创建一个本机进程，并返回 Process 子类的一个实例，该实例可用来控制进程并获取相关信息。
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));//建立一个BufferedReader对象，从字符输入流中读取文本即读取python脚本
            String line;
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");//设置日期格式
            String date=df.format(new Date());
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("log/crawler_log/"+date+".log"),"GBK"));
            while ((line = in.readLine()) != null) {
                out.write(line);
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
}
