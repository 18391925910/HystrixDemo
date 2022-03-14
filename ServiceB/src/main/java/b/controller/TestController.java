package b.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：yueyi
 * @description ：
 * @date ：Created in 2022/3/10 14:01
 */
@RestController
public class TestController {

  @GetMapping("test")
  String test(){
    return "hello";
  }
}
