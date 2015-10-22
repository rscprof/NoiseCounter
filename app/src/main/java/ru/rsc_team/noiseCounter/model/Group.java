package ru.rsc_team.noiseCounter.model;

import java.io.Serializable;

/**
 * Created by rscprof on 14.10.15.
 * Group is a class, that encapsulated counter, and provide listeners for events
 */
//TODO delete listener's
public interface Group extends Serializable {

    //Tick counter
    void tick();

    //Clear counter
    @SuppressWarnings("unused")
    void clear();

    //Get count
    int getCount();

    //set listener for tick
    int addTickListener(GroupTickListener listener);
    void dropTickListener(int position);

    //set listener for border
    @SuppressWarnings({"EmptyMethod", "unused"})
    void addBorderListener(GroupBorderListener listener);

    //get name
    String getName();

}
