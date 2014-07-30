package intexsoft.by.crittercismapi.data.remote.response;

/**
 * Created by vadim on 28.07.2014.
 */

public class AppData {
    private String appName;
    private Integer startsPerDay;
    private Integer crashes;
    private Integer crashPercent;
    private String date;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getStartsPerDay() {
        return startsPerDay;
    }

    public void setStartsPerDay(Integer startsPerDay) {
        this.startsPerDay = startsPerDay;
    }

    public Integer getCrashes() {
        return crashes;
    }

    public void setCrashes(Integer crashes) {
        this.crashes = crashes;
    }

    public Integer getCrashPercent() {
        return crashPercent;
    }

    public void setCrashPercent(Integer crashPercent) {
        this.crashPercent = crashPercent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
