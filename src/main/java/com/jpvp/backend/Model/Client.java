package com.jpvp.backend.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String firstName;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
