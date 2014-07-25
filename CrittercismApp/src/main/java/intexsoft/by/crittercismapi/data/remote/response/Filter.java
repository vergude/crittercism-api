package intexsoft.by.crittercismapi.data.remote.response;

/**
 * Created by Евгений on 25.07.2014.
 */
public class Filter
{
    private String appVersion;
    private String os;
    private String carrier;
    private String device;

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
