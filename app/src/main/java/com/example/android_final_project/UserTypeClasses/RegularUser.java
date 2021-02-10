package com.example.android_final_project.UserTypeClasses;

import java.util.List;

public class RegularUser extends GeneralUser
{
    private List<String> favouriteGenres;
    private int age;


    public RegularUser(String firstName, String lastName, String email, String password, List<String> favouriteGenres, int age) {
        super(firstName, lastName, email, password);
        this.favouriteGenres = favouriteGenres;
        this.age = age;
    }

    public List<String> getFavouriteGenres()
    {
        return this.favouriteGenres;
    }

    public boolean isGenreLiked(String genre)
    {
        if(favouriteGenres.contains(genre))
            return true;
        else return false;
    }

    @Override
    public String getFirstName() {
        return super.getFirstName();
    }

    @Override
    public String getLastName() {
        return super.getLastName();
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }


    @Override
    public String getPassword() {
        return super.getPassword();
    }

    public int getAge()
    {
        return this.age;
    }
}
