package intexsoft.by.crittercismapi.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import intexsoft.by.crittercismapi.Constants;
import intexsoft.by.crittercismapi.CrittercismApplication;
import intexsoft.by.crittercismapi.data.remote.response.LoginResponse;
import intexsoft.by.crittercismapi.event.EventObserver;
import intexsoft.by.crittercismapi.event.LoginPerformedEvent;
import intexsoft.by.crittercismapi.manager.LoginManager;
import intexsoft.by.crittercismapi.utils.StringUtils;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EIntentService;
import org.androidannotations.annotations.ServiceAction;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anastasya.konovalova on 20.06.14.
 */

@EIntentService
public class LoginService extends IntentService
{
	public static final String TAG = LoginService.class.getSimpleName();
	public static final String PASSWORD_KEY = "password";
	public static final String GRANT_TYPE = PASSWORD_KEY;
	public static final int STATUS_CODE = 200;
	public static final int NAME_VALUE_PAIR_SIZE = 3;


	@Bean
	LoginManager loginManager;

	public LoginService()
	{
		super(LoginService.class.getSimpleName());
	}

	public static void login(String userName, String password)
	{
		Context context = CrittercismApplication.getApplication();
		LoginService_.intent(context).fetchLogin(userName, password).start();
	}


	@Override
	protected void onHandleIntent(Intent intent)
	{
		//Do nothing
	}

	@ServiceAction(Constants.Action.REQUEST_LOGIN)
	protected void fetchLogin(String userName, String password)
	{
		LoginResponse loginResponse = doBasicAuth(userName, password);

		LoginPerformedEvent event = new LoginPerformedEvent();
		if (loginResponse == null)
		{
			event.setSuccessful(false);
			Log.d(TAG, "error");
		}
		if (loginResponse != null && StringUtils.isNotEmpty(loginResponse.getError()))
		{
			event.setSuccessful(false);
			event.setErrorMessage(loginResponse.getErrorDescription());

			Log.d(TAG, "error  " + loginResponse.getErrorDescription());
		}
		if (loginResponse != null && StringUtils.isEmpty(loginResponse.getError()))
		{
			event.setSuccessful(true);
			loginManager.saveLoginData(userName, password, loginResponse.getAccessToken(), Integer.valueOf(loginResponse.getExpiresIn()));
		}

		Context context = CrittercismApplication.getApplication();
		EventObserver.sendEvent(context, event);
	}

	private String getBase64EncodedCredentials(String password)
	{
		String base64EncodedCredentials = "Basic " + Base64.encodeToString(
				(Constants.CRITTERCISM_API_CLIENT_ID + ":" + password).getBytes(),
				Base64.NO_WRAP);
		return base64EncodedCredentials;
	}

	private List<NameValuePair> getParameters(String userName, String password)
	{
		List<NameValuePair> parameters = new ArrayList<NameValuePair>(NAME_VALUE_PAIR_SIZE);
		parameters.add(new BasicNameValuePair("grant_type", GRANT_TYPE));
		parameters.add(new BasicNameValuePair("username", userName));
		parameters.add(new BasicNameValuePair(PASSWORD_KEY, password));

		return parameters;
	}

	LoginResponse doBasicAuth(String userName, String password)
	{
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("https://developers.crittercism.com/v1.0/token");

		HttpResponse response;

		try
		{
			httppost.setHeader("Authorization", getBase64EncodedCredentials(password));
			httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
			httppost.setEntity(new UrlEncodedFormEntity(getParameters(userName, password)));

			// Execute HTTP Post Request
			response = httpclient.execute(httppost);

			if (response.getStatusLine().getStatusCode() == STATUS_CODE)
			{
				Log.d("response ok", "ok response :/");
			}
			else
			{
				Log.d("response not ok", "Something went wrong :/");
			}

			String responseBody = EntityUtils.toString(response.getEntity());

			JsonFactory factory = new JsonFactory();
			ObjectMapper mapper = new ObjectMapper(factory);

			LoginResponse loginResponse = null;
			if (responseBody != null)
			{
				loginResponse = mapper.readValue(responseBody, LoginResponse.class);
			}

			return loginResponse;

		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}

}
