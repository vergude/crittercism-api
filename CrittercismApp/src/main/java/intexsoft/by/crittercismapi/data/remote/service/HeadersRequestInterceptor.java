package intexsoft.by.crittercismapi.data.remote.service;

import intexsoft.by.crittercismapi.CrittercismApplication;
import intexsoft.by.crittercismapi.settings.SettingsFacade;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * Created by anastasya.konovalova on 21.07.2014.
 */
public class HeadersRequestInterceptor implements ClientHttpRequestInterceptor
{
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException
	{
		SettingsFacade settingsFacade = SettingsFacade.getInstance(CrittercismApplication.getApplication());

		request.getHeaders().set("Authorization", "Bearer " + settingsFacade.getToken());
		return execution.execute(request, body);
	}
}
