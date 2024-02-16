var invalidIFrameSrcPath = false;

let taskUrl = new URLSearchParams(window.location.search).get("taskUrl");
let updateIframeSrc = (newSrc) => {
  document.getElementById('iFrame').src = newSrc;
}
if (taskUrl){
  updateIframeSrc(taskUrl)
}

loadIframe(false);
var recheckFrameTimer;
function loadIframe(recheckIndicator) {
  var iframe = document.getElementById('iFrame');

  if (!recheckIndicator) {
    $(iframe).on('load', function() {
      iframe.style.visibility = 'hidden';
      processIFrameData(iframe);
      clearTimeout(recheckFrameTimer);
      setTimeout(function() {
        if ($(iframe).attr('src') != 'about:blank') {
          iframe.style.visibility = 'visible';
          }
        }, 500);
      return;
    });
  }
  else {
    const iframeDoc = iframe.contentDocument || iframe.contentWindow.document;
    iframeDoc.onbeforeunload = function() {
      iframe.style.visibility = 'hidden';
    }
    if (iframeDoc.readyState == 'complete') {
      processIFrameData(iframe);
      clearTimeout(recheckFrameTimer);
      iframe.style.visibility = 'visible';
      return;
    }
  }

  resizeIFrame();
  recheckFrameTimer = setTimeout(function(){ loadIframe(true); }, 500);
}

function processIFrameData(iframe) {
    var window = iframe.contentWindow;
    checkUrl(iframe);
    if (invalidIFrameSrcPath) {
      invalidIFrameSrcPath = false;
      return;
    }
    getDataFromIFrame([{
      name : 'currentProcessStep',
      value : window.currentProcessStep
    }, {
      name : 'processSteps',
      value : convertProcessSteps(window.processSteps)
    }, {
      name : 'isShowAllSteps',
      value : window.isShowAllSteps
    }, {
      name : 'isHideCaseInfo',
      value : window.isHideCaseInfo
    }, {
      name : 'isHideTaskName',
      value : window.isHideTaskName
    }, {
      name : 'isHideTaskAction',
      value : window.isHideTaskAction
    }, {
      name : 'isWorkingOnATask',
      value : window.isWorkingOnATask
    }, {
      name : 'processChainDirection',
      value : window.processChainDirection
    }, {
      name : 'processChainShape',
      value : window.processChainShape
    }, {
      name : 'announcementInvisible',
      value : window.announcementInvisible
    }, {
      name : 'viewName',
      value : window.viewName
    }, {
      name : 'caseId',
      value : window.caseId
      }, {
      name : 'taskName',
      value : window.taskName
    }, {
      name : 'taskIcon',
      value : window.taskIcon
    }]);
}

function checkUrl(iFrame) {
  const iframeDoc = iFrame.contentDocument;
  if (iframeDoc === undefined || iframeDoc === null) {
    console.log("The iframe content docment is undefined");
    invalidIFrameSrcPath = true;
    return;
  }
  document.title = iframeDoc.title;
  var path = getPortalIframePath(iFrame);
  if (path === '' || invalidIFrameSrcPath) {
    return;
  }
  invalidIFrameSrcPath = false;

  if (path.match("/default/redirect.xhtml$")) {
    var redirectUrl = new URLSearchParams(iFrame.contentWindow.location.search).get("redirectPage");
    iFrame.src = "about:blank";
    redirectToUrlCommand([{
      name: 'url',
      value: redirectUrl
    }]);
  } else {
    useTaskInIFrame([{
      name: 'url',
      value: path
    }]);
    updateHistory(iFrame.contentWindow.location.href);
  }
}

window.addEventListener("resize", resizeIFrame, false);
function resizeIFrame() {
  Portal.updateLayoutContent();
  Portal.updateBreadcrumb();
  var taskHeaderContainerHeight = ($('.js-task-header-container').outerHeight(true)||0);
  var announcementMessageContainerHeight = ($('.js-annoucement-in-frame-template').outerHeight(true)||0);
  var mainScreenHeight = PortalLayout.getAvailableHeight() - PortalLayout.getYPaddingLayoutContent();
  var availableHeight = mainScreenHeight - taskHeaderContainerHeight - announcementMessageContainerHeight;
  if (!!availableHeight) {
    $('iframe[id="iFrame"]').height(availableHeight);
  }
}

function updateContentContainerClass() {
  if ($('.js-task-name-vertical-process-chain').length > 0) {
    $('.js-task-frame-container').addClass('vertical-process-chain');
    if ($('.vertical-chain-shape-line').length > 0) {
      $('.js-task-frame-container').addClass('vertical-chain-shape-line');
    }
  }
  if (!window.announcementInvisible) {
    $('#announcement').removeClass('u-invisibility');
  }
  $('.task-template-container').removeClass('u-invisibility');
}

function getPortalIframePath(iFrame) {
  invalidIFrameSrcPath = false;
  const iframeLocation = iFrame.contentWindow.location;
  let path = '';
  try {
    path = iframeLocation.pathname;
  } catch (error) {
    invalidIFrameSrcPath = true;
    console.log("Cannot access to iframe location data: " + error);
  }
  return path;
}

let updateHistory = (newHref) => {
  let newHrefUrl = new URL(newHref);
  let historyUrl = new URL(window.location);
  historyUrl.searchParams.set('taskUrl', newHrefUrl.pathname + newHrefUrl.search);
  history.replaceState({}, "", historyUrl);
}

const convertProcessSteps = processSteps => {
  // If the process steps is a valid array, convert to JSON
  if (Array.isArray(processSteps)) {
    return JSON.stringify(processSteps);
  }

  // Regex for "a,b,c"
  const onlyCommaRegex = /^[a-zA-Z0-9\s]+(,[a-zA-Z0-9\s]+)*$/;

  // If process steps is a String in "a,b,c" format, split to array
  // Then convert to JSON
  if (onlyCommaRegex.test(processSteps)) {
    const steps = processSteps.split(',');
    const trimmedSteps = steps.map(item => item.trim());
    return JSON.stringify(trimmedSteps);
  }

  // Otherwise return JSON of the process step
  return JSON.stringify(processSteps);
}