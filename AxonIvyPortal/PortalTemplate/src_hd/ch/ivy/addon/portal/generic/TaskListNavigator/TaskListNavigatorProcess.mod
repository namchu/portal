[Ivy]
15F6ACE164737414 3.20 #module
>Proto >Proto Collection #zClass
Ss0 TaskListNavigatorProcess Big #zClass
Ss0 RD #cInfo
Ss0 #process
Ss0 @TextInP .ui2RdDataAction .ui2RdDataAction #zField
Ss0 @TextInP .rdData2UIAction .rdData2UIAction #zField
Ss0 @TextInP .resExport .resExport #zField
Ss0 @TextInP .type .type #zField
Ss0 @TextInP .processKind .processKind #zField
Ss0 @AnnotationInP-0n ai ai #zField
Ss0 @MessageFlowInP-0n messageIn messageIn #zField
Ss0 @MessageFlowOutP-0n messageOut messageOut #zField
Ss0 @TextInP .xml .xml #zField
Ss0 @TextInP .responsibility .responsibility #zField
Ss0 @RichDialogInitStart f0 '' #zField
Ss0 @RichDialogProcessEnd f1 '' #zField
Ss0 @PushWFArc f2 '' #zField
Ss0 @RichDialogProcessStart f3 '' #zField
Ss0 @RichDialogEnd f4 '' #zField
Ss0 @PushWFArc f5 '' #zField
Ss0 @CallSub f40 '' #zField
Ss0 @RichDialogProcessStart f8 '' #zField
Ss0 @CallSub f7 '' #zField
Ss0 @GridStep f10 '' #zField
Ss0 @PushWFArc f11 '' #zField
Ss0 @GridStep f44 '' #zField
Ss0 @PushWFArc f12 '' #zField
Ss0 @PushWFArc f6 '' #zField
Ss0 @GridStep f13 '' #zField
Ss0 @PushWFArc f14 '' #zField
Ss0 @PushWFArc f9 '' #zField
>Proto Ss0 Ss0 TaskListNavigatorProcess #zField
Ss0 f0 guid 15F6ACE17192667A #txt
Ss0 f0 type ch.ivy.addon.portal.generic.TaskListNavigator.TaskListNavigatorData #txt
Ss0 f0 method start() #txt
Ss0 f0 disableUIEvents true #txt
Ss0 f0 inParameterDecl 'ch.ivyteam.ivy.richdialog.exec.RdMethodCallEvent methodEvent = event as ch.ivyteam.ivy.richdialog.exec.RdMethodCallEvent;
<> param = methodEvent.getInputArguments();
' #txt
Ss0 f0 outParameterDecl '<> result;
' #txt
Ss0 f0 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>start()</name>
    </language>
</elementInfo>
' #txt
Ss0 f0 83 51 26 26 -16 15 #rect
Ss0 f0 @|RichDialogInitStartIcon #fIcon
Ss0 f1 type ch.ivy.addon.portal.generic.TaskListNavigator.TaskListNavigatorData #txt
Ss0 f1 211 51 26 26 0 12 #rect
Ss0 f1 @|RichDialogProcessEndIcon #fIcon
Ss0 f2 expr out #txt
Ss0 f2 109 64 211 64 #arcP
Ss0 f3 guid 15F6ACE172E8B516 #txt
Ss0 f3 type ch.ivy.addon.portal.generic.TaskListNavigator.TaskListNavigatorData #txt
Ss0 f3 actionDecl 'ch.ivy.addon.portal.generic.TaskListNavigator.TaskListNavigatorData out;
' #txt
Ss0 f3 actionTable 'out=in;
' #txt
Ss0 f3 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>close</name>
    </language>
</elementInfo>
' #txt
Ss0 f3 83 291 26 26 -15 12 #rect
Ss0 f3 @|RichDialogProcessStartIcon #fIcon
Ss0 f4 type ch.ivy.addon.portal.generic.TaskListNavigator.TaskListNavigatorData #txt
Ss0 f4 guid 15F6ACE172F16528 #txt
Ss0 f4 211 291 26 26 0 12 #rect
Ss0 f4 @|RichDialogEndIcon #fIcon
Ss0 f5 expr out #txt
Ss0 f5 109 304 211 304 #arcP
Ss0 f40 type ch.ivy.addon.portal.generic.TaskListNavigator.TaskListNavigatorData #txt
Ss0 f40 processCall 'Functional Processes/OpenPortalTasks:useView(ch.ivy.addon.portal.generic.view.TaskView)' #txt
Ss0 f40 doCall true #txt
Ss0 f40 requestActionDecl '<ch.ivy.addon.portal.generic.view.TaskView taskView> param;
' #txt
Ss0 f40 requestMappingAction 'param.taskView=in.taskView;
' #txt
Ss0 f40 responseActionDecl 'ch.ivy.addon.portal.generic.TaskListNavigator.TaskListNavigatorData out;
' #txt
Ss0 f40 responseMappingAction 'out=in;
' #txt
Ss0 f40 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>OpenPortalTasks</name>
        <nameStyle>15,5,7
</nameStyle>
    </language>
</elementInfo>
' #txt
Ss0 f40 968 152 112 48 -48 -12 #rect
Ss0 f40 @|CallSubIcon #fIcon
Ss0 f8 guid 15F6AD8AACDD69BB #txt
Ss0 f8 type ch.ivy.addon.portal.generic.TaskListNavigator.TaskListNavigatorData #txt
Ss0 f8 actionDecl 'ch.ivy.addon.portal.generic.TaskListNavigator.TaskListNavigatorData out;
' #txt
Ss0 f8 actionTable 'out=in;
' #txt
Ss0 f8 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>openTask</name>
        <nameStyle>8,5,7
