package cn.clscls.plugin.simplekafka;

import cn.clscls.plugin.simplekafka.dialog.KafkaOperationDialog;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class SimpleKafkaWindowFactory implements ToolWindowFactory {

  @Override
  public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
    ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
    Content kafkaContent =
        contentFactory.createContent(
            new KafkaOperationDialog(project).getContentPanel(), "Kafka", false);

    toolWindow.getContentManager().addContent(kafkaContent);
  }
}
