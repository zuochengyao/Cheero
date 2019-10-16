package com.icheero.app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable
{
    private String name;
    private int age;
    private String phoneNumber;

    public User()
    {

    }

    protected User(Parcel in)
    {
        name = in.readString();
        age = in.readInt();
        phoneNumber = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>()
    {
        @Override
        public User createFromParcel(Parcel in)
        {
            return new User(in);
        }

        @Override
        public User[] newArray(int size)
        {
            return new User[size];
        }
    };

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeString(phoneNumber);
    }
}
