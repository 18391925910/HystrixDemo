package a.service;

import a.ClickhouseSenderConfig;
import a.MetricsSender.ClickhouseSender;
import a.MetricsSender.MetricsSender;
import a.component.HystrixStreamSubscriber;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rx.Subscriber;
import com.netflix.hystrix.contrib.requests.stream.HystrixRequestEventsSseServlet;
/**
 * @author ：yueyi
 * @description ：
 * @date ：Created in 2022/3/11 14:11
 */
public class HystrixMetricsService {


  MetricsSender sender;

  //@Value("${}")
  int pausePollerThreadDelayInMs;

  @PostConstruct
  void init(){

    ClickhouseSender.init(new ClickhouseSenderConfig());
    sender= ClickhouseSender.getInstance();

    HystrixStreamSubscriber.init(new Subscriber<String>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable throwable) {

      }

      @Override
      public void onNext(String s) {
          sender.send(s);
      }
    },1000);

    //HystrixStreamSubscriber.subscribe();
  }

}
