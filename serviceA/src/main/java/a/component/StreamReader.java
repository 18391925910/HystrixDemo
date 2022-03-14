package a.component;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.metric.HystrixRequestEventsStream;
import com.netflix.hystrix.metric.consumer.CumulativeCommandEventCounterStream;
import com.netflix.hystrix.metric.consumer.RollingCommandMaxConcurrencyStream;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesCommandDefault;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yueyi
 */
@Slf4j
public class StreamReader {

  static HystrixCommandKey commandKey = HystrixCommandKey.Factory.asKey("b");
  static HystrixPropertiesCommandDefault properties = new HystrixPropertiesCommandDefault(commandKey, HystrixCommandProperties.Setter());
  static HystrixRequestEventsStream eventsStream = HystrixRequestEventsStream.getInstance();
  public static String read(){
    final String[] s = new String[1];
    eventsStream.observe().subscribe(a->{
      s[0] = a.toString();
    });
    return s[0];
  }


}
