package balcorasystems.slw41;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static android.R.attr.entries;


public class Fragment_Tracking extends Fragment
{
    LineChart trackingChart;
    ViewPager trackingPager;
    PagerAdapter mPagerAdapter;
    public ArrayList<Entry> repaymentEntries = new ArrayList();
    boolean datapopulated =false;   //curently no safety on trying to launch this thing without propper data in objects
    boolean chartMovement=false;

    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState)
    {
        final View rootLayoutView = inflater.inflate(R.layout.tracking, selectionContainer, false);

        trackingChart = (LineChart) rootLayoutView.findViewById(R.id.trackingChart);
        trackingPager = (ViewPager) rootLayoutView.findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        trackingPager.setAdapter(mPagerAdapter);

        Object_Repayment targetRepayment = Object_Debt.repaymentPortfolio.get(Object_Debt.selectedRepaymentPlan);
        Integer index=0;

        for (Double x: targetRepayment.monthlyPayments)
        {
            repaymentEntries.add(new Entry(index, x.floatValue()));
            index++;
        }


        LineDataSet dataset = new LineDataSet(repaymentEntries, "Monthly Repayment");
        dataset.setDrawCircleHole(false);
        dataset.setDrawValues(false);
//        dataset.setMode(LineDataSet.Mode.STEPPED);
        dataset.setCircleColor(0);
        dataset.setDrawFilled(true);

        LineData data = new LineData(dataset);
        data.setDrawValues(false);

        trackingChart.setDrawGridBackground(false);
        trackingChart.setDrawBorders(false);
        trackingChart.setData(data);
        trackingChart.animateX(100);
        trackingChart.setDrawGridBackground(false);
        trackingChart.setGridBackgroundColor(0);
        trackingChart.getAxisLeft().setDrawGridLines(false);
        trackingChart.getXAxis().setDrawGridLines(false);
        trackingChart.getAxisRight().setDrawGridLines(false);
        trackingChart.setScaleXEnabled(false);
        trackingChart.highlightValue(0, 0);
        trackingChart.setGridBackgroundColor(0);
        dataset.setDrawHorizontalHighlightIndicator(false);
        dataset.setHighLightColor(Color.BLACK);

        trackingChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h)
            {
                //check to make sure chart doesn't get stuck in infinite loop moving the chart and then moving the viewpager, which then moves the chart, which then moves the viewpager...
                if (chartMovement==false)
                {
                    trackingPager.setCurrentItem(Math.round(e.getX()), true);
                }

            }

            @Override
            public void onNothingSelected() {

            }
        });

        trackingPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

                //check to make sure chart doesn't get stuck in infinite loop moving the chart and then moving the viewpager, which then moves the chart, which then moves the viewpager...
                chartMovement=true;
                trackingChart.highlightValue( (float) position, 0);
                chartMovement=false;

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        return rootLayoutView;
        }


    }

    class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            Object_Repayment targetRepayment = Object_Debt.repaymentPortfolio.get(Object_Debt.selectedRepaymentPlan);
            Fragment_Tracking_Detail instanceOfTracker = new Fragment_Tracking_Detail();
            instanceOfTracker.startdate=targetRepayment.repaymentStartDate;
            instanceOfTracker.dates=targetRepayment.repaymentActions.masterDates.get(position);
            instanceOfTracker.values=targetRepayment.repaymentActions.masterValues.get(position);
            instanceOfTracker.coordinate=position;

            return instanceOfTracker;

        }

        @Override
        public int getCount() {
            Object_Repayment targetRepayment = Object_Debt.repaymentPortfolio.get(Object_Debt.selectedRepaymentPlan);
            return targetRepayment.monthlyPayments.size();
        }
    }

