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
    public static Integer selection;


    public interface OnGoToDetailPlan
    {
        void toDetailPlan();
    }

    public OnGoToDetailPlan mListener;
//    public Object_Repayment targetRepayment = MainActivity.masterBorrower.debtAndRepaymentObject.repaymentPortfolio.get(selection);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState)
    {
        View rootLayoutView = inflater.inflate(R.layout.plan_detailmk2, selectionContainer, false);
        mListener = (OnGoToDetailPlan) getContext();

        final Object_Repayment targetRepayment = Object_Debt.repaymentPortfolio.get(selection);

        TextView newPayment = (TextView) rootLayoutView.findViewById(R.id.newPaymentTitle);
        newPayment.setText("New monthly payment: $" + String.valueOf(targetRepayment.monthlyPayments.get(0)));

        TextView totalSpent = (TextView) rootLayoutView.findViewById(R.id.totalRepayment);
        totalSpent.setText(String.valueOf(targetRepayment.repaymentTotal));

        TextView totalForgiven = (TextView) rootLayoutView.findViewById(R.id.totalForgiveness);
        totalForgiven.setText(String.valueOf(targetRepayment.forgivnessTotal));

        TextView averagePayment = (TextView) rootLayoutView.findViewById(R.id.averagePayment);
        averagePayment.setText(String.valueOf(targetRepayment.meanMonthlyRepayment));


        Button seePlanButton = (Button) rootLayoutView.findViewById(R.id.getButton);
        seePlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Object_Debt.selectedRepaymentPlan =selection;
                mListener.toDetailPlan();
            }
        });

        LineChart lineChart = (LineChart) rootLayoutView.findViewById(R.id.detailChart);
        ArrayList<Entry> entries = new ArrayList();

        Integer index=0;

        for (Double x: targetRepayment.monthlyPayments)
        {
            entries.add(new Entry(index, x.floatValue()));
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





        return rootLayoutView;
    }
}
