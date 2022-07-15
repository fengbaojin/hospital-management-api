package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fengbaojin
 * 2022/2/23.
 */
public class Pager<T> {
    public long total;
    public List<T> data = new ArrayList();

    public Pager() {
    }

    public Pager(long total, List<T> data) {
        this.total = total;
        this.data = data;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }



}
