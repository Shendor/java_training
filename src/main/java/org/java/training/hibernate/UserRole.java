package org.java.training.hibernate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_USER_ROLE")
@IdClass(value = UserRole.class)
public class UserRole implements Serializable {

    @Id
    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @Column(name = "ROLE_CODE")
    private String roleCode;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "user=" + user.getFullName() +
                ", roleCode='" + roleCode + '\'' +
                '}';
    }
}
