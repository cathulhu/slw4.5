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

    private ArrayList<String> adapterPlans;

    public Plans_Adapter(ArrayList<String> passedPlans) {
        this.adapterPlans = passedPlans;
    }


    @Override
    public Plans_Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_plans_row, viewGroup, false);
        final ViewHolder genericViewholder = new ViewHolder(view);
        mListener = (OnNavigateToDetail) view.getContext();       //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;




        LineChart lineChart = (LineChart) view.findViewById(R.id.previewChart);
        // creating list of entry<br />
        ArrayList<Entry> entries = new ArrayList();

        Integer index=0;
        for (double x: MainActivity.masterLoan.payments)
        {
            entries.add(new Entry(index, (float) x));
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
        lineChart.animateX(100);
        lineChart.setDrawGridBackground(false);
        lineChart.setGridBackgroundColor(0);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.setScaleXEnabled(false);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Number  " + genericViewholder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                mListener.recyclerToDetail();
            }
        });

        return genericViewholder;
    }

    @Override
    public void onBindViewHolder(Plans_Adapter.ViewHolder viewHolder, int i) {

        //this is behaviour that happens every time an element is added to the recycler_plans view I think
        viewHolder.rowItem.setText(adapterPlans.get(i));

    }


    @Override
    public int getItemCount() {
        return adapterPlans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView rowItem;

        public ViewHolder(View view) {
            super(view);


            rowItem = (TextView) view.findViewById(R.id.rowItem);
        }
    }
}