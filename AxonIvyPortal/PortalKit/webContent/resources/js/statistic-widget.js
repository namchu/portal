var topValue;
var leftValue;

function bindCursorChangeEvent() {
  $('.js-drilldown-cursor').bind('jqplotDataHighlight', function(ev, seriesIndex, pointIndex, data, radius) {
    $('.jqplot-event-canvas').css('cursor', 'pointer');
  });
  $('.js-drilldown-cursor').bind('jqplotDataUnhighlight', function(ev) {
    $('.jqplot-event-canvas').css('cursor', 'default');
  });
  $('.js-expiry-chart').bind('click', function(ev) {
    var $expiryChartDrillDown = $('.js-expiry-chart-drill-down');
    var $expiryChartTaskList = $('.js-expiry-chart-task-list');
   /* if (pointIndex === 0) { // Expired bar
      $expiryChartDrillDown.hide();
      $expiryChartTaskList.css('margin-top', '10px');
    } else {*/
      $expiryChartDrillDown.show();
      $expiryChartTaskList.css('margin-top', '0px');
    /*}*/
    var index = this.className.match(/expiry-chart-(\d+)/)[1];
    var widgetVar = 'context-menu-' + index;
    PF('context-menu-' + index).show();
    topValue = ev.pageY;
    leftValue = ev.pageX;
  });
}

function updateDrillDownPanelPosition(panel) {
  console.log(panel);
  var widgetVar = panel.widgetVar;
  $('.' + widgetVar).css({
    'left' : leftValue,
    'top' : topValue,
    'position' : 'absolute'
  });
}

function barChartExtender() {
  var currentAngle;
  if (window.screen.availWidth < 1366) {
    currentAngle = -30;
  } else {
    currentAngle = 0;
  }
  this.cfg.grid = {
    gridLineColor : 'transparent',
    background : 'rgba(255,255,255, 0)',
    drawBorder : true,
    shadow : false
  };
//  this.cfg.axes.yaxis.labelOptions = {
//    textColor : 'black',
//    fontSize : '11.4px',
//  };
//  this.cfg.axes.xaxis.labelOptions = {
//    textColor : 'black',
//    fontSize : '11.4px'
//  };
//  this.cfg.axes.xaxis.tickOptions = {
//    textColor : 'black',
//    fontSize : '11.4px',
//    angle : currentAngle
//  };
//  this.cfg.axes.yaxis.tickOptions = {
//    textColor : 'black',
//    fontSize : '11.4px'
//  };
}

function elapsedTimeBarChartExtender() {
 /* var currentAngle;
  var showLabel = !isNaN(this.cfg.data[0][0]);
  if (window.screen.availWidth < 1366 || this.cfg.data[0].length > 2) {
    currentAngle = -70;
  } else {
    currentAngle = 0;
  }*/

 /* this.cfg.axes.yaxis.labelOptions = {
    textColor : 'black',
    fontSize : '11.4px'
  };
  this.cfg.axes.xaxis.labelOptions = {
    textColor : 'black',
    fontSize : '11.4px'
  };
  this.cfg.axes.xaxis.tickOptions = {
    textColor : 'black',
    fontSize : '11.4px',
    angle : currentAngle,
    showLabel: showLabel
  };
  this.cfg.axes.yaxis.tickOptions = {
    textColor : 'black',
    fontSize : '11.4px'
  };
  this.cfg.highlighter = {
    show : true,
    tooltipAxes : 'y',
    useAxesFormatters : false,
    tooltipFormatString : "%.2f"
  };*/
}

function chartExtender() {
  this.cfg.grid = {
    background : 'rgba(255,255,255, 0)',
    drawGridlines : false,
    drawBorder : false,
    shadow : false
  };
//  this.cfg.seriesDefaults.rendererOptions.dataLabelFormatString = '%.4s%%';
//  this.cfg.seriesDefaults.rendererOptions.dataLabelThreshold = 0;
}

