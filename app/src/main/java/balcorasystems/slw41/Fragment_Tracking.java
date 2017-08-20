package balcorasystems.slw41;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;


public class Fragment_Tracking extends Fragment
{
    LineChart trackingChart;
    Integer pageNumber=0;
    ViewPager trackingPager;
    PagerAdapter mPagerAdapter;
    Integer chartSelection=0;
    ArrayList<Entry> entries = new ArrayList();
    boolean datapopulated =false;

    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState) {
        final View rootLayoutView = inflater.inflate(R.layout.tracking, selectionContainer, false);

        trackingChart = (LineChart) rootLayoutView.findViewById(R.id.trackingChart);

        trackingPager = (ViewPager) rootLayoutView.findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());

        trackingPager.setAdapter(mPagerAdapter);


//        //just a test of the functionality that can allow clicking on graph to scroll the viewpager
//        TextView title = (TextView) rootLayoutView.findViewById(R.id.top1);
//        title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                trackingPager.setCurrentItem(4, true);
//            }
//        });





        //chart population, very crude example

        Integer index=0;
        ArrayList<ArrayList> uberPayments = MainActivity.masterUberPayments;
        ArrayList<Double> individualContents = new ArrayList<>();
        individualContents = uberPayments.get(0);

        for (Double y: individualContents)
        {
            entries.add(new Entry(index, y.floatValue()));
            index++;
        }
        datapopulated=true;

        LineDataSet dataset = new LineDataSet(entries, "Moneyz");
        dataset.setDrawCircleHole(false);
        dataset.setDrawValues(false);
        dataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataset.setCircleColor(0);
        dataset.setDrawFilled(true);

        LineData data = new LineData(dataset);
        data.setDrawValues(false);

        trackingChart.setDrawGridBackground(false);
        trackingChart.setDrawBorders(false);
        trackingChart.setData(data);
        trackingChart.animateX(50);
        trackingChart.setDrawGridBackground(false);

        //end chart population

        trackingChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                trackingPager.setCurrentItem(Math.round(e.getX()), true);
            }

            @Override
            public void onNothingSelected() {

            }
        });






        return rootLayoutView;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment_Tracking_Detail instancedFrag = new Fragment_Tracking_Detail();
            instancedFrag.coordinate=position;
            instancedFrag.paymentValue=entries.get(Math.round(position)).getY();
            return instancedFrag;

        }

        @Override
        public int getCount() {


            return 120;
        }
    }
}
