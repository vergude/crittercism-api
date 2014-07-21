package intexsoft.by.crittercismapi.data.remote.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Евгений on 21.07.2014.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppSummaryResponse {

    private AppSummaryData [] appSummaryData;
}
