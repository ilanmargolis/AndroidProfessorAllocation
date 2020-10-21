package com.example.retrofit.repository;


import java.util.List;

public interface ResultEvent<T> {

    void onResult(List<T> tList);

    void onFail(String message);
}
