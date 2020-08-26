package com.ilife.taobaoservice.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stats {
    Order mostExpensive;
    Double expenseMonth;
    Double expenseYear;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Category{
        List<Long> orderIds;
        Double expense;
        public void addOrder(Order order){
            orderIds.add(order.getId());
            expense += order.getTotal();
        }
    }
    Map<String, Category> categories;
}
