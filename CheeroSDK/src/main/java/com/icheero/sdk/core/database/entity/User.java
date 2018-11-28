package com.icheero.sdk.core.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "t_user")
public class User
{
    @Id
    @Property(nameInDb = "u_id")
    private Long id;
    @Property(nameInDb = "u_name")
    private String name;
    @Property(nameInDb = "u_date")
    private String date;

    @Generated(hash = 1927182208)
    public User(Long id, String name, String date)
    {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    @Generated(hash = 586692638)
    public User()
    {
    }

    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDate()
    {
        return this.date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }
}
