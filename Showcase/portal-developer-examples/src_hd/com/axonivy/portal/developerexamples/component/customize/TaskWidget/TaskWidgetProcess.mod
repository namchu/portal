[Ivy]
1624D16247B4E149 9.2.0 #module
>Proto >Proto Collection #zClass
Ps0 TaskWidgetProcess Big #zClass
Ps0 RD #cInfo
Ps0 #process
Ps0 @TextInP .type .type #zField
Ps0 @TextInP .processKind .processKind #zField
Ps0 @AnnotationInP-0n ai ai #zField
Ps0 @MessageFlowInP-0n messageIn messageIn #zField
Ps0 @MessageFlowOutP-0n messageOut messageOut #zField
Ps0 @TextInP .xml .xml #zField
Ps0 @TextInP .responsibility .responsibility #zField
Ps0 @UdProcessEnd f1 '' #zField
Ps0 @UdInit f2 '' #zField
Ps0 @PushWFArc f3 '' #zField
>Proto Ps0 Ps0 TaskWidgetProcess #zField
Ps0 f1 229 133 22 22 14 0 #rect
Ps0 f1 @|UdProcessEndIcon #fIcon
Ps0 f2 guid 1573377403EC1C55 #txt
Ps0 f2 method useTaskView(TaskView) #txt
Ps0 f2 inParameterDecl '<ch.ivy.addon.portal.generic.view.TaskView taskView> param;' #txt
Ps0 f2 inParameterMapAction 'out.taskView=param.taskView;
' #txt
Ps0 f2 outParameterDecl '<> result;' #txt
Ps0 f2 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>useTaskView(TaskView)</name>
    </language>
</elementInfo>
' #txt
Ps0 f2 77 133 22 22 -36 17 #rect
Ps0 f2 @|UdInitIcon #fIcon
Ps0 f3 expr out #txt
Ps0 f3 99 144 229 144 #arcP
>Proto Ps0 .type com.axonivy.portal.developerexamples.component.customize.TaskWidget.TaskWidgetData #txt
>Proto Ps0 .processKind HTML_DIALOG #txt
>Proto Ps0 -8 -8 16 16 16 26 #rect
>Proto Ps0 '' #fIcon
Ps0 f2 mainOut f3 tail #connect
Ps0 f3 head f1 mainIn #connect
