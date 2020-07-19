package com.ilife.weiboservice.service.serviceimpl;

import com.ilife.weiboservice.dao.UserDao;
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
import java.util.Objects;

@Service
public class WeiboServiceImpl implements WeiboService {

    @Autowired
    private WeiboDao weiboDao;

    @Autowired
    private UserDao userDao;

    @Override
    public List<Weibo> findAllByUid(Long uid) {
        return weiboDao.findAllByUid(uid);
    }

    @Override
    public ResponseEntity<?> deleteByUid(Long uid) {
        if (userDao.findAllById(uid) == null) {
            System.out.println("user " + uid.toString() + " not exist");
            return ResponseEntity.status(501).body("user " + uid.toString() + " not exist");
        }
        weiboDao.deleteByUid(uid);
        return new ResponseEntity<>("delete all Weibos of " + uid.toString(), HttpStatus.OK);
    }

    @Override
    public Weibo findById(String id) {
        return weiboDao.findById(id);
    }

    @Override
    public void crawlWeibo(Long uid) {
        try {
            // TODO:should go to crawl.py and modify some parameter
            File file = new File(System.getProperty("user.dir")+"\\crawler\\weiboSpider\\weibo_spider\\crawl.py");
            if (file.exists()) System.out.println("exist!");
            else System.out.println("not exist!");
            File f=new File(System.getProperty("user.dir"));
            for(File value: Objects.requireNonNull(f.listFiles())){
                System.out.println(value.getName());
                if(value.getName().equals("crawler")){
                    for(File value2: Objects.requireNonNull(value.listFiles())){
                        for(File value3: Objects.requireNonNull(value2.listFiles())){
                            if(value3.getName().equals("weibo_spider")){
                                for(File value4: Objects.requireNonNull(value3.listFiles())){
                                    System.out.println(value4.getName());
                                }
                            }
                        }
                    }
                }
            }
            String[] args = new String[]{"python3", "crawler\\weiboSpider\\weibo_spider\\crawl.py", uid.toString()};
            System.out.println("start crawling");
            Process pr = Runtime.getRuntime().exec(args);
            //Runtime.exec 方法创建一个本机进程，并返回 Process 子类的一个实例，该实例可用来控制进程并获取相关信息。
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream(), StandardCharsets.UTF_8));//建立一个BufferedReader对象，从字符输入流中读取文本即读取python脚本
            String line;
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");//设置日期格式
            String date = df.format(new Date());
            //BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("log\\crawler_log\\" + date + ".log"), StandardCharsets.UTF_8));
            while ((line = in.readLine()) != null) {
//                out.write(line);
//                out.write("\n");
                System.out.println(line);
            }
            in.close();
//            out.flush();
//            out.close();
            int r2 = pr.waitFor();//等待进程运行完成再向下执行
            System.out.println("end" + r2);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public ResponseEntity<?> deleteById(String id) {
        if (weiboDao.findById(id) == null)
            return ResponseEntity.status(501).body("Weibo id " + id + " not exists");
        weiboDao.deleteById(id);
        return ResponseEntity.ok("delete Weibo " + id);
    }

    @Override
    public ResponseEntity<?> save(Weibo weibo) {
        if (findById(weibo.getId()) != null) {
            return ResponseEntity.status(501).body("Weibo id " + weibo.getId() + " already exists");
        } else {
            Weibo _weibo = weiboDao.save(weibo);
            return ResponseEntity.ok().body(_weibo.toString());
        }
    }
}
