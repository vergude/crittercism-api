package intexsoft.by.crittercismapi.data.remote.response;

/**
 * Created by Евгений on 25.07.2014.
 */
public class SeriesData
{
    private String name;
    private String label;
    private Integer [] points;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer[] getPoints() {
        return points;
    }

    public void setPoints(Integer[] points) {
        this.points = points;
    }
}
