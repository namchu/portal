package ch.ivy.addon.portalkit.bean;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import ch.ivy.addon.portalkit.dto.UserDTO;
import ch.ivy.addon.portalkit.util.SecurityMemberDisplayNameUtils;
import ch.ivyteam.ivy.security.ISecurityMember;

@ManagedBean
@ApplicationScoped
/**
 * This bean provide some methods to generate display name for {@link ISecurityMember}
 * and {@link UserDTO}
 */
public class SecurityMemberDisplayNameFormatBean implements Serializable {
  private static final long serialVersionUID = 1L;
  
  public String generateBriefDisplayNameForSecurityMember(ISecurityMember member, String securityMemberName) {
    return SecurityMemberDisplayNameUtils.generateBriefDisplayNameForSecurityMember(member, securityMemberName);
  }
  
  public String generateFullDisplayNameForSecurityMember(ISecurityMember member, String securityMemberName) {
    return SecurityMemberDisplayNameUtils.generateFullDisplayNameForSecurityMember(member, securityMemberName);
  }
  
  public String generateFullDisplayNameForUserDTO(UserDTO user) {
    return SecurityMemberDisplayNameUtils.generateFullDisplayNameForUserDTO(user);
  }
}
