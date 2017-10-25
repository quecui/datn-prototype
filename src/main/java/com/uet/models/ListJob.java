package com.uet.models;

import java.util.List;

/**
 * Created by hung_pt on 7/28/17.
 */
public class ListJob {
    private String _class;
    private List<Jobs> jobs;

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public List<Jobs> getJobs() {
        return jobs;
    }

    public void setJobs(List<Jobs> jobs) {
        this.jobs = jobs;
    }
}
