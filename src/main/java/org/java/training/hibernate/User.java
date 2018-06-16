package org.java.training.hibernate;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "T_USER")
public class User {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "USER_IDENTIFICATION")
    private String userId;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "EMAIL_ID")
    private String email;

    @Column(name = "CONTACT_PHONE_NUMBER")
    private String contactPhoneNum;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "PASSWORD_EXPIRY_DATE")
    private String passwordExpirityDate;

    @Column(name = "USER_STATUS")
    private String userStatus;

    @Column(name = "LOCK_FLAG")
    private Integer lockFlag;

    @Column(name = "FAILED_LOGIN_ATTEMPTS")
    private Integer failedLoginAttempts;

    @Column(name = "PASSWORD_SALT")
    private String passwordSalt;

    @Column(name = "LAST_FAILED_LOGIN_ATTEMPT")
    private String lastFaliedLoginAttempt;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Cascade(value = {CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    private Set<UserRole> roles;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactPhoneNum() {
        return contactPhoneNum;
    }

    public void setContactPhoneNum(String contactPhoneNum) {
        this.contactPhoneNum = contactPhoneNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordExpirityDate() {
        return passwordExpirityDate;
    }

    public void setPasswordExpirityDate(String passwordExpirityDate) {
        this.passwordExpirityDate = passwordExpirityDate;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getLockFlag() {
        return lockFlag;
    }

    public void setLockFlag(Integer lockFlag) {
        this.lockFlag = lockFlag;
    }

    public Integer getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(Integer failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getLastFaliedLoginAttempt() {
        return lastFaliedLoginAttempt;
    }

    public void setLastFaliedLoginAttempt(String lastFaliedLoginAttempt) {
        this.lastFaliedLoginAttempt = lastFaliedLoginAttempt;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (userId != null ? !userId.equals(user.userId) : user.userId != null) return false;
        if (fullName != null ? !fullName.equals(user.fullName) : user.fullName != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (contactPhoneNum != null ? !contactPhoneNum.equals(user.contactPhoneNum) : user.contactPhoneNum != null)
            return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (passwordExpirityDate != null ? !passwordExpirityDate.equals(user.passwordExpirityDate) : user.passwordExpirityDate != null)
            return false;
        if (userStatus != null ? !userStatus.equals(user.userStatus) : user.userStatus != null) return false;
        if (lockFlag != null ? !lockFlag.equals(user.lockFlag) : user.lockFlag != null) return false;
        if (failedLoginAttempts != null ? !failedLoginAttempts.equals(user.failedLoginAttempts) : user.failedLoginAttempts != null)
            return false;
        if (passwordSalt != null ? !passwordSalt.equals(user.passwordSalt) : user.passwordSalt != null) return false;
        return lastFaliedLoginAttempt != null ? lastFaliedLoginAttempt.equals(user.lastFaliedLoginAttempt) : user.lastFaliedLoginAttempt == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (contactPhoneNum != null ? contactPhoneNum.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (passwordExpirityDate != null ? passwordExpirityDate.hashCode() : 0);
        result = 31 * result + (userStatus != null ? userStatus.hashCode() : 0);
        result = 31 * result + (lockFlag != null ? lockFlag.hashCode() : 0);
        result = 31 * result + (failedLoginAttempts != null ? failedLoginAttempts.hashCode() : 0);
        result = 31 * result + (passwordSalt != null ? passwordSalt.hashCode() : 0);
        result = 31 * result + (lastFaliedLoginAttempt != null ? lastFaliedLoginAttempt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                '}';
    }
}
