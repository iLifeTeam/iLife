package com.ilife.zhihu.crawller;

import com.google.protobuf.ByteString;
import com.ilife.zhihu.service.ZhihuService;
import com.ilife.zhihu.service.serviceimpl.ZhihuServiceImpl;
import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ZhihuCrawlerServiceClient {


    private final ManagedChannel channel;
    private final ZhihuServiceGrpc.ZhihuServiceBlockingStub blockingStub;

    public ZhihuCrawlerServiceClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        blockingStub = ZhihuServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
    public String login(String username, String password){
//        ConnectivityState state = channel.getState(true);
//        System.out.println( state.toString());
        Zhihu.LoginRequest request = Zhihu.LoginRequest.newBuilder()
                .setUsername(username)
                .setPassword(password)
                .build();
        Zhihu.LoginResponse response =  blockingStub.login(request);
        return response.getResponse();
    }
    public String login(String username, String password,String captcha){
        Zhihu.LoginRequest request = Zhihu.LoginRequest.newBuilder()
                .setUsername(username)
                .setPassword(password)
                .setCaptcha(captcha)
                .build();
        Zhihu.LoginResponse response =  blockingStub.login(request);
        return response.getResponse();
    }
    /* can only be called after successful login*/
    public String getActivities(String username){
        Zhihu.ActivitiyRequest request = Zhihu.ActivitiyRequest.newBuilder()
                .setUsername(username)
                .build();
        Zhihu.ActivityResponse response = blockingStub.getActivity(request);
        return response.getResponseJson();
    }
    public static void main(String[] args) throws InterruptedException {
        ZhihuCrawlerServiceClient client = new ZhihuCrawlerServiceClient("python-crawller", 4001);
        String username = "zxy771906409@163.com";
        String password = "zxy13,./0904";
        String response = client.login(username,password);
        System.out.println(response);
        switch (response){
            case "already login":
                System.out.println("already login");
                break;
            case "success":
                System.out.println("success");
                break;
            default:
                Scanner scanner = new Scanner (System.in);
                System.out.print("Enter your captcha:");
                String captcha = scanner.next();
                System.out.println("get captcha: " + captcha);
                String okresponse = client.login(username,password,captcha);
                System.out.println(okresponse);
                break;
        }
        String activities = client.getActivities(username);
        System.out.println(activities);
    }

}
