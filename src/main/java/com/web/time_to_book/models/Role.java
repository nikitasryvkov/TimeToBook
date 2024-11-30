package com.web.time_to_book.models;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    private String name;
    private Set<User> users;

    public Role(String name) {
        this.name = name;
    }

    protected Role() {
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @OneToMany(mappedBy = "role", targetEntity = User.class)
    public Set<User> getUsers() {
        return users;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
