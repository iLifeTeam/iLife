package com.ilife.taobaoservice.service;

import java.sql.Date;

public interface CrawlerService {
    String login(String username, String password);
    Integer fetchHistory(String username);
    Integer fetchHistoryAfter(String username, Date date);
}
