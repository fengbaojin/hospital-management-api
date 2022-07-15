package com.example.demo.thread;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

// 异步执行代码
@Service("asyncExecutorTest")
public class AsyncExecutorTest {

    // 异步执行的方法, 注解内为自定义线程池类名
    @Async(value = "localBootAsyncExecutor")
    public Future<Integer> test1(Integer i) throws InterruptedException {
        Thread.sleep(100);
        System.out.println("@Async 执行:Sql" + i);
        return new AsyncResult(i);
    }
}
