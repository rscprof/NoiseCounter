package ru.rsc_team.noiseCounter.model;

/**
 * Created by rscprof on 14.10.15.
 *
 * This listener start when group overflow counter (perhaps, 100)
 */
@SuppressWarnings("unused")
public interface GroupBorderListener {

    void border();

}
