package com.webapps2020.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

//All queries used in SystemUserGroup
@Entity
@NamedQueries({
    @NamedQuery(name = "isAdmin", query = "SELECT COUNT(e) from SystemUserGroup e where e.email = :email AND e.groupname = :groupName"),})

/**
 * The Entity class of SystemUserGroup.
 */
public class SystemUserGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;    //Auto generated ID.

    private String email;   //Email of the user.
    private String groupname;   //Group name of the user

    public SystemUserGroup() {
    }

    /**
     *
     * @param email Email of the user.
     * @param users Group name of the user.
     */
    public SystemUserGroup(String email, String users) {
        this.email = email;
        this.groupname = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SystemUserGroup)) {
            return false;
        }
        SystemUserGroup other = (SystemUserGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Override
    public String toString() {
        return "com.webapps2020.entity.SystemUserGroup[ id=" + id + " ]";
    }

}
