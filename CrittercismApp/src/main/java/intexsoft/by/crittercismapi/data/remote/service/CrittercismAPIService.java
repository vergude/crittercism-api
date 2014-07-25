package intexsoft.by.crittercismapi.data.remote.service;

import intexsoft.by.crittercismapi.Constants;
import intexsoft.by.crittercismapi.data.remote.entity.AppSummaryData;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by anastasya.konovalova on 21.07.2014.
 */
@Rest(rootUrl = Constants.BASE_URL, converters = {MappingJackson2HttpMessageConverter.class, StringHttpMessageConverter.class}, interceptors = HeadersRequestInterceptor.class)
public interface CrittercismAPIService
{

	RestTemplate getRestTemplate();

	@Get("/apps?attributes=appName,crashPercent")
    HashMap<String,AppSummaryData> getApps();

    @Post("/errorMonitoring/graph")
    String getErrorGraph (String graphRequest);

}
