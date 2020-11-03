package com.example.retrofit.repository;

public interface ResultEvent {

    <T> void onResult(T result);

    void onFail(String message);
}
