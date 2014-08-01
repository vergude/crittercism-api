package intexsoft.by.crittercismapi;

public class Constants
{

    public static final String BASE_URL = "https://developers.crittercism.com/v1.0";

	public static final String CRITTERCISM_API_CLIENT_ID = "33VfTC2gd6soUEvBpSJeSHJNEJTcfN8F";
    public static final Integer DURATION_ONE_DAY = 1440;
	public static final Integer DURATION_MONTH = 34200;
    public static final String GRAPH_CRASHES = "crashes";
    public static final String GRAPH_APPLOADS = "appLoads";
    public static final String GROUP_BY_APP_VERSION = "appVersion";
	public static final String GROUP_BY_APP_ID = "appId";
    public static final long INTERVAL = 30000;

    public final static class Action
    {
        private Action()
        {
        }

        private static final String BASE = "intexsoft.by.crittercismapi.action";

        public static final String REQUEST_LOGIN = BASE + ".REQUEST_LOGIN";
		public static final String REQUEST_GET_APPS = BASE + ".REQUEST_GET_APPS";
		public static final String REQUEST_GET_DAILY_STATISTICS = BASE + ".REQUEST_GET_DAILY_STATISTICS";
    }
}
