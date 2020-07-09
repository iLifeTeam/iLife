package com.ilife.zhihu.crawller;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

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
        ZhihuCrawlerServiceClient client = new ZhihuCrawlerServiceClient("127.0.0.1", 4001);

        String response = client.login(username,password);
        switch (response){
            case "already login":
                System.out.println("already login");
                break;
            case "need captcha":
                Scanner scanner = new Scanner (System.in);
                System.out.print("Enter your captcha:");
                String captcha = scanner.next();
                System.out.println("get captcha: " + captcha);
                String okresponse = client.login(username,password,captcha);
                System.out.println(okresponse);
                break;
            case "success":
                System.out.println("success");
        }
        String activities = client.getActivities(username);
        System.out.println(activities);
    }

}
