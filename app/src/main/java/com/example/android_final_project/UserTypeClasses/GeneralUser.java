package com.example.android_final_project.UserTypeClasses;

public abstract class GeneralUser
{
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String password;

    public GeneralUser(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    protected String getFirstName()
    {
        return this.firstName;
    }
    protected String getLastName()
    {
        return this.lastName;
    }
    protected String getEmail()
    {
        return this.email;
    }
    protected String getFullname()
    {
        String fullName = this.firstName + this.lastName;
        return fullName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
