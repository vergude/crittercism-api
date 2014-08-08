package intexsoft.by.crittercismapi.data.remote.service;

import intexsoft.by.crittercismapi.Constants;
import intexsoft.by.crittercismapi.data.remote.request.GraphRequest;
import intexsoft.by.crittercismapi.data.remote.request.PieRequest;
import intexsoft.by.crittercismapi.data.remote.response.AppSummaryData;
import intexsoft.by.crittercismapi.data.remote.response.GraphResponse;
import intexsoft.by.crittercismapi.data.remote.response.PieResponse;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by anastasya.konovalova on 21.07.2014.
 */
@Rest(rootUrl = Constants.BASE_URL, converters = {MappingJackson2HttpMessageConverter.class},
		interceptors = HeadersRequestInterceptor.class)
public interface CrittercismAPIService
{

	RestTemplate getRestTemplate();

	@Get("/apps?attributes=appName,crashPercent")
	HashMap<String, AppSummaryData> getApps();

	@Post("/errorMonitoring/graph")
	GraphResponse getErrorGraph(GraphRequest graphRequest);

	@Post("/errorMonitoring/pie")
	PieResponse getErrorGraphAllApps(PieRequest pieRequest);

}
