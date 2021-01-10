package com.example.android_final_project.UserTypeClasses;

import java.util.List;

public class regularUser extends GeneralUser
{
    private List<String> favouriteGenres;
    private int age;


    public regularUser(String firstName, String lastName, String email, String password, List<String> favouriteGenres, int age) {
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

    public int getAge()
    {
        return this.age;
    }
}
