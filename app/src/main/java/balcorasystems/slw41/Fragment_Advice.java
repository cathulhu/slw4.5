package balcorasystems.slw41;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.ColumnChartView;

import static android.icu.lang.UProperty.INT_START;


public class Fragment_Advice extends Fragment
{

    String reccomendedPlanName = "Income Based Repayment";

    public interface goToSummaryListener
    {
        public void navigateToSummary(Double stdPayment, Double newPayment, String plan);
    }

    public goToSummaryListener mListener;


//    private static final int DEFAULT_DATA = 0;
//    private static final int SUBCOLUMNS_DATA = 1;
//    private static final int STACKED_DATA = 2;
//    private static final int NEGATIVE_SUBCOLUMNS_DATA = 3;
//    private static final int NEGATIVE_STACKED_DATA = 4;
//
//    private ColumnChartView chart;
//    private ColumnChartData data;
//    private boolean hasAxes = true;
//    private boolean hasAxesNames = true;
//    private boolean hasLabels = false;
//    private boolean hasLabelForSelected = false;
//    private int dataType = DEFAULT_DATA;

    //using simple tight couplings for now, can switch to bundle or data on disk, something more elegant later
    Integer income = MainActivity.simpleIncome;
    Integer debt = MainActivity.simpleDebt;
    double defaultMonthlyInterestRate = (0.0466 / 12);

    public Double simpleStandardRepaymentCalc()
    {
        double repaymentTermMonhts = 120;

        double paymentCalcNumerator = defaultMonthlyInterestRate * debt;
        double paymentCalcDenominator = 1 - Math.pow(1 + defaultMonthlyInterestRate, -repaymentTermMonhts);
        double fixedPayment = paymentCalcNumerator / paymentCalcDenominator;

        return fixedPayment;
    }

    public Double simpleStandardRepaymentCalc(double passedDebt)
    {
        double repaymentTermMonhts = 120;

        double paymentCalcNumerator = defaultMonthlyInterestRate * passedDebt;
        double paymentCalcDenominator = 1 - Math.pow(1 + defaultMonthlyInterestRate, -repaymentTermMonhts);
        double fixedPayment = paymentCalcNumerator / paymentCalcDenominator;

        return fixedPayment;
    }