</nameStyle>
    </language>
</elementInfo>
' #txt
Ss0 f8 83 163 26 26 -27 15 #rect
Ss0 f8 @|RichDialogProcessStartIcon #fIcon
Ss0 f7 type ch.ivy.addon.portal.generic.TaskListNavigator.TaskListNavigatorData #txt
Ss0 f7 processCall 'Functional Processes/InitializeTaskDataModel:call()' #txt
Ss0 f7 doCall true #txt
Ss0 f7 requestActionDecl '<> param;
' #txt
Ss0 f7 responseActionDecl 'ch.ivy.addon.portal.generic.TaskListNavigator.TaskListNavigatorData out;
' #txt
Ss0 f7 responseMappingAction 'out=in;
out.taskDataModel=result.dataModel;
' #txt
Ss0 f7 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>InitializeTaskDataModel</name>
    </language>
</elementInfo>
' #txt
Ss0 f7 344 154 144 44 -65 -8 #rect
Ss0 f7 @|CallSubIcon #fIcon
Ss0 f10 actionDecl 'ch.ivy.addon.portal.generic.TaskListNavigator.TaskListNavigatorData out;
' #txt
Ss0 f10 actionTable 'out=in;
' #txt
Ss0 f10 actionCode 'import ch.ivy.addon.portalkit.enums.TaskAssigneeType;
import ch.ivy.addon.portal.generic.view.TaskView;
import ch.ivy.addon.portalkit.util.PermissionUtils;

in.taskDataModel.setIgnoreInvolvedUser(PermissionUtils.checkReadAllTasksPermission());
in.taskDataModel.setTaskAssigneeType(TaskAssigneeType.ALL);

in.taskView = TaskView.create().dataModel(in.taskDataModel).noTaskFoundMessage("").showHeaderToolbar(false).createNewTaskView();' #txt
Ss0 f10 type ch.ivy.addon.portal.generic.TaskListNavigator.TaskListNavigatorData #txt
Ss0 f10 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>Build data model, task view</name>
        <nameStyle>27,7
</nameStyle>
    </language>
</elementInfo>
' #txt
Ss0 f10 552 154 160 44 -74 -8 #rect
Ss0 f10 @|StepIcon #fIcon
Ss0 f11 expr out #txt
Ss0 f11 488 176 552 176 #arcP
Ss0 f44 actionDecl 'ch.ivy.addon.portal.generic.TaskListNavigator.TaskListNavigatorData out;
' #txt
Ss0 f44 actionTable 'out=in;
' #txt
Ss0 f44 actionCode 'import ch.ivy.addon.portalkit.enums.SessionAttribute;
import ch.ivy.addon.portalkit.util.SecurityServiceUtils;

SecurityServiceUtils.removeSessionAttribute(SessionAttribute.LAST_PAGE.toString());' #txt
Ss0 f44 type ch.ivy.addon.portal.generic.TaskListNavigator.TaskListNavigatorData #txt
Ss0 f44 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>Remove the session
attribute of last page</name>
        <nameStyle>41
</nameStyle>
    </language>
</elementInfo>
' #txt
Ss0 f44 206 164 36 24 -47 22 #rect
Ss0 f44 @|StepIcon #fIcon
Ss0 f12 expr out #txt
Ss0 f12 109 176 206 176 #arcP
Ss0 f6 expr out #txt
Ss0 f6 242 176 344 176 #arcP
Ss0 f13 actionDecl 'ch.ivy.addon.portal.generic.TaskListNavigator.TaskListNavigatorData out;
' #txt
Ss0 f13 actionTable 'out=in;
' #txt
Ss0 f13 actionCode 'import ch.ivy.addon.portal.generic.navigation.PortalPage;
import ch.ivy.addon.portalkit.enums.SessionAttribute;
import ch.ivy.addon.portalkit.util.SecurityServiceUtils;

SecurityServiceUtils.setSessionAttribute(SessionAttribute.LAST_PAGE.toString(), PortalPage.LINK_TO_TASK);

SecurityServiceUtils.removeSessionAttribute(SessionAttribute.TASK_DATA_MODEL.toString());' #txt
Ss0 f13 type ch.ivy.addon.portal.generic.TaskListNavigator.TaskListNavigatorData #txt
Ss0 f13 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>Store the last page 
as Portal Task to session</name>
        <nameStyle>46,7
</nameStyle>
    </language>
</elementInfo>
' #txt
Ss0 f13 752 154 160 44 -62 -16 #rect
Ss0 f13 @|StepIcon #fIcon
Ss0 f14 expr out #txt
Ss0 f14 712 176 752 176 #arcP
Ss0 f9 expr out #txt
Ss0 f9 912 176 968 176 #arcP
>Proto Ss0 .type ch.ivy.addon.portal.generic.TaskListNavigator.TaskListNavigatorData #txt
>Proto Ss0 .processKind HTML_DIALOG #txt
>Proto Ss0 -8 -8 16 16 16 26 #rect
>Proto Ss0 '' #fIcon
Ss0 f0 mainOut f2 tail #connect
Ss0 f2 head f1 mainIn #connect
Ss0 f3 mainOut f5 tail #connect
Ss0 f5 head f4 mainIn #connect
Ss0 f7 mainOut f11 tail #connect
Ss0 f11 head f10 mainIn #connect
Ss0 f8 mainOut f12 tail #connect
Ss0 f12 head f44 mainIn #connect
Ss0 f44 mainOut f6 tail #connect
Ss0 f6 head f7 mainIn #connect
Ss0 f10 mainOut f14 tail #connect
Ss0 f14 head f13 mainIn #connect
Ss0 f13 mainOut f9 tail #connect
Ss0 f9 head f40 mainIn #connect
