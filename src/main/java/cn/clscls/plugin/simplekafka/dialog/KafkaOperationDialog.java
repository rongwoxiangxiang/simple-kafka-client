package cn.clscls.plugin.simplekafka.dialog;

import cn.clscls.plugin.simplekafka.ui.KafkaOperation;
import cn.clscls.plugin.simplekafka.util.JsonUtil;
import cn.clscls.plugin.simplekafka.util.KafkaClientUtil;
import cn.clscls.plugin.simplekafka.util.NotifyHandler;
import cn.clscls.plugin.simplekafka.util.OutputInputFormatUtil;
import com.google.common.collect.Maps;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaOperationDialog extends DialogWrapper {

  public static final String TITLE = "Kafka message processor";

  public KafkaOperationDialog(Project project) {
    super(project);
    setTitle(TITLE);
    init();
  }

  @Override
  protected JComponent createSouthPanel() {
    return null;
  }

  @Override
  protected JComponent createCenterPanel() {
    KafkaOperation kafkaOperation = new KafkaOperation();
    kafkaOperation.getSend().addActionListener(sendMessageListener(kafkaOperation));
    kafkaOperation.getConsumer().addActionListener(consumerMessageListener(kafkaOperation));
    return kafkaOperation.getMainPanel();
  }

  private ActionListener sendMessageListener(KafkaOperation kafkaOperation) {
    return e -> {
      try {
        KafkaProducer<Object, Object> producer = KafkaClientUtil.getProducer(kafkaOperation.getInputHost());

        String topic = kafkaOperation.getInputTopic();
        String message = kafkaOperation.getInputMessage();

        ProducerRecord<Object, Object> record;
        if (message.startsWith("{") || message.startsWith("[")) {
          Object messageObj = JsonUtil.parseJson(message, Object.class);
          record = new ProducerRecord<>(topic, messageObj);
        } else {
          record = new ProducerRecord<>(topic, message);
        }

        Future future =
            producer.send(
                record,
                (metadata, exception) -> {
                  if (exception != null) {
                    kafkaOperation.setOutput(exception.getLocalizedMessage());
                  } else {
                    HashMap<String, String> map = Maps.newHashMap();
                    map.put("topic", topic);
                    map.put("Offset", String.valueOf(metadata.offset()));
                    map.put("partition", String.valueOf(metadata.partition()));

                    kafkaOperation.setOutput(OutputInputFormatUtil.toOutputString(map));
                  }
                });
        future.get(1, TimeUnit.SECONDS);
      } catch (Exception ex) {
        NotifyHandler.notifyInfo(ex.getMessage());
      }
    };
  }

  private ActionListener consumerMessageListener(KafkaOperation kafkaOperation) {
    return e -> {
      try {
        String groupId = kafkaOperation.getInputGroupId();
        String topic = kafkaOperation.getInputTopic();
        Consumer<Object, Object> consumer =
            KafkaClientUtil.getConsumer(kafkaOperation.getInputHost(), groupId, topic);
        ConsumerRecords<Object, Object> records = consumer.poll(Duration.of(5, ChronoUnit.SECONDS));

        String msg = "";
        for (ConsumerRecord<Object, Object> record : records) {
          var value = JsonUtil.toJSONString(record.value());
          if (value == null) {
            value = record.value().toString();
          }
          msg = msg + "offset: " + record.offset() + "\nmessage:" + value + "\n";
        }
        consumer.commitAsync();

        if (StringUtils.isEmpty(msg)) {
          msg = "no kafka message";
        }
        kafkaOperation.setOutput(msg);

      } catch (Exception ex) {
        NotifyHandler.notifyInfo(ex.getMessage());
      }
    };
  }

}
