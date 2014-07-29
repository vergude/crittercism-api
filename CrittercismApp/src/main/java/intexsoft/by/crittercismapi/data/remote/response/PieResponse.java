package intexsoft.by.crittercismapi.data.remote.response;

import intexsoft.by.crittercismapi.data.remote.request.PieRequestInternal;

/**
 * Created by Евгений on 29.07.2014.
 */
public class PieResponse
{
    private PieRequestInternal params;
    private SeriesCollection data;

    public PieRequestInternal getParams() {
        return params;
    }

    public void setParams(PieRequestInternal params) {
        this.params = params;
    }

    public SeriesCollection getData() {
        return data;
    }

    public void setData(SeriesCollection data) {
        this.data = data;
    }
}
