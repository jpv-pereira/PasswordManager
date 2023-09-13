package com.jpvp.backend.Model;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String userName;

    private String lastName;
    private String phoneNumber;

    /*
        Add a one to many relation between the client and passwords that they have added to their account
    */
    @OneToMany
    private List<StoredPassword> storedPasswordList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<StoredPassword> getStoredPasswordList() {
        return storedPasswordList;
    }

    public void setStoredPasswordList(List<StoredPassword> storedPasswordList) {
        this.storedPasswordList = storedPasswordList;
    }
}
