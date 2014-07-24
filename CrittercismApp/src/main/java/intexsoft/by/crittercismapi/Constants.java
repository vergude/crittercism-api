package intexsoft.by.crittercismapi;

public class Constants
{

    public static final String BASE_URL = "https://developers.crittercism.com/v1.0";

	public static final String CRITTERCISM_API_CLIENT_ID = "33VfTC2gd6soUEvBpSJeSHJNEJTcfN8F";
    public static final Integer DURATION = 43200;
    public static final String GRAPH = "crashes";

    public final static class Action
    {
        private Action()
        {
        }

        private static final String BASE = "intexsoft.by.crittercismapi.action";

        public static final String REQUEST_LOGIN = BASE + ".REQUEST_LOGIN";
		public static final String REQUEST_GET_APPS = BASE + ".REQUEST_GET_APPS";
    }
}
