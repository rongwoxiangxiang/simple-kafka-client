package cn.clscls.plugin.simplekafka.util;

import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections.MapUtils;

public class OutputInputFormatUtil {

  public static String toOutputString(Map<String, String> map) {
    if (MapUtils.isEmpty(map)) {
      return "";
    }
    return map.entrySet().stream()
        .map(e -> e.getKey() + ": " + e.getValue())
        .collect(Collectors.joining("\n"));
  }
}
