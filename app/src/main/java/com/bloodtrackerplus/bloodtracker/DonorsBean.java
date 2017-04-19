package com.bloodtrackerplus.bloodtracker;

import java.io.Serializable;

/**
 * Created by Neelam on 3/12/2016.
 */
public class DonorsBean implements Serializable
{

    String mob;
    String name;

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
