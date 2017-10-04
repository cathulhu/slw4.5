package balcorasystems.slw41;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.TimeUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static balcorasystems.slw41.R.id.datePicker;

public class Fragment_Calendar extends Fragment
{
    public interface NextAfter
    {
        void NextAfterSelection();
    }

    public NextAfter mListener;

    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState)
    {
        final View rootLayoutView = inflater.inflate(R.layout.repayment_calendar, selectionContainer, false);

        final Calendar c = Calendar.getInstance();
        Integer year = c.get(Calendar.YEAR);
        Integer month = c.get(Calendar.MONTH);
        Integer day = c.get(Calendar.DAY_OF_MONTH);

        final DatePicker repaymentDate = (DatePicker) rootLayoutView.findViewById(datePicker);
        repaymentDate.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2)
            {
                i1++;
                String myDate = i+"/"+i1+"/"+i2;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Date date = null;
                try {
                    date = sdf.parse(myDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long millis = date.getTime();
                Fragment_MasterQuestionSpawner.Borrower.repaymentDate.setTime(millis);
            }
        });

        return rootLayoutView;
    }
}
