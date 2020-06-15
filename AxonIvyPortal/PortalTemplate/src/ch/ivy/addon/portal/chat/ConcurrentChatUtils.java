package ch.ivy.addon.portal.chat;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

import ch.ivy.addon.portalkit.util.IvyExecutor;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.security.ISession;

public final class ConcurrentChatUtils {
  public static final String PORTAL_CHAT_RESPONSE_HISTORY = "PortalChatResponseHistory_%s";
  private static final int RECENT_HISTORY_SIZE = 20;

  private ConcurrentChatUtils() {}

  @SuppressWarnings("unchecked")
  public static Deque<ChatResponse> getRecentChatResponseHistory(String username) {
    return IvyExecutor.executeAsSystem(() -> {
      Deque<ChatResponse> history = (Deque<ChatResponse>) Ivy.wf().getApplication()
          .getAttribute(String.format(PORTAL_CHAT_RESPONSE_HISTORY, username));
      if (history == null) {
        history = new ConcurrentLinkedDeque<>();
        Ivy.wf().getApplication().setAttribute(String.format(PORTAL_CHAT_RESPONSE_HISTORY, username), history);
      }
      if (history.size() > RECENT_HISTORY_SIZE) {
        int numberOfEntriesToRemove = history.size() - RECENT_HISTORY_SIZE;
        for (int i = 0; i < numberOfEntriesToRemove; i++) {
          history.pollFirst();
        }
      }
      return history;
    });
  }

  public static void removePortalChatResponseHistory(String username) {
    IvyExecutor.executeAsSystem(() -> {
      Ivy.wf().getApplication().removeAttribute(String.format(PORTAL_CHAT_RESPONSE_HISTORY, username));
      return null;
    });
  }

  public static boolean isUserOnline(String username) {
    return Ivy.wf().getSecurityContext().getSessions().stream()
        .anyMatch(session -> session.getSessionUserName().equals(username));
  }

  /**
   * Gets map [username, ISession] of all active sessions
   * 
   * @return map [username, ISession] of all active sessions
   */
  public static Map<String, ISession> getUserNameToSession() {
    Map<String, ISession> map = new HashMap<>();
    for (ISession session : Ivy.wf().getSecurityContext().getSessions()) {
      map.put(session.getSessionUserName(), session);
    }
    return map;
  }


}
