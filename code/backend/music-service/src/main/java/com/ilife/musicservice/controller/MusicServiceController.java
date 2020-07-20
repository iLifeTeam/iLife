package com.ilife.musicservice.controller;

import com.ilife.musicservice.crawler.NetEaseCrawler;
import com.ilife.musicservice.entity.musics;
import com.ilife.musicservice.entity.wyyuser;
import com.ilife.musicservice.service.MusicsService;
import com.ilife.musicservice.service.SingService;
import com.ilife.musicservice.service.WyyhistoryService;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Api
public class MusicServiceController {


    @Autowired
    private WyyhistoryService wyyhistoryService;

    @Autowired
    private NetEaseCrawler netEaseCrawler;


    @PostMapping("/music/gethistorybypage")
    public Page<wyyuser> gethistorybypage(@RequestParam("page") Integer page, @RequestParam("size") Integer size, @RequestParam String ph, String pw) {
        long uid = netEaseCrawler.getuid(ph, pw).longValue();
        if (uid == -1) return null;
        Pageable pageable = PageRequest.of(page, size);
        Page<wyyuser> t = wyyhistoryService.findAllbyid(uid, pageable);
        if (t.isEmpty() == true) {
            if (t.getTotalPages() >= (page + 1)) {
                netEaseCrawler.crawlbyid(netEaseCrawler.getuid(ph,pw));
            }
            return wyyhistoryService.findAllbyid(uid, pageable);
        } else return t;
    }

    @PostMapping("/music/getid")
    public Integer getid(@RequestParam String ph, String pw) {
        return netEaseCrawler.getuid(ph, pw);
    }

    @PostMapping("/music/updatehistory")
    public boolean updatehistory(@RequestParam String ph, String pw) {
        long uid = netEaseCrawler.getuid(ph, pw);
        if (uid == -1) return false;
//        wyyhistoryService.deletebyid(uid);
        netEaseCrawler.crawlbyid(netEaseCrawler.getuid(ph,pw));
        return true;
    }


    @PostMapping("/music/gethistorybyid")
    public Page<wyyuser> gethistorybyid(@RequestParam("page") Integer page, @RequestParam("size") Integer size, @RequestParam Integer id) {
        Long uid = id.longValue();
        Pageable pageable = PageRequest.of(page, size);
        Page<wyyuser> t = wyyhistoryService.findAllbyid(uid, pageable);
        if (t.isEmpty() == true) {
            if (t.getTotalPages() >= (page + 1)) {
                netEaseCrawler.crawlbyid(id);
            }
            return wyyhistoryService.findAllbyid(uid, pageable);
        } else return t;
    }

    @PostMapping("/music/updatehistorybyid")
    public boolean updatehistorybyid(@RequestParam Integer id) {
        netEaseCrawler.crawlbyid(id);
        return true;
    }

}
