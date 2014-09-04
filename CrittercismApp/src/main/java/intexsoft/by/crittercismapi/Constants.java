package intexsoft.by.crittercismapi;

public final class Constants
{

	private Constants()
	{

	}

    public static final String BASE_URL = "https://developers.crittercism.com/v1.0";

	public static final String CRITTERCISM_API_CLIENT_ID = "33VfTC2gd6soUEvBpSJeSHJNEJTcfN8F";
    public static final Integer DURATION_ONE_DAY = 1440;
	public static final Integer DURATION_ONE_MONTH = 43200;
    public static final String GRAPH_CRASHES = "crashes";
    public static final String GRAPH_APPLOADS = "appLoads";
    public static final String GROUP_BY_APP_VERSION = "appVersion";
	public static final String GROUP_BY_APP_ID = "appId";
	/*24*60*60*1000;*/
    public static final long INTERVAL = 3 * 60 * 1000;
	public static final long MILLISECONDS_OF_MONTH = 2592000000L;

	public static final class Action
    {
        private Action()
        {
        }

        private static final String BASE = "intexsoft.by.crittercismapi.action";

        public static final String REQUEST_LOGIN = BASE + ".REQUEST_LOGIN";
		public static final String REQUEST_GET_APPS = BASE + ".REQUEST_GET_APPS";
		public static final String REQUEST_GET_AND_SAVE_DAILY_STATISTICS = BASE + ".REQUEST_GET_AND_SAVE_DAILY_STATISTICS";
		public static final String REQUEST_GET_TODAY_STATISTICS = BASE + ".REQUEST_GET_TODAY_STATISTICS";
		public static final String REQUEST_GET_APP_DETAILS_ERROR = "REQUEST_GET_APP_DETAILS_ERROR";
    }
}
