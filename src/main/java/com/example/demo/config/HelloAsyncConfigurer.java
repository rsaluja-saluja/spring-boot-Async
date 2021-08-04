package com.example.demo.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.stereotype.Component;

/* The @EnableAsync annotation creates a handly SimpleAsyncTaskExecutor by default. 
 * Unfortunately,This implementation has no practical upper limit for its thread pool size. 
 * This means that The Spring boot application may crash if there were too many @Async methods 
 * running at the same time. To avoid this , we need to provide our own Executor. 
*/

//Class level configuration - no need to mention executor bean name with @Async
// Bean can be defined as in class HelloAsyncConfig

@Component
public class HelloAsyncConfigurer implements AsyncConfigurer {

	// Multiple executor methods can be defined with different therad limits and can be used with different @async functions
    @Override
    public Executor getAsyncExecutor() {
        return Executors.newFixedThreadPool(3);
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    	System.out.println("Rajni&&&&&&&");
        return (ex, method, params) -> {
            System.out.println("Exception with message :" + ex.getMessage());
            System.out.println("Method :" + method.toString());
            System.out.println("Number of parameters :" + params.length);
        };
    }
}