package com.ilife.alipayservice.service.serviceimpl;

import com.ilife.alipayservice.entity.Bill;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Service
public class CSVLoader {


    private static  Integer DETAIL_SKIP_LINE = 5;   // magic numbers and magic strings
    public List<Bill> loadBillsFromCSVFile(String name) {
        try {
            File detailFile = new File(name);
            InputStreamReader fileReader = new InputStreamReader(new FileInputStream(detailFile), "GBK");
            CSVReader reader = new CSVReaderBuilder(fileReader)
                    .withCSVParser(
                            new CSVParserBuilder().withSeparator(',').build())
                    .withSkipLines(DETAIL_SKIP_LINE)
                    .build();
            List<String[]> lines = reader.readAll();
            List<String[]> compactedLines = new ArrayList<>();
            for (int i = 0; i < lines.size(); i ++){
                if (lines.get(i+1)[0].charAt(0) == '#')
                    break;
                if(lines.get(i+1)[1].equals(lines.get(i)[1])){ // has same bid, remove one whose product info is empty.
                    String[] chooseLine = lines.get(i)[3].equals("\t") ? lines.get(i+1) : lines.get(i);
                    compactedLines.add(chooseLine);
                    i ++;
                }else {
                    compactedLines.add(lines.get(i));
                }
            }
            List<Bill> bills = new ArrayList<>();
            compactedLines.forEach(
                    line -> {
                        if (line[0].charAt(0) == '#')
                            return;
                        bills.add(parseStringArrayToBill(line));
                    }
            );
            return bills;
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Timestamp convertStringToTimestamp(String time){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse(time);
            return new Timestamp(parsedDate.getTime());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private Bill parseStringArrayToBill(String[] line){
        Bill bill = new Bill();
        bill.setBid(line[1]); // "业务流水号"
        bill.setShopBid(line[2]); // 商户订单号
        bill.setProductInfo(line[3]); // 商品名称
        bill.setTime(convertStringToTimestamp(line[4]));
        bill.setTargetAccount(line[5]);
        bill.setIn(Float.parseFloat(line[6]));
        bill.setOut(Float.parseFloat(line[7]));
        bill.setPaymentApproach(line[10]);
        bill.setComment(line[11]);
        return bill;
    }
}
