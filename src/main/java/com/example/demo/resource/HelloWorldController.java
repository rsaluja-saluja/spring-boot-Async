package com.example.demo.resource;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.HelloService;

@RestController
public class HelloWorldController {

    private HelloService helloService;

    public HelloWorldController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello")
    public String hello() {
        long start = System.currentTimeMillis();
        helloService.processSomethingForLong();
        long end = System.currentTimeMillis();
        return "Hello World Took " + (end - start) + " milliseconds ! and the current Thread is : "+Thread.currentThread().getName();
    }
    
    @GetMapping("/helloReturnFuture")
    public String helloReturnUsingFuture() throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        Future<String> stringFuture = helloService.longRunningProcessThatReturnsUsingFuture();
        System.out.println("Hello Return from long running function. Thread is: "+Thread.currentThread().getName());
        
        while (!stringFuture.isDone()) {
            Thread.sleep(1000);
        }
        String message = stringFuture.get();
        System.out.println(message);
        long end = System.currentTimeMillis();
        return "Hello World Took " + (end - start) + " milliseconds ! and the current Thread is : "+Thread.currentThread().getName();
    }
    
    @GetMapping("/helloReturn")
    public String helloReturnUsingCompletableFuture() throws InterruptedException, ExecutionException {
    	System.out.println("HelloReturn COntroller called");
        long start = System.currentTimeMillis();
        CompletableFuture<String> completableStringFuture = helloService.longRunningProcessThatReturnsUsingCompletableFuture();
       // System.out.println("Hello Return from long running function. Thread is: "+Thread.currentThread().getName()+" time is: "+System.currentTimeMillis());
        
        completableStringFuture.join();
      //  System.out.println("Hello Return long running function ends. Thread is: "+Thread.currentThread().getName()+" time is: "+System.currentTimeMillis());
        
        String message = completableStringFuture.get();
        System.out.println(message);
        long end = System.currentTimeMillis();
        return "Hello World Took " + (end - start) + " milliseconds ! and the current Thread is : "+Thread.currentThread().getName();
    }


}