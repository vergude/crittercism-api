package intexsoft.by.crittercismapi.ui.dialogs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;


/**
 * Created by vadim on 26.07.2014.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
{

    private FragmentDatePickerInterface fragmentDatePickerInterface;

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        fragmentDatePickerInterface = (FragmentDatePickerInterface) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final Calendar calendar = fragmentDatePickerInterface.getCalendar();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        String date = Integer.toString(day).concat(".").concat(Integer.toString(month+1).concat(".").concat(Integer.toString(year)));
        fragmentDatePickerInterface.setDate(date);

    }

    public interface FragmentDatePickerInterface
    {
        public Calendar getCalendar();
        public void setDate(String date);
    }
}