package intexsoft.by.crittercismapi.data.remote.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Евгений on 25.07.2014.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeriesCollection
{
    private String start;
    private String end;
    private Integer interval;
    private SeriesData [] series;
    private SeriesData [] slices;

    public SeriesData[] getSlices() {
        return slices;
    }

    public void setSlices(SeriesData[] slices) {
        this.slices = slices;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public SeriesData[] getSeries() {
        return series;
    }

    public void setSeries(SeriesData[] series) {
        this.series = series;
    }
}
