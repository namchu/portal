[Ivy]
[>Created: Fri Mar 25 14:12:50 ICT 2016]
153A84D4509574FB 3.18 #module
>Proto >Proto Collection #zClass
Cs0 CaseItemHistoryProcess Big #zClass
Cs0 RD #cInfo
Cs0 #process
Cs0 @TextInP .ui2RdDataAction .ui2RdDataAction #zField
Cs0 @TextInP .rdData2UIAction .rdData2UIAction #zField
Cs0 @TextInP .resExport .resExport #zField
Cs0 @TextInP .type .type #zField
Cs0 @TextInP .processKind .processKind #zField
Cs0 @AnnotationInP-0n ai ai #zField
Cs0 @MessageFlowInP-0n messageIn messageIn #zField
Cs0 @MessageFlowOutP-0n messageOut messageOut #zField
Cs0 @TextInP .xml .xml #zField
Cs0 @TextInP .responsibility .responsibility #zField
Cs0 @RichDialogInitStart f0 '' #zField
Cs0 @RichDialogProcessEnd f4 '' #zField
Cs0 @GridStep f78 '' #zField
Cs0 @RichDialogMethodStart f63 '' #zField
Cs0 @GridStep f9 '' #zField
Cs0 @PushWFArc f10 '' #zField
Cs0 @PushWFArc f7 '' #zField
Cs0 @PushWFArc f1 '' #zField
Cs0 @GridStep f13 '' #zField
Cs0 @PushWFArc f2 '' #zField
Cs0 @PushWFArc f3 '' #zField
>Proto Cs0 Cs0 CaseItemHistoryProcess #zField
Cs0 f0 guid 153362B0ADC5C7D8 #txt
Cs0 f0 type ch.ivy.addon.portalkit.singleapp.cases.CaseItemHistory.CaseItemHistoryData #txt
Cs0 f0 method start(Long) #txt
Cs0 f0 disableUIEvents true #txt
Cs0 f0 inParameterDecl 'ch.ivyteam.ivy.richdialog.exec.RdMethodCallEvent methodEvent = event as ch.ivyteam.ivy.richdialog.exec.RdMethodCallEvent;
<java.lang.Long caseId> param = methodEvent.getInputArguments();
' #txt
Cs0 f0 inParameterMapAction 'out.caseId=param.caseId;
' #txt
Cs0 f0 outParameterDecl '<> result;
' #txt
Cs0 f0 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>start(Long)</name>
        <nameStyle>11,5,7
</nameStyle>
    </language>
</elementInfo>
' #txt
Cs0 f0 69 85 22 22 14 0 #rect
Cs0 f0 @|RichDialogInitStartIcon #fIcon
Cs0 f4 type ch.ivy.addon.portalkit.singleapp.cases.CaseItemHistory.CaseItemHistoryData #txt
Cs0 f4 69 309 22 22 14 0 #rect
Cs0 f4 @|RichDialogProcessEndIcon #fIcon
Cs0 f78 actionDecl 'ch.ivy.addon.portalkit.singleapp.cases.CaseItemHistory.CaseItemHistoryData out;
' #txt
Cs0 f78 actionTable 'out=in;
' #txt
Cs0 f78 actionCode 'import ch.ivy.addon.portalkit.util.SecurityServiceUtils;
import java.util.ArrayList;
import ch.ivyteam.ivy.workflow.TaskState;
import ch.ivyteam.ivy.workflow.ITask;
import ch.ivy.addon.portalkit.service.HistoryService;

List<ITask> finishedTasks = new ArrayList();
in.internalCase = ivy.wf.findCase(in.caseId);
for(ITask task : in.internalCase.getTasks()) {
	if(task.getState().equals(TaskState.DONE)) {
		finishedTasks.add(task);
	}
}

HistoryService historyService = new HistoryService();
in.histories = historyService.getHistories(finishedTasks, in.internalCase.getNotes());

in.tasksOfCaseProcessLink = SecurityServiceUtils.findProcessByUserFriendlyRequestPath("Start Processes/PortalTemplatePages/PortalTaskListOfCase.ivp");' #txt
Cs0 f78 security system #txt
Cs0 f78 type ch.ivy.addon.portalkit.singleapp.cases.CaseItemHistory.CaseItemHistoryData #txt
Cs0 f78 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>get case
create histories 
from tasks and notes</name>
        <nameStyle>47,7