    public Double simpleIBRRepaymentCalc()
    {

        ArrayList<Double> payments = new ArrayList<>();
        int povertyLevelsingle48states2017 = 11880;
        double annualRaise = 1.015;         //assuming 1.5% per year income, average is 2.5 with most unlucky getting 1%, so a conservative estimate.
        double annualInflation = 1.02;      //assuming 2% inflation per year, historical average is higher but in recent years it's been about 1.5% so this should be safe.
                                            //not yet sure if I'm going to include the complexity of inflation and raises in the quick IBR calculation.
        int repaymentYear = 0;
        int repaymentYearLimit = 20;
        double runningIncome = income;
        double runningDebt = debt;
        double runningInterest = 0;
        double totalSpent = 0;
        double totalUnpaidInterest = 0;
        double totalAccruedInterest=0;
        double financialHardshipLine = povertyLevelsingle48states2017 * 1.5;
        double discretionaryIncome = runningIncome - financialHardshipLine;
        double ibrPayment = (discretionaryIncome * 0.15) / 12;
        double standardPayment = simpleStandardRepaymentCalc(runningDebt);


        while (runningDebt > 0 && repaymentYear < 20 &&(ibrPayment < standardPayment) )     //built in edge case detection if IBR gets more expensive than standard
        {
            financialHardshipLine = povertyLevelsingle48states2017 * 1.5;
            discretionaryIncome = runningIncome - financialHardshipLine;

            for (int m=0; m < 12; m++)
            {
                ibrPayment = (discretionaryIncome * 0.15) / 12;
                ibrPayment = Math.floor(ibrPayment * 100) / 100;

                if (ibrPayment < 0)
                {
                    ibrPayment = 0;
                }

                double monthlyAccruedInterest = defaultMonthlyInterestRate*runningDebt;
                standardPayment = simpleStandardRepaymentCalc(runningDebt);

                if (monthlyAccruedInterest > ibrPayment)
                {
                    if (repaymentYear < 3 && (standardPayment > ibrPayment))  //ibr doesn't capitalize interest for first 36 months
                    {
                        monthlyAccruedInterest=ibrPayment;
                    }

                    totalUnpaidInterest += monthlyAccruedInterest - ibrPayment;
                }

                if (ibrPayment > standardPayment)
                {
                    ibrPayment=standardPayment;
                }

                runningDebt += monthlyAccruedInterest;
                totalAccruedInterest += monthlyAccruedInterest - ibrPayment;

                if (totalAccruedInterest < 0)
                {
                    totalAccruedInterest =0;
                }

                runningDebt -= ibrPayment;
                totalSpent += ibrPayment;
                totalSpent += ibrPayment;
                payments.add(ibrPayment);
            }

            repaymentYear++;
        }

        while (runningDebt > 0 && (ibrPayment > standardPayment))   //edge case behaviour to finish off repayment over 10 more years
        {
            standardPayment = simpleStandardRepaymentCalc(runningDebt);
            runningDebt -= standardPayment;
            totalSpent += standardPayment;
        }

        return payments.get(0);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState)
    {
        View rootLayoutView = inflater.inflate(R.layout.simple_advice, selectionContainer, false);

        mListener = (goToSummaryListener) getContext();       //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;


        Button goSummaryButton;
        goSummaryButton = (Button) rootLayoutView.findViewById(R.id.button2);

        TextView newPaymentParagraph;
        TextView savingsParagraph;

        newPaymentParagraph = (TextView) rootLayoutView.findViewById(R.id.textView12);
        savingsParagraph = (TextView) rootLayoutView.findViewById(R.id.textView13);

        Double formattedIBRPayment = simpleIBRRepaymentCalc();
        Double formattedSavings = simpleStandardRepaymentCalc()-simpleIBRRepaymentCalc();
        Double formattedStandarPayment = simpleStandardRepaymentCalc();

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);

        String paragraph1 = "If you aren't already on an Income Driven repayment plan then you can probably lower your monthly payments to around $" + nf.format(formattedIBRPayment);
        String paragraph2 = "If you're on the standard repayment plan then you're probably paying around $" + nf.format(formattedStandarPayment) + " a month now. So By switching to the 'Income Based Repayment' plan you'll save about $" + nf.format(formattedSavings) + " a month.";

        newPaymentParagraph.setText(paragraph1);
        savingsParagraph.setText(paragraph2);

        goSummaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.navigateToSummary(simpleStandardRepaymentCalc(), simpleIBRRepaymentCalc(), reccomendedPlanName);
            }
        });


//        chart = (ColumnChartView) rootLayoutView.findViewById(R.id.chart);
//        chart.setOnValueTouchListener(new ValueTouchListener());
//        generateData();

        return rootLayoutView;
    }




