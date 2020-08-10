package ch.ivy.addon.portal.chat;

import java.io.Serializable;

public class ClusterChatEventParameter implements Serializable {
  private String clientId;
  private String messageText;
  private String receiver; // receiver if sending private message or caseId if sending group message
  private String actor; // sender if sending message or reader if reading message
  private String participant; // participant if reading private message or caseId if read group message
  private String username;
  private boolean isOnline;
  private long nodeId;
  private GroupChat groupChat;

  private ClusterChatEventParameter() {}

  public static ClusterChatEventParameter forSendMessage(String messageText, String receiver, String clientId,
      String actor, long nodeId) {
    ClusterChatEventParameter instance = new ClusterChatEventParameter();
    instance.setClientId(clientId);
    instance.setMessageText(messageText);
    instance.setReceiver(receiver);
    instance.setActor(actor);
    instance.setNodeId(nodeId);
    return instance;
  }

  public static ClusterChatEventParameter forReadMessage(String participant, String clientId, String actor) {
    ClusterChatEventParameter instance = new ClusterChatEventParameter();
    instance.setParticipant(participant);
    instance.setClientId(clientId);
    instance.setActor(actor);
    return instance;
  }

  public static ClusterChatEventParameter forUpdateUserStatus(String username, boolean isOnline) {
    ClusterChatEventParameter instance = new ClusterChatEventParameter();
    instance.setUsername(username);
    instance.setOnline(isOnline);
    return instance;
  }

  public static ClusterChatEventParameter forUpdateGroupList(GroupChat groupChat) {
    ClusterChatEventParameter instance = new ClusterChatEventParameter();
    instance.setGroupChat(groupChat);
    return instance;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getMessageText() {
    return messageText;
  }

  public void setMessageText(String messageText) {
    this.messageText = messageText;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public String getActor() {
    return actor;
  }

  public void setActor(String actor) {
    this.actor = actor;
  }

  public String getParticipant() {
    return participant;
  }

  public void setParticipant(String participant) {
    this.participant = participant;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public boolean isOnline() {
    return isOnline;
  }

  public void setOnline(boolean isOnline) {
    this.isOnline = isOnline;
  }

  public long getNodeId() {
    return nodeId;
  }

  public void setNodeId(long nodeId) {
    this.nodeId = nodeId;
  }

  public GroupChat getGroupChat() {
    return groupChat;
  }

  public void setGroupChat(GroupChat groupChat) {
    this.groupChat = groupChat;
  }

}
