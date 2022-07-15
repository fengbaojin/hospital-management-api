package com.example.demo.service.impl;

import com.example.demo.service.AsyncExecutorService;
import com.example.demo.thread.AsyncExecutorTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


@Service
public class AsyncExecutorServiceImpl implements AsyncExecutorService {

    @Autowired
    AsyncExecutorTest asyncExecutorTest;


    @Override
    public List<Integer> test() throws InterruptedException {
        List<Integer> list = new ArrayList<>();
        List<Future> result = new ArrayList<>();
        System.out.println("执行开始");
        try {
            for (int i = 0; i < 5; i++) {
                Future<Integer> integerFuture = asyncExecutorTest.test1(i);
                result.add(integerFuture);
            }
            for (Future future : result) {
                Integer i = (int) future.get();
                list.add(i);
            }
            System.out.println("执行结束");
            return list;
        } catch (InterruptedException e) {
            System.out.println("service2执行出错");
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
