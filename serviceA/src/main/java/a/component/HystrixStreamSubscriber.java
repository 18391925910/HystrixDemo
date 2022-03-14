package a.component;

import a.MetricsSender.MetricsSender;
import com.netflix.hystrix.HystrixCollapserMetrics;
import com.netflix.hystrix.HystrixCommandMetrics;
import com.netflix.hystrix.HystrixThreadPoolMetrics;
import com.netflix.hystrix.contrib.sample.stream.HystrixSampleSseServlet;
import com.netflix.hystrix.metric.HystrixRequestEvents;
import com.netflix.hystrix.metric.consumer.HystrixDashboardStream;
import com.netflix.hystrix.metric.consumer.HystrixDashboardStream.DashboardData;
import com.netflix.hystrix.serial.SerialHystrixDashboardData;
import com.netflix.hystrix.serial.SerialHystrixRequestEvents;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * @author yueyi
 * Subscribe the hystrix stream and Completes the specified processing of the data
 */
@Slf4j
public class HystrixStreamSubscriber {
  static Subscriber<String> subscriber;

  //wake up occasionally and check that poller is still alive.  this value controls how often
  protected static final int DEFAULT_PAUSE_POLLER_THREAD_DELAY_IN_MS = 500;

  private static int pausePollerThreadDelayInMs=500;

  final static AtomicBoolean moreDataWillBeSent = new AtomicBoolean(true);

  public static void init(Subscriber<String> streamSubscriber,int delayInMs){
    subscriber=streamSubscriber;
    pausePollerThreadDelayInMs=delayInMs;
  }


  public static void subscribe() {
    if (Objects.isNull(subscriber)) {
      log.error("subscribe error: subscriber is null!");
      return;
    }
    Subscription subscription=null;
    try {
      Subject<HystrixRequestEvents, HystrixRequestEvents> writeOnlyRequestEventsSubject;
      writeOnlyRequestEventsSubject = PublishSubject.create();
      Observable<HystrixRequestEvents> readOnlyRequestEvents= writeOnlyRequestEventsSubject.onBackpressureBuffer(1024);

      Observable<String> singleSource=readOnlyRequestEvents
          .map(SerialHystrixRequestEvents::toJsonString);

       subscription=singleSource.observeOn(Schedulers.io())
          .subscribe(subscriber);

      while (moreDataWillBeSent.get()) {

        try {

          Thread.sleep(pausePollerThreadDelayInMs);

          log.info("ping:");

        } catch (InterruptedException e) {

          log.error("InterruptedException:{}",e.getMessage());

          moreDataWillBeSent.set(false);
        }
      }
    } finally {
      if (!Objects.isNull(subscription)){
        subscription.unsubscribe();
      }
    }
  }
}