//    private void reset() {
//        hasAxes = true;
//        hasAxesNames = true;
//        hasLabels = false;
//        hasLabelForSelected = false;
//        dataType = DEFAULT_DATA;
//        chart.setValueSelectionEnabled(hasLabelForSelected);
//
//    }
//
//    private void generateDefaultData() {
//        int numSubcolumns = 2;
//        int numColumns = 8;
//        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
//        List<Column> columns = new ArrayList<Column>();
//        List<SubcolumnValue> values;
//        for (int i = 0; i < numColumns; ++i) {
//
//            values = new ArrayList<SubcolumnValue>();
//            for (int j = 0; j < numSubcolumns; ++j) {
//                values.add(new SubcolumnValue((float) Math.random() * 50f + 5, ChartUtils.pickColor()));
//            }
//
//            Column column = new Column(values);
//            column.setHasLabels(hasLabels);
//            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
//            columns.add(column);
//        }
//
//        data = new ColumnChartData(columns);
//
//        if (hasAxes) {
//            Axis axisX = new Axis();
//            Axis axisY = new Axis().setHasLines(true);
//            if (hasAxesNames) {
//                axisX.setName("Axis X");
//                axisY.setName("Axis Y");
//            }
//            data.setAxisXBottom(axisX);
//            data.setAxisYLeft(axisY);
//        } else {
//            data.setAxisXBottom(null);
//            data.setAxisYLeft(null);
//        }
//
//        chart.setColumnChartData(data);
//
//    }
//
//    /**
//     * Generates columns with subcolumns, columns have larger separation than subcolumns.
//     */
//    private void generateSubcolumnsData() {
//        int numSubcolumns = 4;
//        int numColumns = 4;
//        // Column can have many subcolumns, here I use 4 subcolumn in each of 8 columns.
//        List<Column> columns = new ArrayList<Column>();
//        List<SubcolumnValue> values;
//        for (int i = 0; i < numColumns; ++i) {
//
//            values = new ArrayList<SubcolumnValue>();
//            for (int j = 0; j < numSubcolumns; ++j) {
//                values.add(new SubcolumnValue((float) Math.random() * 50f + 5, ChartUtils.pickColor()));
//            }
//
//            Column column = new Column(values);
//            column.setHasLabels(hasLabels);
//            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
//            columns.add(column);
//        }
//
//        data = new ColumnChartData(columns);
//
//        if (hasAxes) {
//            Axis axisX = new Axis();
//            Axis axisY = new Axis().setHasLines(true);
//            if (hasAxesNames) {
//                axisX.setName("Axis X");
//                axisY.setName("Axis Y");
//            }
//            data.setAxisXBottom(axisX);
//            data.setAxisYLeft(axisY);
//        } else {
//            data.setAxisXBottom(null);
//            data.setAxisYLeft(null);
//        }
//
//        chart.setColumnChartData(data);
//
//    }
//
//    /**
//     * Generates columns with stacked subcolumns.
//     */
//    private void generateStackedData() {
//        int numSubcolumns = 4;
//        int numColumns = 8;
//        // Column can have many stacked subcolumns, here I use 4 stacke subcolumn in each of 4 columns.
//        List<Column> columns = new ArrayList<Column>();
//        List<SubcolumnValue> values;
//        for (int i = 0; i < numColumns; ++i) {
//
//            values = new ArrayList<SubcolumnValue>();
//            for (int j = 0; j < numSubcolumns; ++j) {
//                values.add(new SubcolumnValue((float) Math.random() * 20f + 5, ChartUtils.pickColor()));
//            }
//
//            Column column = new Column(values);
//            column.setHasLabels(hasLabels);
//            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
//            columns.add(column);
//        }
//
//        data = new ColumnChartData(columns);
//
//        // Set stacked flag.
//        data.setStacked(true);
//
//        if (hasAxes) {
//            Axis axisX = new Axis();
//            Axis axisY = new Axis().setHasLines(true);
//            if (hasAxesNames) {
//                axisX.setName("Axis X");
//                axisY.setName("Axis Y");
//            }
//            data.setAxisXBottom(axisX);
//            data.setAxisYLeft(axisY);
//        } else {
//            data.setAxisXBottom(null);
//            data.setAxisYLeft(null);
//        }
//
//        chart.setColumnChartData(data);
//    }
//
//    private void generateNegativeSubcolumnsData() {
//
//        int numSubcolumns = 4;
//        int numColumns = 4;
//        List<Column> columns = new ArrayList<Column>();
//        List<SubcolumnValue> values;
//        for (int i = 0; i < numColumns; ++i) {
//
//            values = new ArrayList<SubcolumnValue>();
//            for (int j = 0; j < numSubcolumns; ++j) {
//                int sign = getSign();
//                values.add(new SubcolumnValue((float) Math.random() * 50f * sign + 5 * sign, ChartUtils.pickColor
//                        ()));
//            }
//
//            Column column = new Column(values);
//            column.setHasLabels(hasLabels);
//            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
//            columns.add(column);
//        }
//
//        data = new ColumnChartData(columns);
//
//        if (hasAxes) {
//            Axis axisX = new Axis();
//            Axis axisY = new Axis().setHasLines(true);
//            if (hasAxesNames) {
//                axisX.setName("Axis X");
//                axisY.setName("Axis Y");
//            }
//            data.setAxisXBottom(axisX);
//            data.setAxisYLeft(axisY);
//        } else {
//            data.setAxisXBottom(null);
//            data.setAxisYLeft(null);
//        }
//
//        chart.setColumnChartData(data);
//    }
//
//    private void generateNegativeStackedData() {
//
//        int numSubcolumns = 4;
//        int numColumns = 8;
//        // Column can have many stacked subcolumns, here I use 4 stacke subcolumn in each of 4 columns.
//        List<Column> columns = new ArrayList<Column>();
//        List<SubcolumnValue> values;
//        for (int i = 0; i < numColumns; ++i) {
//
//            values = new ArrayList<SubcolumnValue>();
//            for (int j = 0; j < numSubcolumns; ++j) {
//                int sign = getSign();
//                values.add(new SubcolumnValue((float) Math.random() * 20f * sign + 5 * sign, ChartUtils.pickColor()));
//            }
//
//            Column column = new Column(values);
//            column.setHasLabels(hasLabels);
//            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
//            columns.add(column);
//        }
//
//        data = new ColumnChartData(columns);
//
//        // Set stacked flag.
//        data.setStacked(true);
//
//        if (hasAxes) {
//            Axis axisX = new Axis();
//            Axis axisY = new Axis().setHasLines(true);
//            if (hasAxesNames) {
//                axisX.setName("Axis X");
//                axisY.setName("Axis Y");
//            }
//            data.setAxisXBottom(axisX);
//            data.setAxisYLeft(axisY);
//        } else {
//            data.setAxisXBottom(null);
//            data.setAxisYLeft(null);
//        }
//
//        chart.setColumnChartData(data);
//    }
//
//    private int getSign() {
//        int[] sign = new int[]{-1, 1};
//        return sign[Math.round((float) Math.random())];
//    }
//
//    private void generateData() {
//        switch (dataType) {
//            case DEFAULT_DATA:
//                generateDefaultData();
//                break;
//            case SUBCOLUMNS_DATA:
//                generateSubcolumnsData();
//                break;
//            case STACKED_DATA:
//                generateStackedData();
//                break;
//            case NEGATIVE_SUBCOLUMNS_DATA:
//                generateNegativeSubcolumnsData();
//                break;
//            case NEGATIVE_STACKED_DATA:
//                generateNegativeStackedData();
//                break;
//            default:
//                generateDefaultData();
//                break;
//        }
//    }
//
//    private void toggleLabels() {
//        hasLabels = !hasLabels;
//
//        if (hasLabels) {
//            hasLabelForSelected = false;
//            chart.setValueSelectionEnabled(hasLabelForSelected);
//        }
//
//        generateData();
//    }
//
//    private void toggleLabelForSelected() {
//        hasLabelForSelected = !hasLabelForSelected;
//        chart.setValueSelectionEnabled(hasLabelForSelected);
//
//        if (hasLabelForSelected) {
//            hasLabels = false;
//        }
//
//        generateData();
//    }
//
//    private void toggleAxes() {
//        hasAxes = !hasAxes;
//
//        generateData();
//    }
//
//    private void toggleAxesNames() {
//        hasAxesNames = !hasAxesNames;
//
//        generateData();
//    }
//
//    /**
//     * To animate values you have to change targets values and then call {@link Chart#startDataAnimation()}
//     * method(don't confuse with View.animate()).
//     */
//    private void prepareDataAnimation() {
//        for (Column column : data.getColumns()) {
//            for (SubcolumnValue value : column.getValues()) {
//                value.setTarget((float) Math.random() * 100);
//            }
//        }
//    }
//
//    private class ValueTouchListener implements ColumnChartOnValueSelectListener {
//
//        @Override
//        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
////            Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onValueDeselected() {
//            // TODO Auto-generated method stub
//
//        }
//
//    }

}