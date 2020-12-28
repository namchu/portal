var PortalSessionWarning = function() {
  var sessionCounterCookieName = 'ch.ivy.addon.portal.SessionCounter',
  sessionCounterUpdatedOnCookieName = 'ch.ivy.addon.portal.SessionCounterUpdatedOn',
  isLogOutCookieName = 'ch.ivy.addon.portal.IsLogout',
  warningDialogShow = false,

  isInteractedInIframeTaskTemplate = false,

  intervalCheckSessionTimeout, 
  init = function(clientSideTimeOut) {
    timeout = clientSideTimeOut,
    timeOutSeconds = timeout / 1000,
    setCookie(sessionCounterCookieName, timeOutSeconds);
    setCookie(isLogOutCookieName, false);
    intervalCheckSessionTimeout = setInterval(timerDecrement, 1000); // Call Every Second
    window.onload = resetCounterAndTimeout;
    document.onkeypress = resetCounterAndTimeout;
    document.onclick = resetCounterAndTimeout;
    document.onmousedown = resetCounterAndTimeout;
    document.ontouchstart = resetCounterAndTimeout;
    document.onscroll = resetCounterAndTimeout;

    // Using IFrame Task template
    if ($("#iFrame").length > 0) {
      $("#iFrame").get(0).onkeypress = updateInteractionStatusInIFrame;
      $("#iFrame").get(0).onclick = updateInteractionStatusInIFrame;
      $("#iFrame").get(0).onmousedown = updateInteractionStatusInIFrame;
      $("#iFrame").get(0).ontouchstart = updateInteractionStatusInIFrame;
      $("#iFrame").get(0).onscroll = updateInteractionStatusInIFrame;
    }
  },

  timerDecrement = function() {
    var lastUpdated = getCookie(sessionCounterUpdatedOnCookieName), now = new Date(), shouldCheck = false;
    if (lastUpdated == 'null') {
      setCookie(sessionCounterUpdatedOnCookieName, now);
    } else if (now.getTime() - new Date(lastUpdated).getTime() >= 1000) {
      setCookie(sessionCounterUpdatedOnCookieName, now);
      shouldCheck = true;
    }

    if (shouldCheck) {
      if (timeOutSeconds > 0) {

        if (parseInt((getCookie(sessionCounterCookieName)), 10) > 0) {
          timeOutSeconds = parseInt(getCookie(sessionCounterCookieName), 10);
        }
        timeOutSeconds--;
        setCookie(sessionCounterCookieName, timeOutSeconds);

      } else {
        if (getCookie(isLogOutCookieName) == "false") {
          setCookie(isLogOutCookieName, true);
          logoutAndShowDialog();
        } else {
          PF('timeout-warning-dialog').hide();
          PF('timeout-dialog').show();
          clearInterval(intervalCheckSessionTimeout);
        }
      }
    } else {
      timeOutSeconds = parseInt(getCookie(sessionCounterCookieName), 10);
    }

    if (timeOutSeconds < 60) {
      if ($("#iFrame").length > 0 && isInteractedInIframeTaskTemplate == true) {
        warningDialogShow = false;
        isInteractedInIframeTaskTemplate = false;
        keepSessionInIFrame();
      } else if (warningDialogShow == false) {
        warningDialogShow = true;
        PF('timeout-warning-dialog').show();
      }
    } else {
      hideWarningDialog();
    }
  },

  resetCounterAndTimeout = function() {
    if (warningDialogShow == false) {
      setCookie(sessionCounterUpdatedOnCookieName, null);
      timeOutSeconds = timeout / 1000;
      setCookie(sessionCounterCookieName, timeOutSeconds);
    }
  },

  updateInteractionStatusInIFrame = function() {
    isInteractedInIframeTaskTemplate = true;
  },

  hideWarningDialog = function() {
    warningDialogShow = false;
    PF('timeout-warning-dialog').hide();
  }
  
  return {
    init: init,
    resetCounterAndTimeout: resetCounterAndTimeout,
    hideWarningDialog: hideWarningDialog
  };
}();