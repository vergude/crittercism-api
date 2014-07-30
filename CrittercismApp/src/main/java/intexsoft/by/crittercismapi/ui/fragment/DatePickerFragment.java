package intexsoft.by.crittercismapi.ui.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

import intexsoft.by.crittercismapi.R;

/**
 * Created by vadim on 26.07.2014.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
{

    private FragmentDatePickerInterface fragmentDatePickerInterface;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        fragmentDatePickerInterface=(FragmentDatePickerInterface) getTargetFragment();
        final Calendar calendar = fragmentDatePickerInterface.getCalendar();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view,int intYear,int intMonth, int intDay)
    {
        String[]mounts=getResources().getStringArray(R.array.year);
        String strDay = Integer.toString(intDay);
        String strMonth = mounts[intMonth];
        String strYear = Integer.toString(intYear);
        String date = strDay.concat(", ").concat(strMonth).concat(" ").concat(strYear);
        fragmentDatePickerInterface.setDate(date);

    }

    public interface FragmentDatePickerInterface
    {
        public Calendar getCalendar();
        public void setDate(String date);
    }
}