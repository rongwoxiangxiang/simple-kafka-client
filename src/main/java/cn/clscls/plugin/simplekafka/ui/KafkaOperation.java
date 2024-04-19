package cn.clscls.plugin.simplekafka.ui;

import javax.swing.*;
import org.apache.commons.lang3.StringUtils;

/**
 * @author
 * @date 2023.12.07
 */
public class KafkaOperation {
  private JPanel mainPanel;
  private JButton consumer;
  private JButton send;
  private JTextField inputHost;
  private JTextField inputTopic;
  private JTextField inputGroupId;

  private JTextArea output;
  private JTextArea inputMessage;

  public JPanel getMainPanel() {
    return mainPanel;
  }

  public JButton getConsumer() {
    return consumer;
  }

  public JButton getSend() {
    return send;
  }

  public String getInputHost() {
    return StringUtils.trim(inputHost.getText());
  }

  public void setInputHost(String inputHostStr) {
    this.inputHost.setText(inputHostStr);
  }

  public String getInputTopic() {
    return StringUtils.trim(inputTopic.getText());
  }

  public void setInputTopic(String inputTopicStr) {
    this.inputTopic.setText(inputTopicStr);
  }

  public String getInputGroupId() {
    return StringUtils.trim(inputGroupId.getText());
  }

  public void setInputGroupId(String inputGroupIdStr) {
    this.inputGroupId.setText(inputGroupIdStr);
  }

  public void setOutput(String outputStr) {
    this.output.setText(outputStr);
  }

  public String getInputMessage() {
    return StringUtils.trim(inputMessage.getText());
  }

  public void setInputMessage(String inputMessageStr) {
    this.inputMessage.setText(inputMessageStr);
  }
}
