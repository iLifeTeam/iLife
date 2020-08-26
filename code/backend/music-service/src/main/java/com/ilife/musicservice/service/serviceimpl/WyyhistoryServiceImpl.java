package com.ilife.musicservice.service.serviceimpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ilife.musicservice.crawler.NetEaseCrawler;
import com.ilife.musicservice.dao.WyyhistoryDao;
import com.ilife.musicservice.entity.sing;
import com.ilife.musicservice.entity.wyyuser;
import com.ilife.musicservice.repository.WyyhistoryRepository;
import com.ilife.musicservice.service.WyyhistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WyyhistoryServiceImpl implements WyyhistoryService {
    @Autowired
    private WyyhistoryDao wyyhistoryDao;


    public List<wyyuser> findAllbyid(Long id){
        return wyyhistoryDao.findAllbyid(id);
    }
    public Page<wyyuser> findAllbyid(Long id, Pageable pageable){
        return wyyhistoryDao.findAllbyid(id,pageable);
    }


//    public void deletebyid(Long id) {
//        wyyhistoryDao.deletebywyyid(id);
//    }
    public JSONArray getFavorSingers(Long id){
        List<wyyuser> his =  wyyhistoryDao.findAllbyid(id);
        List<String> singers = new ArrayList<>();
        for (int i = 0; i < his.size(); i++) {
            List<sing> s = his.get(i).getMusics().getSingers();
            for(int j = 0; j < s.size();j++){
                singers.add(s.get(j).getSname());
            }
        }
        Map<String,Integer> map = new HashMap<>();
        for(String str:singers){
            Integer i = 1; //定义一个计数器，用来记录重复数据的个数
            if(map.get(str) != null){
                i=map.get(str)+1;
            }
            map.put(str,i);
        }
        List<Map.Entry<String, Integer>> infoIds =
                new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue() - o1.getValue());
//                return (o1.getKey()).toString().compareTo(o2.getKey());
            }
        });

        JSONArray array = new JSONArray();
        JSONObject jo = null;
        for(int i =0; i < infoIds.size();++i){
            jo = new JSONObject();
            jo.put("name",infoIds.get(i).getKey());
            jo.put("times",infoIds.get(i).getValue());
            array.add(jo);
        }
        return array;
    }


}