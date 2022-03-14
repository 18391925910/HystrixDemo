package a.MetricsSender;

import a.ClickhouseSenderConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ：yueyi
 * @description ：
 * @date ：Created in 2022/3/11 14:04
 */
@Slf4j
public class ClickhouseSender implements MetricsSender {

  private static final ClickhouseSender instance = new ClickhouseSender();

  public static ClickhouseSender getInstance() {
    return instance;
  }

  public static void init(ClickhouseSenderConfig clickhouseSenderConfig) {

  }

  @Override
  public void send(String data) {
    log.error(data);
  }
}
