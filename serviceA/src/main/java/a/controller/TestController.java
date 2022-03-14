package a.controller;

import a.component.HystrixStreamSubscriber;
import a.component.StreamReader;
import a.feignClient.FeignTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：yueyi
 * @description ：
 * @date ：Created in 2022/3/10 14:01
 */
@RestController
@EnableCircuitBreaker
public class TestController {
  @Autowired
  FeignTest feignTest;


  @GetMapping("get")
  String get(){
    return feignTest.test();
  }

  @GetMapping("subscribe")
  String subscribe(){

    return "HystrixStreamSubscriber:subscribe success";
  }
  @GetMapping("read")
  String read(){
    return StreamReader.read();
  }
}
