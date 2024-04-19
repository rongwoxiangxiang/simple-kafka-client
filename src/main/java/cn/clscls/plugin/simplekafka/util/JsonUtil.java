package cn.clscls.plugin.simplekafka.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.text.SimpleDateFormat;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtil {

  private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

  private static final JsonMapper MAPPER = new JsonMapper();
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  static {
    MAPPER.setDateFormat(DATE_FORMAT);
    MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    // 关闭日期序列化为时间戳的功能
    MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    // 关闭序列化的时候没有为属性找到getter方法,报错
    MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    // 反序列化未知属性不抛出异常
    MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  public static String toJSONString(Object args) {
    if (args == null) {
      return "";
    }
    try {
      return MAPPER.writeValueAsString(args);
    } catch (JsonProcessingException e) {
      log.error("JsonUtil|json format occur err,data is :{},ex is", args, e);
    }
    return "";
  }

  public static <T> T parseJson(String args, Class<T> clazz) {
    if (StringUtils.isEmpty(args)) {
      return null;
    }
    try {
      return MAPPER.readValue(args, clazz);
    } catch (JsonProcessingException e) {
      log.error("JsonUtil|parseJson err ", e);
      return null;
    }
  }
}
