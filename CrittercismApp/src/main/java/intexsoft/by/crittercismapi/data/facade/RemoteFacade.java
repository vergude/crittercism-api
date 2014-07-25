package intexsoft.by.crittercismapi.data.facade;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;

import intexsoft.by.crittercismapi.Constants;
import intexsoft.by.crittercismapi.CrittercismApplication;
import intexsoft.by.crittercismapi.data.remote.request.GraphRequest;
import intexsoft.by.crittercismapi.data.remote.request.GraphRequestInternal;
import intexsoft.by.crittercismapi.data.remote.response.AppSummaryData;
import intexsoft.by.crittercismapi.data.remote.response.GraphResponse;
import intexsoft.by.crittercismapi.data.remote.service.CrittercismAPIService;
import intexsoft.by.crittercismapi.settings.SettingsFacade;
import intexsoft.by.crittercismapi.utils.ThreadUtils;


@EBean(scope = EBean.Scope.Singleton)
public class RemoteFacade
{

	@RootContext
	protected Context context;

	@RestService
	protected CrittercismAPIService remoteService;

	public static RemoteFacade getInstance(Context context)
	{
		return RemoteFacade_.getInstance_(context);
	}

	public void getApps() {
		ThreadUtils.checkAndThrowIfUIThread();

        HashMap<String,AppSummaryData> response = remoteService.getApps();

        Log.d("**********", response.size() + "");
	}

    public void getErrorGraph() {
        ThreadUtils.checkAndThrowIfUIThread();

        HashMap<String,AppSummaryData> responseApp = remoteService.getApps();

        GraphRequest graphRequest = new GraphRequest();
        GraphRequestInternal graphRequestInternal = new GraphRequestInternal();
        graphRequestInternal.setAppId(responseApp.keySet().iterator().next());
        graphRequestInternal.setGraph(Constants.GRAPH);
        graphRequestInternal.setDuration(Constants.DURATION);

        graphRequest.setParams(graphRequestInternal);

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";



		try
		{
			jsonString = mapper.writeValueAsString(graphRequest);
			Log.d("**********", jsonString);
		}
		catch (JsonProcessingException e)
		{
			Log.d("*****error*****","Errrrrorrrrrrr");
			e.printStackTrace();
		}
//
//		String response = remoteService.getErrorGraph(jsonString);

		SettingsFacade settingsFacade = SettingsFacade.getInstance(CrittercismApplication.getApplication());

		// Set the Content-Type header
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(new MediaType("application","json"));
		requestHeaders.add("Authorization", "Bearer " + settingsFacade.getToken());
		HttpEntity<String> requestEntity = new HttpEntity<String>(jsonString, requestHeaders);

		// Create a new RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();

		// Add the Jackson and String message converters
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

		// Make the HTTP POST request, marshaling the request to JSON, and the response to a String
		ResponseEntity<String> responseEntity = restTemplate.exchange("https://developers.crittercism.com:443/v1.0/errorMonitoring/graph",
				HttpMethod.POST, requestEntity, String.class);
		String response = responseEntity.getBody();
        Log.d("**********", response + "");

        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper2 = new ObjectMapper(factory);
        TypeReference<GraphResponse> typeRef = new TypeReference<GraphResponse>() {};
        try {
            GraphResponse graphResponse = mapper2.readValue(response, typeRef);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
