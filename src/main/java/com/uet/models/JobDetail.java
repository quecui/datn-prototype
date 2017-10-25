package com.uet.models;

import java.util.List;

/**
 * Created by hung_pt on 7/28/17.
 */
public class JobDetail {
    String _class;
    List<Actions> actions;
    String description;
    String displayName;
    String displayNameOrNull;
    String fullDisplayName;
    String fullName;
    String name;
    String url;
    String buildable;
    List<Builds> builds;
    String color;
    Builds firstBuild;
    List<HealthReport> healthReport;
    boolean inQueue;
    boolean keepDependencies;
    Builds lastBuild;
    Builds lastCompletedBuild;
    Builds lastFailedBuild;
    String lastStableBuild;
    String lastSuccessfulBuild;
    String lastUnstableBuild;
    Builds lastUnsuccessfulBuild;
    int nextBuildNumber;
    List<Property> property;
//    String queueItem;
    QueueItem queueItem;
    boolean concurrentBuild;
    List<DownstreamProjects> downstreamProjects;
    Property scm;
    List<DownstreamProjects> upstreamProjects;

    public String get_class() {
        return _class;
    }

    public String getFullDisplayName() {
        return fullDisplayName;
    }

    public String getLastSuccessfulBuild() {
        if (lastSuccessfulBuild == null || lastSuccessfulBuild.equals(""))
            return "No build successfully! ";
        return lastSuccessfulBuild;
    }

    public void setLastSuccessfulBuild(String lastSuccessfulBuild) {
        this.lastSuccessfulBuild = lastSuccessfulBuild;
    }

    public void setFullDisplayName(String fullDisplayName) {
        this.fullDisplayName = fullDisplayName;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public List<Actions> getActions() {
        return actions;
    }

    public void setActions(List<Actions> actions) {
        this.actions = actions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayNameOrNull() {
        return displayNameOrNull;
    }

    public void setDisplayNameOrNull(String displayNameOrNull) {
        this.displayNameOrNull = displayNameOrNull;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBuildable() {
        return buildable;
    }

    public void setBuildable(String buildable) {
        this.buildable = buildable;
    }

    public List<Builds> getBuilds() {
        return builds;
    }

    public void setBuilds(List<Builds> builds) {
        this.builds = builds;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Builds getFirstBuild() {
        return firstBuild;
    }

    public void setFirstBuild(Builds firstBuild) {
        this.firstBuild = firstBuild;
    }

    public List<HealthReport> getHealthReport() {
        return healthReport;
    }

    public void setHealthReport(List<HealthReport> healthReport) {
        this.healthReport = healthReport;
    }

    public boolean isInQueue() {
        return inQueue;
    }

    public void setInQueue(boolean inQueue) {
        this.inQueue = inQueue;
    }

    public boolean isKeepDependencies() {
        return keepDependencies;
    }

    public void setKeepDependencies(boolean keepDependencies) {
        this.keepDependencies = keepDependencies;
    }

    public Builds getLastBuild() {
        return lastBuild;
    }

    public void setLastBuild(Builds lastBuild) {
        this.lastBuild = lastBuild;
    }

    public Builds getLastCompletedBuild() {
        return lastCompletedBuild;
    }

    public void setLastCompletedBuild(Builds lastCompletedBuild) {
        this.lastCompletedBuild = lastCompletedBuild;
    }

    public Builds getLastFailedBuild() {
        return lastFailedBuild;
    }

    public void setLastFailedBuild(Builds lastFailedBuild) {
        this.lastFailedBuild = lastFailedBuild;
    }

    public String getLastStableBuild() {
        return lastStableBuild;
    }

    public void setLastStableBuild(String lastStableBuild) {
        this.lastStableBuild = lastStableBuild;
    }

    public String getLastUnstableBuild() {
        return lastUnstableBuild;
    }

    public void setLastUnstableBuild(String lastUnstableBuild) {
        this.lastUnstableBuild = lastUnstableBuild;
    }

    public Builds getLastUnsuccessfulBuild() {
        return lastUnsuccessfulBuild;
    }

    public void setLastUnsuccessfulBuild(Builds lastUnsuccessfulBuild) {
        this.lastUnsuccessfulBuild = lastUnsuccessfulBuild;
    }

    public int getNextBuildNumber() {
        return nextBuildNumber;
    }

    public void setNextBuildNumber(int nextBuildNumber) {
        this.nextBuildNumber = nextBuildNumber;
    }

    public List<Property> getProperty() {
        return property;
    }

    public void setProperty(List<Property> property) {
        this.property = property;
    }

    public QueueItem getQueueItem() {
        return queueItem;
    }

    public void setQueueItem(QueueItem queueItem) {
        this.queueItem = queueItem;
    }

    public boolean isConcurrentBuild() {
        return concurrentBuild;
    }

    public void setConcurrentBuild(boolean concurrentBuild) {
        this.concurrentBuild = concurrentBuild;
    }

    public List<DownstreamProjects> getDownstreamProjects() {
        return downstreamProjects;
    }

    public void setDownstreamProjects(List<DownstreamProjects> downstreamProjects) {
        this.downstreamProjects = downstreamProjects;
    }

    public Property getScm() {
        return scm;
    }

    public void setScm(Property scm) {
        this.scm = scm;
    }

    public List<DownstreamProjects> getUpstreamProjects() {
        return upstreamProjects;
    }

    public void setUpstreamProjects(List<DownstreamProjects> upstreamProjects) {
        this.upstreamProjects = upstreamProjects;
    }
}
