package com.ilife.jingdongservice.service;

import java.sql.Date;

public interface CrawlerService {
    String login(String username);
    Boolean loginCheck(String username);
    Integer fetchHistory(String username);
    Integer fetchHistoryAfter(String username, Date date);
}
