package com.example.demo.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

	@Async
    public void processSomethingForLong() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("I take 10 seconds to complete on a Thread named : " + Thread.currentThread().getName());
    }
	
	@Async(value = "threadPoolTaskExecutor")
	public Future<String> longRunningProcessThatReturnsUsingFuture() {
	    try {
	        Thread.sleep(20000);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	    return new AsyncResult<>("I take 10 seconds to return on a Thread named : " + Thread.currentThread().getName());
	}
	
	@Async//(value = "threadPoolTaskExecutor")
	public CompletableFuture<String> longRunningProcessThatReturnsUsingCompletableFuture() {
		System.out.println("longRunningProcessThatReturnsUsingCompletableFuture started :  "+Thread.currentThread().getName());
	    try {
	        Thread.sleep(20000);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
		System.out.println("longRunningProcessThatReturnsUsingCompletableFuture ended :  "+Thread.currentThread().getName());

	    String str = "I take 10 seconds to return on a Thread named : " + Thread.currentThread().getName();
	    return CompletableFuture.completedFuture(str);
	}
}