</nameStyle>
    </language>
</elementInfo>
' #txt
Cs0 f78 62 180 36 24 20 -35 #rect
Cs0 f78 @|StepIcon #fIcon
Cs0 f63 guid 1533632E93F2AB48 #txt
Cs0 f63 type ch.ivy.addon.portalkit.singleapp.cases.CaseItemHistory.CaseItemHistoryData #txt
Cs0 f63 method createNote() #txt
Cs0 f63 disableUIEvents false #txt
Cs0 f63 inParameterDecl 'ch.ivyteam.ivy.richdialog.exec.RdMethodCallEvent methodEvent = event as ch.ivyteam.ivy.richdialog.exec.RdMethodCallEvent;
<> param = methodEvent.getInputArguments();
' #txt
Cs0 f63 outParameterDecl '<> result;
' #txt
Cs0 f63 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>createNote()</name>
        <nameStyle>12,5,7
</nameStyle>
    </language>
</elementInfo>
' #txt
Cs0 f63 397 93 22 22 14 0 #rect
Cs0 f63 @|RichDialogMethodStartIcon #fIcon
Cs0 f9 actionDecl 'ch.ivy.addon.portalkit.singleapp.cases.CaseItemHistory.CaseItemHistoryData out;
' #txt
Cs0 f9 actionTable 'out=in;
' #txt
Cs0 f9 actionCode 'import ch.ivy.addon.portalkit.service.ServerWorkingOnDetector;

ServerWorkingOnDetector detector = new ServerWorkingOnDetector();
in.currentServer = detector.getServerWorkingOn();' #txt
Cs0 f9 security system #txt
Cs0 f9 type ch.ivy.addon.portalkit.singleapp.cases.CaseItemHistory.CaseItemHistoryData #txt
Cs0 f9 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>find current server</name>
        <nameStyle>19,7
</nameStyle>
    </language>
</elementInfo>
' #txt
Cs0 f9 62 244 36 24 20 -9 #rect
Cs0 f9 @|StepIcon #fIcon
Cs0 f10 expr out #txt
Cs0 f10 80 204 80 244 #arcP
Cs0 f7 expr out #txt
Cs0 f7 80 268 80 309 #arcP
Cs0 f1 expr out #txt
Cs0 f1 80 107 80 180 #arcP
Cs0 f13 actionDecl 'ch.ivy.addon.portalkit.singleapp.cases.CaseItemHistory.CaseItemHistoryData out;
' #txt
Cs0 f13 actionTable 'out=in;
' #txt
Cs0 f13 actionCode 'import ch.ivyteam.ivy.workflow.INote;

INote note = in.internalCase.createNote(ivy.session, in.noteContent.trim());
in.noteContent = "";' #txt
Cs0 f13 type ch.ivy.addon.portalkit.singleapp.cases.CaseItemHistory.CaseItemHistoryData #txt
Cs0 f13 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>create note
reset note field</name>
        <nameStyle>28,7
</nameStyle>
    </language>
</elementInfo>
' #txt
Cs0 f13 390 180 36 24 23 -10 #rect
Cs0 f13 @|StepIcon #fIcon
Cs0 f2 expr out #txt
Cs0 f2 408 115 408 180 #arcP
Cs0 f3 expr out #txt
Cs0 f3 390 192 98 192 #arcP
>Proto Cs0 .type ch.ivy.addon.portalkit.singleapp.cases.CaseItemHistory.CaseItemHistoryData #txt
>Proto Cs0 .processKind HTML_DIALOG #txt
>Proto Cs0 -8 -8 16 16 16 26 #rect
>Proto Cs0 '' #fIcon
Cs0 f78 mainOut f10 tail #connect
Cs0 f10 head f9 mainIn #connect
Cs0 f9 mainOut f7 tail #connect
Cs0 f7 head f4 mainIn #connect
Cs0 f0 mainOut f1 tail #connect
Cs0 f1 head f78 mainIn #connect
Cs0 f2 head f13 mainIn #connect
Cs0 f3 head f78 mainIn #connect
Cs0 f63 mainOut f2 tail #connect
Cs0 f13 mainOut f3 tail #connect
