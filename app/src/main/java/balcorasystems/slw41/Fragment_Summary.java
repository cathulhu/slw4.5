package balcorasystems.slw41;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.Shape;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.qap.ctimelineview.TimelineRow;
import org.qap.ctimelineview.TimelineViewAdapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Fragment_Summary extends Fragment
{

    public interface returnToMain
    {
        void loadSelection();
    }

    public returnToMain mListener;




//        final List<Long> loanServicersNumbers = new ArrayList<Long>();
//        loanServicersNumbers.add(18884864722L);
//        loanServicersNumbers.add(18002364300L);
//        loanServicersNumbers.add(18007221300L);
//        loanServicersNumbers.add(18006992908L);
//        loanServicersNumbers.add(18888664352L);
//        loanServicersNumbers.add(18553376884L);
//        loanServicersNumbers.add(18006631662L);
//        loanServicersNumbers.add(18885560022L);
//        loanServicersNumbers.add(18662649762L);
//        loanServicersNumbers.add(18006213115L);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState) {
        View rootLayoutView = inflater.inflate(R.layout.summary_timeline, selectionContainer, false);


//        ArrayList<TimelineRow> timelineRowsList = new ArrayList<>();

        TimelineRow myRow = new TimelineRow(0);
        TimelineRow myRow1 = new TimelineRow(1);
        TimelineRow myRow2 = new TimelineRow(2);
        TimelineRow myRow3 = new TimelineRow(3);
        TimelineRow myRow4 = new TimelineRow(4);
        TimelineRow myRowA = new TimelineRow(5);
        TimelineRow myRow5 = new TimelineRow(6);

        myRow.setTitle("Loan Health");
        myRow.setDescription("Steps to get (and keep) your loan into good standing");
        myRow.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.a1));
        myRow.setBackgroundColor(Color.GREEN);
        myRow.setImageSize(20);
        myRow.setBackgroundSize(40);
        myRow.setBellowLineColor(Color.LTGRAY);
        myRow.setBellowLineSize(3);

        myRow1.setDescription("Enter loan rehabilitation/Keep paying/Start Paying");
        myRow1.setBackgroundColor(Color.GRAY);
        myRow1.setBackgroundSize(15);
        myRow1.setBellowLineColor(Color.LTGRAY);
        myRow1.setBellowLineSize(3);

        myRow2.setDescription("Call and ask for loan consolidation, consolidating your existing loans into a single direct consolidation loan");
        myRow2.setBackgroundColor(Color.GRAY);
        myRow2.setBackgroundSize(15);
        myRow2.setBellowLineColor(Color.LTGRAY);
        myRow2.setBellowLineSize(3);

        myRow3.setDescription("make 5 payments of $52 a month");
        myRow3.setBackgroundColor(Color.GRAY);
        myRow3.setBackgroundSize(15);
        myRow3.setBellowLineColor(Color.LTGRAY);
        myRow3.setBellowLineSize(3);

        myRow5.setDescription("After making X payments and returning to good standing call and signup for the XXX repayment program");
        myRow5.setBackgroundColor(Color.GRAY);
        myRow5.setBackgroundSize(15);
        myRow5.setBellowLineColor(Color.LTGRAY);
        myRow5.setBellowLineSize(3);

        myRow4.setTitle("Repayment");
        myRow4.setDescription("paying the bulk of loans");
        myRow4.setBackgroundColor(Color.GRAY);
        myRow4.setBackgroundSize(40);
        myRow4.setBackgroundColor(Color.GREEN);
        myRow4.setImageSize(20);
        myRow4.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.a7));
        myRow4.setBellowLineColor(Color.LTGRAY);
        myRow4.setBellowLineSize(3);


        Button select = (Button) rootLayoutView.findViewById(R.id.selectButton);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener = (returnToMain) getContext();
                mListener.loadSelection();

            }
        });



// To set row Below Line Color (optional)





// Add the new row to the list
//        timelineRowsList.add(myRow);
//        timelineRowsList.add(myRow1);
//        timelineRowsList.add(myRow2);
//        timelineRowsList.add(myRow3);
//        timelineRowsList.add(myRow4);
//        timelineRowsList.add(myRow5);
//        timelineRowsList.add(myRowA);

// Create the Timeline Adapter
//        ArrayAdapter<TimelineRow> myAdapter = new TimelineViewAdapter(getContext(), 0, timelineRowsList,
//                //if true, list will be sorted by date
//                false);

// Get the ListView and Bind it with the Timeline Adapter
//        ListView myListView = (ListView) rootLayoutView.findViewById(R.id.timeline_listView);
//        myListView.setAdapter(myAdapter);



        return rootLayoutView;
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//
//
//    }

}
