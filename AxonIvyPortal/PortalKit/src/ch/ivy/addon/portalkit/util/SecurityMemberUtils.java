package ch.ivy.addon.portalkit.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import ch.ivy.addon.portalkit.constant.PortalConstants;
import ch.ivy.addon.portalkit.dto.RoleDTO;
import ch.ivy.addon.portalkit.dto.SecurityMemberDTO;
import ch.ivy.addon.portalkit.dto.UserDTO;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.process.call.SubProcessCall;
import ch.ivyteam.ivy.security.IRole;
import ch.ivyteam.ivy.security.ISecurityMember;

public class SecurityMemberUtils {
  
  public static SecurityMemberDTO getCurrentSessionUserAsSecurityMemberDTO() {
    return IvyExecutor.executeAsSystem(() -> {
      return new SecurityMemberDTO(Ivy.session().getSessionUser());
    });
  }
  
  @SuppressWarnings("unchecked")
  public static List<SecurityMemberDTO> findSecurityMembers(String query, int startIndex, int count) {
    return IvyExecutor.executeAsSystem(() -> {
      if (Ivy.request().getApplication().getName().equals(PortalConstants.PORTAL_APPLICATION_NAME)) {
        Map<String, List<SecurityMemberDTO>> usersByApp = SubProcessCall.withPath(PortalConstants.SECURITY_SERVICE_CALLABLE)
            .withStartName("findSecurityMembersOverAllApplications")
            .withParam("username", Ivy.session().getSessionUserName())
            .withParam("query", query)
            .withParam("startIndex", startIndex)
            .withParam("count", count)
            .call()
            .get("members", Map.class);
        return usersByApp.values().stream().flatMap(List::stream)
            .collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(SecurityMemberDTO::getName))), ArrayList::new));
      }
      
      return SubProcessCall.withPath(PortalConstants.SECURITY_SERVICE_CALLABLE)
          .withStartName("findSecurityMembers")
          .withParam("application", Ivy.request().getApplication())
          .withParam("query", query)
          .withParam("startIndex", startIndex)
          .withParam("count", count)
          .call()
          .get("members", List.class);
    });
  }
  
  public static List<SecurityMemberDTO> convertIRoleToSecurityMemberDTO(List<IRole> roles) {
    return IvyExecutor.executeAsSystem(() -> {
      return roles.stream().map(role -> new SecurityMemberDTO(role)).collect(Collectors.toList());
    });
  }
  
  public static ISecurityMember findISecurityMemberFromUserDTO(UserDTO userDTO) {
    return IvyExecutor.executeAsSystem(() -> {
        return Ivy.wf().getSecurityContext().findUser(userDTO.getId());
    });
  }
  
  public static ISecurityMember findISecurityMemberFromRoleDTO(RoleDTO roleDTO) {
    return IvyExecutor.executeAsSystem(() -> {
        return Ivy.wf().getSecurityContext().findRole(roleDTO.getId());
    });
  }
}
