package com.axonivy.portal.developerexamples.publicapi;

import org.primefaces.PrimeFaces;

import ch.ivyteam.ivy.environment.Ivy;

public class PortalGlobalGrowInIFrameAPI {

  public PortalGlobalGrowInIFrameAPI() {}

  /**
   * Displays Portal Growl customized message.
   * 
   * @param message
   */
  public void displayCustomizedMessage(String message) {
    Ivy.log().warn("displayCustomizedMessage");
    String statement = "parent.displayPortalGrowlMessageCommand([{name: 'taskId', value: " + Ivy.wfTask().getId()
        + "},{name: 'overridePortalGrowl', value: true},{name: 'portalGrowlMessage', value: '" + message + "'}]);";
    PrimeFaces.current().executeScript(statement);
  }
}
