package cn.clscls.plugin.simplekafka.util;

import com.intellij.ide.impl.ProjectUtil;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.util.ui.UIUtil;
import javax.swing.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotifyHandler {

  private static final Logger log = LoggerFactory.getLogger(NotifyHandler.class);

  public static boolean showYesNoDialog(String title, String message) {
    return Messages.YES == Messages.showYesNoDialog(message, title, UIUtil.getWarningIcon());
  }

  public static void showInfoMessage(String title, String message) {
    showInfoMessage(message, title, ProjectUtil.getActiveProject());
  }

  public static void showInfoMessage(String title, String message, Project project) {
    try {
      ApplicationManager.getApplication()
          .invokeLater(() -> Messages.showInfoMessage(project, message, title));
    } catch (Exception e) {
      log.error("showInfoMessage ", e);
    }
  }

  public static void showErrorDialog(String message) {
    try {
      ApplicationManager.getApplication()
          .invokeLater(() -> Messages.showErrorDialog(message, "发生异常啦"));
    } catch (Exception e) {
      log.error("showInfoMessage ", e);
    }
  }

  public static void showWarningDialog(String message) {
    try {
      ApplicationManager.getApplication()
          .invokeLater(() -> Messages.showWarningDialog(message, "发生异常啦"));
    } catch (Exception e) {
      log.error("showInfoMessage ", e);
    }
  }

  public static void notifyError(String content) {
    notifyError(content, ProjectUtil.getActiveProject());
  }

  public static void notifyError(String content, Project project) {
    notify(content, NotificationType.ERROR, project);
  }

  public static void notifyWarn(String content) {
    notifyWarn(content, ProjectUtil.getActiveProject());
  }

  public static void notifyWarn(String content, Project project) {
    notify(content, NotificationType.WARNING, project);
  }

  public static void notifyInfo(String content) {
    notifyInfo(content, ProjectUtil.getActiveProject());
  }

  public static void notifyInfo(String content, Project project) {
    notify(content, NotificationType.INFORMATION, project);
  }

  public static void notify(String content, NotificationType type, Project project) {
    try {
      NotificationGroup notificationGroup =
          NotificationGroupManager.getInstance().getNotificationGroup("NotificationGroupId");
      ApplicationManager.getApplication()
          .invokeLater(() -> notificationGroup.createNotification(content, type).notify(project));
    } catch (Exception e) {
      log.error("NotifyHandler|notify ", e);
    }
  }
}
