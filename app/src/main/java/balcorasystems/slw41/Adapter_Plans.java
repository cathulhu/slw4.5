package balcorasystems.slw41;

import android.graphics.Color;
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

import static java.lang.Boolean.FALSE;

public class Adapter_Plans extends RecyclerView.Adapter<Adapter_Plans.ViewHolder>
{

    public interface OnNavigateToDetail
    {
        void recyclerToDetail(Integer passedSelection);
    }
    public OnNavigateToDetail mListener;

    private ArrayList<String> adapterPlans; //probably not keeping this, I can come up with a better way to get the number of plans returned, maybe just cap it at 3 or do something else.



    public Adapter_Plans(ArrayList<String> passedPlans)
    {
        this.adapterPlans = passedPlans;

        Logic_Calculations calculator = new Logic_Calculations(MainActivity.masterBorrower);
        calculator.StandardRepayCalc();
        Logic_Calculations.payeCalc();
        Logic_Calculations.repayeCalc();

        //add in an IBR and other plans once those calculations have been written. Eventually doing this calculation should be moved somewhere else and this adapter will just grab values.
        //this style is just repeating the calculation everytime, need to come up with something else
    }





    @Override
    public Adapter_Plans.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_plans_row, viewGroup, false);
        final ViewHolder genericViewholder = new ViewHolder(view);
        mListener = (OnNavigateToDetail) view.getContext();       //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;

        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Number  " + genericViewholder.getAdapterPosition(), Toast.LENGTH_SHORT).show();

                //can I also transmit some data on the page other then the adapter number that will give info about which was chosen?
                mListener.recyclerToDetail(genericViewholder.getAdapterPosition());
            }
        });

        return genericViewholder;
    }




    @Override
    public void onBindViewHolder(Adapter_Plans.ViewHolder viewHolder, int i)
    {
        //this is behaviour that happens every time an element is added to the recycler_plans view I think

        //populating graph
        ArrayList<Entry> entries = new ArrayList();
        Object_Repayment targetRepayment = Object_Debt.repaymentPortfolio.get(i);
        Integer index=0;

        for (Double x: targetRepayment.monthlyPayments)
        {
            entries.add(new Entry(index, x.floatValue()));
            index++;
        }

        LineDataSet dataset = new LineDataSet(entries, "Monthly Repayment");
        dataset.setDrawCircleHole(false);
        dataset.setDrawValues(false);
//        dataset.setMode(LineDataSet.Mode.STEPPED);
        dataset.setCircleColor(0);
        dataset.setDrawFilled(true);

        LineData data = new LineData(dataset);
        data.setDrawValues(false);

        viewHolder.lineChart.setDrawGridBackground(false);
        viewHolder.lineChart.setDrawBorders(false);
        viewHolder.lineChart.setData(data);
        viewHolder.lineChart.animateX(100);
        viewHolder.lineChart.setDrawGridBackground(false);
        viewHolder.lineChart.setGridBackgroundColor(0);
        viewHolder.lineChart.getAxisLeft().setDrawGridLines(false);
        viewHolder.lineChart.getXAxis().setDrawGridLines(false);
        viewHolder.lineChart.getAxisRight().setDrawGridLines(false);
        viewHolder.lineChart.setScaleXEnabled(false);
        viewHolder.lineChart.setGridBackgroundColor(Color.TRANSPARENT);
        data.setHighlightEnabled(FALSE);

        viewHolder.rowItem.setText(adapterPlans.get(i));

        viewHolder.monthlyPayment.setText("$"+ String.valueOf(targetRepayment.monthlyPayments.get(i)));
        //retrieves the first new payment, not the average or anything
        viewHolder.totalTime.setText(String.valueOf(targetRepayment.monthlyPayments.size())+" Months");
        //just calculating it here, could have this data on the object;
        viewHolder.totalRepayment.setText("$"+String.valueOf(targetRepayment.repaymentTotal));
        viewHolder.forgiveness.setText("$"+String.valueOf(targetRepayment.forgivnessTotal));
        //not yet listing average payment here
        viewHolder.owedTaxes.setText("$"+String.valueOf(targetRepayment.taxTotal));

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
        private TextView owedTaxes;
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
            owedTaxes= (TextView) view.findViewById(R.id.owedTaxesValue);
        }
    }
}