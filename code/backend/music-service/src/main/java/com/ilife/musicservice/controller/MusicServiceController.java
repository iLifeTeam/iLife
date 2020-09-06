package com.ilife.musicservice.controller;

import com.alibaba.fastjson.JSONArray;
import com.ilife.musicservice.crawler.NetEaseCrawler;
import com.ilife.musicservice.entity.musics;
import com.ilife.musicservice.entity.wyyuser;
import com.ilife.musicservice.service.MusicsService;
import com.ilife.musicservice.service.WyyhistoryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@Api
public class MusicServiceController {


    @Autowired
    private WyyhistoryService wyyhistoryService;

    @Autowired
    private MusicsService musicsService;

    @Autowired
    private NetEaseCrawler netEaseCrawler;

    @PostMapping("/music/gethistorybypage")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Page<wyyuser> gethistorybypage(@RequestParam("page") Integer page, @RequestParam("size") Integer size, @RequestParam String ph, String pw) {
        long uid = netEaseCrawler.getuid(ph, pw).longValue();
        if (uid == -1) return null;
        Pageable pageable = PageRequest.of(page, size);
        Page<wyyuser> t = wyyhistoryService.findAllbyid(uid, pageable);
//        if (t.isEmpty() == true) {
//            if (t.getTotalPages() >= (page + 1)) {
//                netEaseCrawler.crawlbyid(netEaseCrawler.getuid(ph,pw));
//            }
//            return wyyhistoryService.findAllbyid(uid, pageable);
//        }
//        else
            return t;
    }


    @PostMapping("/music/getid")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Long getid(@RequestParam String ph, String pw) {
        return netEaseCrawler.getuid(ph, pw);
    }





    @PostMapping("/music/updatehistory")
    @PreAuthorize("hasRole('ROLE_USER')")
    public boolean updatehistory(@RequestParam String ph, String pw) {
        long uid = netEaseCrawler.getuid(ph, pw);
        if (uid == -1) return false;
//        wyyhistoryService.deletebyid(uid);
        netEaseCrawler.crawlbyid(netEaseCrawler.getuid(ph,pw));
        return true;
    }


    @PostMapping("/music/gethistorybyid")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Page<wyyuser> gethistorybyid(@RequestParam("page") Integer page, @RequestParam("size") Integer size, @RequestParam Long id) {
        Long uid = id.longValue();
        Pageable pageable = PageRequest.of(page, size);
        Page<wyyuser> t = wyyhistoryService.findAllbyid(uid, pageable);
//        if (t.isEmpty() == true) {
//            if (t.getTotalPages() >= (page + 1)) {
//                netEaseCrawler.crawlbyid(id);
//            }
//            return wyyhistoryService.findAllbyid(uid, pageable);
//        }
//        else
            return t;
    }


    @PostMapping("/music/updatehistorybyid")
    @PreAuthorize("hasRole('ROLE_USER')")
    public boolean updatehistorybyid(@RequestParam Long id) {
        netEaseCrawler.crawlbyid(id);
        return true;
    }


    @PostMapping("/music/getFavorSong")
    @PreAuthorize("hasRole('ROLE_USER')")
    public musics getFavorSong(@RequestParam String ph, String pw) {
        long uid = netEaseCrawler.getuid(ph, pw);
        if (uid == -1) return null;
//        wyyhistoryService.deletebyid(uid);

        return musicsService.getFavoriteSong(uid);
    }


    @PostMapping("/music/getFavorSingers")
    @PreAuthorize("hasRole('ROLE_USER')")
    public JSONArray getFavorSingers(@RequestParam Long uid) {
        return wyyhistoryService.getFavorSingers(uid);
    }

    @GetMapping("/music/getSimiSongs")
    @PreAuthorize("hasRole('ROLE_USER')")
    public JSONArray getSimiSongs(@RequestParam Long mid) {
        return netEaseCrawler.getsimiSongs(mid);
    }

    @GetMapping("/music/getSongImage")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getImageBySid(@RequestParam Long mid) {
        return netEaseCrawler.getimage(mid);
    }

}
