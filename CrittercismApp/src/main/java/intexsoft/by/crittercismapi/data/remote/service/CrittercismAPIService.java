package intexsoft.by.crittercismapi.data.remote.service;

import intexsoft.by.crittercismapi.Constants;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by anastasya.konovalova on 21.07.2014.
 */
@Rest(rootUrl = Constants.BASE_URL, converters = {StringHttpMessageConverter.class}, interceptors = HeadersRequestInterceptor.class)
public interface CrittercismAPIService
{

	RestTemplate getRestTemplate();

	@Get("/apps?attributes=appName,crashPercent")
	String getApps();
}
