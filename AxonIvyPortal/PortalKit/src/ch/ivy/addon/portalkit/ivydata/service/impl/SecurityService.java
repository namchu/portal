package ch.ivy.addon.portalkit.ivydata.service.impl;

import static ch.ivyteam.ivy.server.ServerFactory.getServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import ch.ivy.addon.portalkit.ivydata.dto.IvySecurityResultDTO;
import ch.ivy.addon.portalkit.ivydata.exception.PortalIvyDataErrorType;
import ch.ivy.addon.portalkit.ivydata.exception.PortalIvyDataException;
import ch.ivy.addon.portalkit.ivydata.service.ISecurityService;
import ch.ivy.addon.portalkit.ivydata.utils.ServiceUtilities;
import ch.ivyteam.ivy.application.ActivityState;
import ch.ivyteam.ivy.application.IApplication;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.security.IRole;
import ch.ivyteam.ivy.security.ISecurityConstants;
import ch.ivyteam.ivy.security.IUser;

public class SecurityService implements ISecurityService {

  private SecurityService() {}

  public static SecurityService newInstance() {
    return new SecurityService();
  }


  @Override
  public IvySecurityResultDTO findUsers(List<String> apps) throws Exception {
    return getServer().getSecurityManager().executeAsSystem(() -> {
      IvySecurityResultDTO result = new IvySecurityResultDTO();
      if (CollectionUtils.isEmpty(apps)) {
        return result;
      }

      List<PortalIvyDataException> errors = new ArrayList<>();
      Map<String, List<IUser>> usersByApp = new HashMap<>();
      for (String appName : apps) {
        try {
          IApplication app = ServiceUtilities.findApp(appName);
          usersByApp.put(appName, ServiceUtilities.findAllUsers(app));
        } catch (PortalIvyDataException e) {
          errors.add(e);
        } catch (Exception ex) {
          Ivy.log().error("Error in getting users within app {0}", ex, appName);
        }
      }
      result.setErrors(errors);
      result.setUsersByApp(usersByApp);
      return result;
    });
  }
  
  @Override
  public IvySecurityResultDTO findUsers(IApplication app) throws Exception {
    return getServer().getSecurityManager().executeAsSystem(() -> {
      IvySecurityResultDTO result = new IvySecurityResultDTO();
      List<PortalIvyDataException> errors = new ArrayList<>();
      if (app.getActivityState() != ActivityState.ACTIVE) {
        result.setErrors(Arrays.asList(new PortalIvyDataException(app.getName(), PortalIvyDataErrorType.APP_NOT_FOUND.toString())));
        return result;
      }

      try {
        List<IUser> users = app.getSecurityContext().getUsers().stream()
            .filter(user -> !StringUtils.equals(ISecurityConstants.SYSTEM_USER_NAME, user.getName()))
            .sorted((u1, u2) -> StringUtils.compareIgnoreCase(u1.getDisplayName(), u2.getDisplayName()))
            .collect(Collectors.toList());
        result.setUsers(users);
      } catch (Exception ex) {
        Ivy.log().error("Error in getting users within app {0}", ex, app.getName());
      }
      result.setErrors(errors);
      return result;
    });
  }

  @Override
  public IvySecurityResultDTO findSecurityMembers(IApplication app) throws Exception {
    return getServer().getSecurityManager().executeAsSystem(() -> {
      IvySecurityResultDTO result = new IvySecurityResultDTO();
      List<PortalIvyDataException> errors = new ArrayList<>();
      if (app.getActivityState() != ActivityState.ACTIVE) {
        result.setErrors(Arrays.asList(new PortalIvyDataException(app.getName(), PortalIvyDataErrorType.APP_NOT_FOUND.toString())));
        return result;
      }

      try {
        List<IRole> roles = app.getSecurityContext().getRoles().stream()
            .filter(role -> role.getProperty("HIDE") != null)
            .sorted((r1, r2) -> StringUtils.compareIgnoreCase(r1.getDisplayName(), r2.getDisplayName()))
            .collect(Collectors.toList());
        result.setRoles(roles);
        
        List<IUser> users = app.getSecurityContext().getUsers().stream()
            .filter(user -> !StringUtils.equals(ISecurityConstants.SYSTEM_USER_NAME, user.getName()))
            .sorted((u1, u2) -> StringUtils.compareIgnoreCase(u1.getDisplayName(), u2.getDisplayName()))
            .collect(Collectors.toList());
        result.setUsers(users);
      } catch (Exception ex) {
        Ivy.log().error("Error in getting security members within app {0}", ex, app.getName());
      }
      result.setErrors(errors);
      return result;
    });
  }

  @Override
  public IvySecurityResultDTO findSecurityMembers(List<String> apps) throws Exception {
    return getServer().getSecurityManager().executeAsSystem(() -> {
      IvySecurityResultDTO result = new IvySecurityResultDTO();
      List<PortalIvyDataException> errors = new ArrayList<>();
      List<IRole> roles = new ArrayList<>();
      List<IUser> users = new ArrayList<>();

      for (String appName : apps) {
        try {
          IApplication app = ServiceUtilities.findApp(appName);
          roles.addAll(app.getSecurityContext().getRoles().stream()
              .filter(role -> role.getProperty("HIDE") != null)
              .collect(Collectors.toList()));
          
          users.addAll(app.getSecurityContext().getUsers().stream()
              .filter(user -> !StringUtils.equals(ISecurityConstants.SYSTEM_USER_NAME, user.getName()))
              .collect(Collectors.toList()));
        } catch (Exception ex) {
          Ivy.log().error("Error in getting security members within app {0}", ex, appName);
        }
      }
      roles = roles.stream().distinct().sorted((r1, r2) -> StringUtils.compareIgnoreCase(r1.getDisplayName(), r2.getDisplayName())).collect(Collectors.toList());
      users = users.stream().distinct().sorted((u1, u2) -> StringUtils.compareIgnoreCase(u1.getDisplayName(), u2.getDisplayName())).collect(Collectors.toList());
      
      result.setUsers(users);
      result.setRoles(roles);
      result.setErrors(errors);
      return result;
    });
  }
}
