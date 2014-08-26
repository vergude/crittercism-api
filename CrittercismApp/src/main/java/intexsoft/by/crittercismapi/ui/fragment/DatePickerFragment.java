package intexsoft.by.crittercismapi.ui.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by vadim on 26.07.2014.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
{

	private FragmentDatePickerInterface fragmentDatePickerInterface;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		fragmentDatePickerInterface = (FragmentDatePickerInterface) getTargetFragment();

		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(fragmentDatePickerInterface.getCurrentDate());

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	@Override
	public void onDateSet(DatePicker view, int intYear, int intMonth, int intDay)
	{
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(fragmentDatePickerInterface.getCurrentDate());
		calendar.set(intYear, intMonth, intDay);

		fragmentDatePickerInterface.setSelectedDate(calendar.getTime());
	}

	public interface FragmentDatePickerInterface
	{
		Date getCurrentDate();

		void setSelectedDate(Date date);
	}
}