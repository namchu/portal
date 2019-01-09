package ch.ivy.addon.portalkit.ivydata.bo;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import ch.ivyteam.ivy.security.IUser;

public class IvyAbsence {

  private IUser user;
  private Date from;
  private Date until;
  private String comment;

  public IUser getUser() {
    return user;
  }

  public void setUser(IUser user) {
    this.user = user;
  }

  public Date getFrom() {
    return from;
  }

  public void setFrom(Date from) {
    this.from = from;
  }

  public Date getUntil() {
    return until;
  }

  public void setUntil(Date until) {
    this.until = until;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
  
  public String getUsername() {
    if (user != null) {
      return user.getName();
    }
    return StringUtils.EMPTY;
  }
  
  public String getFullname() {
    if (user != null) {
      return user.getFullName();
    }
    return StringUtils.EMPTY;
  }
  
  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(user.getName())
        .append(from)
        .append(until)
        .toHashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if(obj instanceof IvyAbsence){
      final IvyAbsence other = (IvyAbsence) obj;
      return new EqualsBuilder()
          .append(user.getName(), other.user.getName())
          .append(from, other.from)
          .append(until, other.until)
          .isEquals();
  } else{
      return false;
  }
  }
}
