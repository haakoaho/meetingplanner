package org.toastmasters.meetingplanner.dto.agenda;

import org.hibernate.annotations.Immutable;

import java.util.Map;

@Immutable
public class AgendaRole{
    private Long userId;
    private Long roleId;
    private String roleName;
    private String userName;

    public AgendaRole(Map<String,Object> map){
        userId = (Long) map.get("user_id");
        roleId = (Long) map.get("role_id");
        roleName = (String) map.get("role_name");
        userName = (String) map.get("user_name");
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
