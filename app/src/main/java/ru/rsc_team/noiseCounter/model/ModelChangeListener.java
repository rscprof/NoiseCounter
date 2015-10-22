package ru.rsc_team.noiseCounter.model;

/**
 * Created by rscprof on 14.10.15.
 *
 * Event fire when model change (for change UI and/or saving)
 */
public interface ModelChangeListener {

    @SuppressWarnings("UnusedParameters")
    void onChange(@SuppressWarnings("UnusedParameters") Model model);
}
