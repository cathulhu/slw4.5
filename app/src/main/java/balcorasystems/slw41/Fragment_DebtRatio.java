package balcorasystems.slw41;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ScrollingView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

public class Fragment_DebtRatio extends Fragment implements SeekBar.OnSeekBarChangeListener, OnChartValueSelectedListener {

    private PieChart mChart;
//    private SeekBar mSeekBarX, mSeekBarY;
//    private TextView tvX, tvY;
    ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
    ArrayList<Double> rawEntriesData = new ArrayList<>();

    Integer data1 = 5;
    Integer data2 = 11;
    Integer data3 = 29;

    Integer FirstTouch =0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState)
    {
        View rootLayoutView = inflater.inflate(R.layout.debt_ratio, selectionContainer, false);
//        RelativeLayout targetLayout = (RelativeLayout) rootLayoutView.findViewById(R.id.target);
        mChart = (PieChart) rootLayoutView.findViewById(R.id.chart1);

        entries.add(0, new PieEntry(data1));
        entries.add(1, new PieEntry(data2));
        entries.add(2, new PieEntry(data3));

        final TextView scalerText = (TextView) rootLayoutView.findViewById(R.id.scalertext);
        final TextView datatext = (TextView) rootLayoutView.findViewById(R.id.datatext);
        final TextView selecttext = (TextView) rootLayoutView.findViewById(R.id.selectedtext);


        mChart.setLongClickable(true);
        mChart.setHapticFeedbackEnabled(true);
        mChart.setTouchEnabled(true);
        mChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartLongPressed(MotionEvent me) {
                Toast.makeText(getContext(), String.valueOf("long") , Toast.LENGTH_SHORT).show();

//                final ScaleGestureDetector pinchGestureDetector = new ScaleGestureDetector(getContext(), new PiePinchListener(getContext()));
//                mChart.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View view, MotionEvent motionEvent)
//                    {
//                        pinchGestureDetector.onTouchEvent(motionEvent);
////                        Toast.makeText(getContext(), String.valueOf(motionEvent.getX() + "   " + motionEvent.getY()) , Toast.LENGTH_SHORT).show();
//                        return true;
//                    }
//                });
            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {
                Toast.makeText(getContext(), String.valueOf("double tap") , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChartSingleTapped(MotionEvent me)
            {


                if (mChart.getHighlightByTouchPoint(me.getX(), me.getY()) != null)
                {
                    Highlight thisHighlight = mChart.getHighlightByTouchPoint(me.getX(), me.getY());
                    Integer dataCoord= (int) thisHighlight.getX();

                    PieEntry oldEntry = entries.get(dataCoord);
                    float oldData = oldEntry.getValue();

                    selecttext.setText("Selected #" + mChart.getHighlightByTouchPoint(me.getX(), me.getY()));
                    scalerText.setText("No Scaler");
                    datatext.setText("data= " + oldData);
                }


            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
                Toast.makeText(getContext(), String.valueOf("fling") , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
                Toast.makeText(getContext(), String.valueOf("scale") , Toast.LENGTH_SHORT).show();
                mChart.setScaleX(scaleX);
                mChart.setScaleY(scaleY);
            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {
                Toast.makeText(getContext(), String.valueOf("translate") , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChartPinch(MotionEvent me, float x, float y, float linearDistance, Highlight selected) {
//                Toast.makeText(getContext(), String.valueOf("pinch x " + x + " " + y + " " + "distance" + linearDistance), Toast.LENGTH_SHORT).show();


                if (selected != null)
                {

                    Integer dataCoord= (int) selected.getX();
                    Highlight thisHighlight = mChart.getHighlightByTouchPoint(x,y);

                    float scaler = linearDistance/250;
                    PieEntry oldEntry = entries.get(dataCoord);
                    float oldData = oldEntry.getValue();
                    Integer changedData = Integer.valueOf((int) (oldData+scaler));

                    if (changedData > 10000)
                    {
                        changedData=10000;
                    }
                    else if (changedData <2)
                    {
                        changedData=2;
                    }

                    entries.set(dataCoord, new PieEntry(changedData));

//                    Toast.makeText(getContext(), String.valueOf("Scaler" + scaler) , Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getContext(), String.valueOf("selected " + selected) , Toast.LENGTH_SHORT).show();
//
//                    selecttext.setText("Selected #" + coordinate);
                    selecttext.setText("Selected #" + mChart.getHighlightByTouchPoint(me.getX(), me.getY()));
                    scalerText.setText("Scaler: " + String.valueOf(scaler));
                    datatext.setText("data= " + changedData);
                }







//                ArrayList<PieEntry> another = new ArrayList<PieEntry>();
//                another.add(0, new PieEntry(2));
//                another.add(1, new PieEntry(2));
//                another.add(2, new PieEntry(2));
//
//                PieDataSet dataSet2 = new PieDataSet(another, "Debt Composition");
//                PieData newData = new PieData(dataSet2);
//                mChart.setData(newData);
//                Integer target = selected.getDataSetIndex();
//                Integer changedData = entries.get(target);
//                        entries.set(target, new PieEntry((66)));
//
//
//                data1=Integer.valueOf((int) (data1*(linearDistance/300)));
//                if (data1>50)
//                {
//                    data1=50;
//                }
//
//                if (data1<2)
//                {
//                    data1=2;
//                }
//                entries.set(0, new PieEntry(data1));

                mChart.notifyDataSetChanged();
                mChart.invalidate();



            }
        });


        mChart.setUsePercentValues(false);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

//        mChart.setCenterTextTypeface(mTfLight);
        mChart.setCenterText(generateCenterSpannableText());

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(1873);

        mChart.setTransparentCircleColor(9921);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(false);
        mChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);

        setData(4, 100);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

//        mSeekBarX.setOnSeekBarChangeListener(this);
//        mSeekBarY.setOnSeekBarChangeListener(this);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
//        mChart.setEntryLabelTypeface(mTfRegular);
        mChart.setEntryLabelTextSize(12f);

        return rootLayoutView;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.pie, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        switch (item.getItemId()) {
//            case R.id.actionToggleValues: {
//                for (IDataSet<?> set : mChart.getData().getDataSets())
//                    set.setDrawValues(!set.isDrawValuesEnabled());
//
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionToggleIcons: {
//                for (IDataSet<?> set : mChart.getData().getDataSets())
//                    set.setDrawIcons(!set.isDrawIconsEnabled());
//
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionToggleHole: {
//                if (mChart.isDrawHoleEnabled())
//                    mChart.setDrawHoleEnabled(false);
//                else
//                    mChart.setDrawHoleEnabled(true);
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionDrawCenter: {
//                if (mChart.isDrawCenterTextEnabled())
//                    mChart.setDrawCenterText(false);
//                else
//                    mChart.setDrawCenterText(true);
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionToggleXVals: {
//
//                mChart.setDrawEntryLabels(!mChart.isDrawEntryLabelsEnabled());
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionSave: {
//                // mChart.saveToGallery("title"+System.currentTimeMillis());
//                mChart.saveToPath("title" + System.currentTimeMillis(), "");
//                break;
//            }
//            case R.id.actionTogglePercent:
//                mChart.setUsePercentValues(!mChart.isUsePercentValuesEnabled());
//                mChart.invalidate();
//                break;
//            case R.id.animateX: {
//                mChart.animateX(1400);
//                break;
//            }
//            case R.id.animateY: {
//                mChart.animateY(1400);
//                break;
//            }
//            case R.id.animateXY: {
//                mChart.animateXY(1400, 1400);
//                break;
//            }
//            case R.id.actionToggleSpin: {
//                mChart.spin(1000, mChart.getRotationAngle(), mChart.getRotationAngle() + 360, Easing.EasingOption
//                        .EaseInCubic);
//                break;
//            }
//        }
//        return true;
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

//        tvX.setText("" + (mSeekBarX.getProgress()));
//        tvY.setText("" + (mSeekBarY.getProgress()));
//
//        setData(mSeekBarX.getProgress(), mSeekBarY.getProgress());
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void setData(int count, float range) {

        float mult = range;

//        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
//        entries.add(0, new PieEntry(data1));
//        entries.add(1, new PieEntry(data2));
//        entries.add(2, new PieEntry(data3));

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
//        for (int i = 0; i < count ; i++) {
//            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5),
//                    mParties[i % mParties.length],
//                    getResources().getDrawable(R.drawable.googleg_standard_color_18)));
//        }



        PieDataSet dataSet = new PieDataSet(entries, "Debt Composition");

//        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
//        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new DefaultValueFormatter(1));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
//        data.setValueTypeface(mTfLight);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("Loans");
//        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
//        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
//        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
//        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
//        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
//        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h)
    {

    }

    @Override
    public void onNothingSelected() {

    }
}


