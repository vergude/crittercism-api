package intexsoft.by.crittercismapi.event;

/**
 * Created by anastasya.konovalova on 22.06.2014.
 */
public class LoginPerformedEvent implements EventObserver.Event
{
	private boolean isSuccessful;

	private String errorMessage;

	public boolean isSuccessful()
	{
		return isSuccessful;
	}

	public void setSuccessful(boolean result)
	{
		this.isSuccessful = result;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}
}
