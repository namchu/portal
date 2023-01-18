package ch.ivy.addon.portalkit.bean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ch.ivy.addon.portalkit.service.DateTimeGlobalSettingService;
import ch.ivyteam.ivy.environment.Ivy;

@ManagedBean
@SessionScoped
public class DateTimePatternBean implements Serializable {
  private static final long serialVersionUID = 1L;

  private DateTimeGlobalSettingService dateTimePatternService;
  
  @PostConstruct
  public void init() {
    dateTimePatternService = DateTimeGlobalSettingService.getInstance();
  }
  
  public String getConfiguredPattern() {
    return dateTimePatternService.getGlobalSettingPattern();
  }
  
  public String getDatePattern() {
    return dateTimePatternService.getDatePattern();
  }
  
  public String getDateTimePattern() {
    return dateTimePatternService.getDateTimePattern();
  }
  
  public String getDateTimestampPattern() {
    return dateTimePatternService.getDateTimestampPattern();
  }

  public String getConfiguredDateFilterPattern() {
    return dateTimePatternService.getGlobalSettingDateFilterPattern();
  }

  public String getConfiguredCalendarPattern() {
    return dateTimePatternService.getGlobalSettingCalendarPattern();
  }

  public String getConfiguredDateWithoutTimePattern() {
    return dateTimePatternService.getDateWithoutTimePattern();
  }
  
  public boolean getIsDateFilterWithTime() {
    return dateTimePatternService.isDateFilterWithTime();
  }
  
  public boolean getIsTimeHidden() {
    return dateTimePatternService.isTimeHidden();
  }
  
  public String getFormattingLanguagePattern() {
    Locale formattingLanguage = Ivy.session().getSessionUser().getFormattingLanguage();
    if (formattingLanguage == null) {
      formattingLanguage = Ivy.session().getFormattingLocale();
    }
    return ((SimpleDateFormat) DateFormat.getDateInstance(DateFormat.LONG, formattingLanguage)).toLocalizedPattern();
  }
  
}