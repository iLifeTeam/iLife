package com.ilife.musicservice.controller;

import com.ilife.musicservice.crawler.NetEaseCrawler;
import com.ilife.musicservice.entity.musics;
import com.ilife.musicservice.entity.wyyuser;
import com.ilife.musicservice.service.MusicsService;
import com.ilife.musicservice.service.SingService;
import com.ilife.musicservice.service.WyyhistoryService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class MusicServiceController {

    @Autowired
    private MusicsService musicsService;

    @Autowired
    private WyyhistoryService wyyhistoryService;

    @Autowired
    private NetEaseCrawler netEaseCrawler;

    @PostMapping("/music/gethistory")
    public List<wyyuser> gethistory(@RequestParam String ph, String pw) {
        long uid = netEaseCrawler.getuid(ph,pw);
        if (uid == -1) return null;
        List<wyyuser> t = wyyhistoryService.findAllbyid(uid);
        if (t.size()==0){
            netEaseCrawler.test(ph,pw);
            return wyyhistoryService.findAllbyid(uid);
        }
        else return t;
    }
    @PostMapping("/music/gethistorybypage")
    public Page<wyyuser> gethistorybypage(@RequestParam("page") Integer page,@RequestParam("size") Integer size, @RequestParam String ph, String pw) {
        long uid = netEaseCrawler.getuid(ph,pw);
        if (uid == -1) return null;
        Pageable pageable = PageRequest.of(page,size);
        Page<wyyuser> t = wyyhistoryService.findAllbyid(uid,pageable);
        if (t.isEmpty()==true){
            if(wyyhistoryService.findAllbyid(uid).size()==0) {netEaseCrawler.test(ph,pw);}
            return wyyhistoryService.findAllbyid(uid,pageable);
        }
        else return t;
    }
//    @RequestMapping("/music/dc2")
//    public musics dc2(){
//        return musicsService.findByid((long) 1);
//    }
//    @RequestMapping("/music/dc3")
//    public String crawler(){
//        netEaseCrawler.test("18679480337","Xiong0608");
//        return "yes";
//    }
//    @RequestMapping("/music/dc4")
//    public String addmusic(){
//        musicsService.addmusic((long)5,"遥远的她");
//        return "yes";
//    }
//    @RequestMapping("/music/dc5")
//    public String addsing(){
//       singService.addsing((long)5,(long)5,"张学友");
//        return "yes";
//    }
}
