package cn.clscls.plugin.simplekafka.util;

import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class KafkaClientUtil {

  private static final Map<String, KafkaProducer<Object, Object>> kafkaProducers = Maps.newConcurrentMap();
  private static final Map<String, Consumer<Object, Object>> kafkaConsumers = Maps.newConcurrentMap();

  public static KafkaProducer<Object, Object> getProducer(String host) {
    Properties props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, host);
    props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, "5000");
    props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, "5000");
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

    return kafkaProducers.computeIfAbsent(host, key -> new KafkaProducer<>(props));
  }

  public static Consumer<Object, Object> getConsumer(String host, String groupId, String topic) {
    Properties props = new Properties();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, host);
    props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, "10000");
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "10000");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

    return kafkaConsumers.computeIfAbsent(
        host + topic,
        key -> {
          Consumer<Object, Object> cm = new KafkaConsumer<>(props);
          cm.subscribe(Collections.singletonList(topic));
          return cm;
        });
  }
}
