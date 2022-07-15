package com.example.demo.service;

import java.util.List;

public interface AsyncExecutorService {
    List<Integer> test() throws InterruptedException;
}
