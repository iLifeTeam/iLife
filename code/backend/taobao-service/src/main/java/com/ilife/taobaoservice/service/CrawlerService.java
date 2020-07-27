package com.ilife.taobaoservice.service;

import java.sql.Date;

public interface CrawlerService {
    String loginWithSms(String phone, String code);
    String  fetchSms(String phone);
    Integer fetchHistory(String username);
    Integer fetchHistoryAfter(String username, Date date);
}
