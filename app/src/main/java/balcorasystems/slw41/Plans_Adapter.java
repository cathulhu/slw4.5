package balcorasystems.slw41;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class Plans_Adapter extends RecyclerView.Adapter<Plans_Adapter.ViewHolder>
{

    public interface OnNavigateToDetail
    {
        public void recyclerToDetail();
    }
    public OnNavigateToDetail mListener;

    private ArrayList<String> adapterPlans; //probably not keeping this, I can come up with a better way to get the number of plans returned, maybe just cap it at 3 or do something else.
//    public static ArrayList<String> monthlyPayments;
//    public static ArrayList<String> timeLength;
//    public static ArrayList<ArrayList> uberPayments;
//    public static ArrayList<Double> forgivnessValues;
//    public ArrayList<Double> totals;

    public Plans_Adapter(ArrayList<String> passedPlans)
    {
        this.adapterPlans = passedPlans;

//        this.forgivnessValues=MainActivity.simpleForgiveness;
//
//
        Calculations calculator = new Calculations(MainActivity.masterBorrower);
        calculator.StandardRepayCalc();
//
//        totals = new ArrayList<>();
//        uberPayments = new ArrayList<>();
//
//        Double stdPayment = Calculations.simpleStandardRepaymentCalc(MainActivity.simpleDebt);
//        ArrayList<Double> stdPayments = new ArrayList<>();
//        for (int i = 0; i < 120; i++)
//        {
//            stdPayments.add(stdPayment);
//        }
//        uberPayments.add(stdPayments);
//
//
//        ArrayList<Double> ibrPayments = new ArrayList<>();
//        ibrPayments = Calculator.standardIBRCalc();
//        uberPayments.add(ibrPayments);
//
//        ArrayList<Double> lowestMonthlyPayments = new ArrayList<>();
//        lowestMonthlyPayments = Calculator.opportunisticIBRCalc();
//        uberPayments.add(lowestMonthlyPayments);


//        Double runningTotalStd = 0.0;
//        for (Double x: stdPayments)
//        {
//            runningTotalStd += x;
//        }
//
//        Double runningIbrTotal = 0.0;
//        for (Double x: ibrPayments)
//        {
//            runningIbrTotal += x;
//        }
//
//        Double runningLowestTotal = 0.0;
//        for (Double x: lowestMonthlyPayments)
//        {
//            runningLowestTotal += x;
//        }

//        totals.add(runningTotalStd);
//        totals.add(runningIbrTotal);
//        totals.add(runningLowestTotal);

        //perhaps I should add in some code that will take everything and turn it into an object which I can pass to the detail screen containing all the payments, all the specs and calculated stuff
    }


    @Override
    public Plans_Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_plans_row, viewGroup, false);
        final ViewHolder genericViewholder = new ViewHolder(view);
        mListener = (OnNavigateToDetail) view.getContext();       //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;




        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Number  " + genericViewholder.getAdapterPosition(), Toast.LENGTH_SHORT).show();



//                MainActivity.masterUberPayments=uberPayments;
//                MainActivity.detailID=genericViewholder.getAdapterPosition();
//                mListener.recyclerToDetail();
            }
        });

        return genericViewholder;
    }

    @Override
    public void onBindViewHolder(Plans_Adapter.ViewHolder viewHolder, int i)
    {
        //this is behaviour that happens every time an element is added to the recycler_plans view I think


        // creating list of entry<br />
//        ArrayList<Entry> entries = new ArrayList();
//
//        Integer index=0;
//
//            ArrayList<Double> individualContents = new ArrayList<>();
//            individualContents = uberPayments.get(i);
//
//            for (Double y: individualContents)
//            {
//                entries.add(new Entry(index, y.floatValue()));
//                index++;
//            }
//
//
//
//        LineDataSet dataset = new LineDataSet(entries, "Monthly Repayment");
//        dataset.setDrawCircleHole(false);
//        dataset.setDrawValues(false);
//        dataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//        dataset.setCircleColor(0);
//        dataset.setDrawFilled(true);
//
//        LineData data = new LineData(dataset);
//        data.setDrawValues(false);
//        dataset.setMode(LineDataSet.Mode.STEPPED);
//
//        viewHolder.lineChart.setDrawGridBackground(false);
//        viewHolder.lineChart.setDrawBorders(false);
//        viewHolder.lineChart.setData(data);
//        viewHolder.lineChart.animateX(100);
//        viewHolder.lineChart.setDrawGridBackground(false);
//        viewHolder.lineChart.setGridBackgroundColor(0);
//        viewHolder.lineChart.getAxisLeft().setDrawGridLines(false);
//        viewHolder.lineChart.getXAxis().setDrawGridLines(false);
//        viewHolder.lineChart.getAxisRight().setDrawGridLines(false);
//        viewHolder.lineChart.setScaleXEnabled(false);
//
//        viewHolder.rowItem.setText(adapterPlans.get(i));
//        viewHolder.monthlyPayment.setText(String.valueOf(uberPayments.get(i).get(0)));
//        viewHolder.totalTime.setText(String.valueOf(uberPayments.get(i).size()));
//        viewHolder.totalRepayment.setText(String.valueOf(totals.get(i)));
//        viewHolder.forgiveness.setText(String.valueOf(forgivnessValues.get(i)));

    }


    @Override
    public int getItemCount()
    {
        return adapterPlans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView rowItem;
        private TextView monthlyPayment;
        private TextView totalRepayment;
        private TextView totalTime;
        private TextView forgiveness;
        LineChart lineChart;


        public ViewHolder(View view)
        {
            super(view);

            monthlyPayment = (TextView) view.findViewById(R.id.monthlyValue);
            totalRepayment = (TextView) view.findViewById(R.id.totalView);
            totalTime = (TextView) view.findViewById(R.id.timeView);
            rowItem = (TextView) view.findViewById(R.id.rowItem);
            lineChart = (LineChart) view.findViewById(R.id.previewChart);
            forgiveness = (TextView) view.findViewById(R.id.forgivnessValue);
        }
    }
}