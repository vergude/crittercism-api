package intexsoft.by.crittercismapi.data.remote.entity;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by anastasya.konovalova on 11.06.2014.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse implements Serializable {

	@JsonProperty(value = "access_token")
    private String accessToken;

	@JsonProperty(value = "token_type")
	private String tokenType;

	@JsonProperty(value = "expires_in")
	private String expiresIn;

	@JsonProperty(value = "error")
	private String error;

	@JsonProperty(value = "error_description")
	private String errorDescription;

	public String getAccessToken()
	{
		return accessToken;
	}

	public void setAccessToken(String accessToken)
	{
		this.accessToken = accessToken;
	}

	public String getTokenType()
	{
		return tokenType;
	}

	public void setTokenType(String tokenType)
	{
		this.tokenType = tokenType;
	}

	public String getExpiresIn()
	{
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn)
	{
		this.expiresIn = expiresIn;
	}

	public String getError()
	{
		return error;
	}

	public void setError(String error)
	{
		this.error = error;
	}

	public String getErrorDescription()
	{
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription)
	{
		this.errorDescription = errorDescription;
	}
}
