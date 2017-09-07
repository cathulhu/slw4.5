package balcorasystems.slw41;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Comparator;

public class Fragment_PlanDetail extends Fragment
{


    public interface OnGoToDetailPlan
    {
        void toDetailPlan();
    }

    public OnGoToDetailPlan mListener;
//    ArrayList<ArrayList> uberPayments = MainActivity.masterUberPayments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState)
    {
        View rootLayoutView = inflater.inflate(R.layout.plan_detailmk2, selectionContainer, false);
        mListener = (OnGoToDetailPlan) getContext();


        Button seePlanButton = (Button) rootLayoutView.findViewById(R.id.getButton);
        seePlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.toDetailPlan();
//                MainActivity.simpleRepaymentSelection=MainActivity.detailID;
                //I forget what this is used for
            }
        });



        LineChart lineChart = (LineChart) rootLayoutView.findViewById(R.id.detailChart);
        // creating list of entry<br />
        ArrayList<Entry> entries = new ArrayList();

        Integer index=0;

        ArrayList<Double> individualContents = new ArrayList<>();
//        individualContents = uberPayments.get(MainActivity.detailID);

        for (Double y: individualContents)
        {
            entries.add(new Entry(index, y.floatValue()));
            index++;
        }



        LineDataSet dataset = new LineDataSet(entries, "Moneyz");
        dataset.setDrawCircleHole(false);
        dataset.setDrawValues(false);
        dataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataset.setCircleColor(0);
        dataset.setDrawFilled(true);

        LineData data = new LineData(dataset);
        data.setDrawValues(false);

        lineChart.setDrawGridBackground(false);
        lineChart.setDrawBorders(false);
        lineChart.setData(data);
        lineChart.animateX(50);
        lineChart.setDrawGridBackground(false);




////        final RelativeLayout StepperFragmentLayout = (RelativeLayout) rootLayoutView.findViewById(R.id.stepperFragmentArea);
//        FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
//        fTransaction.replace(R.id.stepperFragmentArea, new Fragment_StepperAnalysis(), "questions");
//        fTransaction.addToBackStack(null);
//        fTransaction.commit();


        TextView newPayment = (TextView) rootLayoutView.findViewById(R.id.newPaymentTitle);
        newPayment.setText("New monthly payment: $" + String.valueOf(individualContents.get(0)));

        Double averageMontlhy=0d;
        Integer count=0;

        for (double x: individualContents)
        {
            averageMontlhy += x;
            count++;
        }

        averageMontlhy = averageMontlhy/count;

        TextView averagePayment = (TextView) rootLayoutView.findViewById(R.id.averagePayment);
        averagePayment.setText(String.valueOf(averageMontlhy));



        return rootLayoutView;
    }
}
