package balcorasystems.slw41;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Fragment_Tracking_Detail extends Fragment
{


    public Integer coordinate=0;
    public Date startdate = new Date();
    ArrayList<Date> dates = new ArrayList<>();
    ArrayList<String> values = new ArrayList<>();


    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState) {
        final View rootLayoutView = inflater.inflate(R.layout.tracking_month_detail, selectionContainer, false);



  final RecyclerView recyclerView = (RecyclerView) rootLayoutView.findViewById(R.id.actionRecycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new Adapter_Actions(coordinate, values, dates);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        TextView date = (TextView) rootLayoutView.findViewById(R.id.dateText);

        Calendar c = Calendar.getInstance();
        c.setTime(dates.get(0));
//        String month = new SimpleDateFormat("MMM").format(dates.get(0));
//        String year = new SimpleDateFormat("YYYY").format(dates.get(0));


        SimpleDateFormat dt1 = new SimpleDateFormat("MMM yyyy");

        String fulldate = dt1.format(dates.get(0));
        String dataPretty ="Action Items for " + fulldate;
        date.setText(dataPretty);




        return rootLayoutView;
    }
}
