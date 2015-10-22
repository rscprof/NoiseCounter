package ru.rsc_team.noiseCounter.model;

import java.io.Serializable;

/**
 * Created by rscprof on 14.10.15.
 */
//TODO delete listener's
public interface Group extends Serializable {

    //Tick counter
    public void tick();

    //Clear counter
    public void clear();

    //Get count
    public int getCount();

    //set listener for tick
    public int addTickListener(GroupTickListener listener);
    public void dropTickListener (int position);

    //set listener for border
    public void addBorderListener(GroupBorderListener listener);

    //get name
    public String getName();

}
