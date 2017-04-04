package balcorasystems.slw41;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.startapp.android.publish.adsCommon.StartAppAd;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;


public class Fragment_Results extends Fragment
{

    String reccomendedPlanName = "Income Based Repayment";

    //using simple tight couplings for now, can switch to bundle or data on disk, something more elegant later
    public static Integer income = Main2Activity.simpleIncome;
    public static Integer debt = Main2Activity.simpleDebt;
    double defaultMonthlyInterestRate = (0.0466 / 12);

    public Double simpleStandardRepaymentCalc()
    {
        double repaymentTermMonhts = 120;

        double paymentCalcNumerator = defaultMonthlyInterestRate * debt;
        double paymentCalcDenominator = 1 - Math.pow(1 + defaultMonthlyInterestRate, -repaymentTermMonhts);
        double fixedPayment = paymentCalcNumerator / paymentCalcDenominator;

        Main2Activity.currentStdPayment=fixedPayment;

        return fixedPayment;
    }

    public Double simpleStandardRepaymentCalc(double passedDebt)
    {
        double repaymentTermMonhts = 120;

        double paymentCalcNumerator = defaultMonthlyInterestRate * passedDebt;
        double paymentCalcDenominator = 1 - Math.pow(1 + defaultMonthlyInterestRate, -repaymentTermMonhts);
        double fixedPayment = paymentCalcNumerator / paymentCalcDenominator;

        Main2Activity.currentStdPayment=fixedPayment;

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
        double forgiveness = 0;
        double totalUnpaidInterest = 0;
        double totalAccruedInterest=0;
        double financialHardshipLine = povertyLevelsingle48states2017 * 1.5;
        double discretionaryIncome = runningIncome - financialHardshipLine;
        double ibrPayment = (discretionaryIncome * 0.15) / 12;
        double standardPayment = simpleStandardRepaymentCalc(runningDebt);


        while (runningDebt > 0 && repaymentYear < 25)     //built in edge case detection if IBR gets more expensive than standard
        {
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
//                standardPayment = simpleStandardRepaymentCalc(runningDebt);
                //disabling the standard payment recalc for now since this gives misleading results without differentiating between switch to standard from IBR and total spent doing only standard

                if (repaymentYear < 3 && (standardPayment > ibrPayment))  //ibr doesn't capitalize interest for first 36 months
                {
                    monthlyAccruedInterest=0;
                }

                totalUnpaidInterest += monthlyAccruedInterest - ibrPayment;


                if (ibrPayment > standardPayment)
                {
                    ibrPayment=standardPayment;
                }

                runningDebt += monthlyAccruedInterest - ibrPayment;

                if (totalUnpaidInterest <= 0)
                {
                    totalUnpaidInterest=0;
                }

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

        //gonna disable this for now, since this is actually pretty advanced switching to standard vs riding out full IBR conditions.

//        if (ibrPayment > standardPayment)
//        {
//            standardPayment = simpleStandardRepaymentCalc(runningDebt);
//
//            while (runningDebt > 0)   //edge case behaviour to finish off repayment over 10 more years
//            {
//                runningDebt -= standardPayment;
//                totalSpent += standardPayment;
//                payments.add(standardPayment);
//            }
//        }

        for (Double payment: payments)
        {
            totalSpent+=payment;
        }

        forgiveness=runningDebt;

        Main2Activity.totalStdSpent=standardPayment*120;
        Main2Activity.totalIdrSpent=totalSpent;
        Main2Activity.newMonthlyPayment=payments.get(0);

        return payments.get(0);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState)
    {
        View rootLayoutView = inflater.inflate(R.layout.simple_results, selectionContainer, false);

//        mListener = (goToSummaryListener) getContext();       //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;


        TextView newPaymentParagraph= (TextView) rootLayoutView.findViewById(R.id.textView12);
        TextView savingsParagraph= (TextView) rootLayoutView.findViewById(R.id.textView13);
        TextView topSavingsTitle = (TextView) rootLayoutView.findViewById(R.id.topSavings);

//        Double IBRPayment=0.0;
//        Double Savings=0.0;
//        Double StandardPayment=0.0;

        Double IBRPayment = simpleIBRRepaymentCalc();
        Double Savings = simpleStandardRepaymentCalc()-simpleIBRRepaymentCalc();
        Double StandardPayment = simpleStandardRepaymentCalc();

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(0);

        topSavingsTitle.setText("$" + String.valueOf(nf.format(Savings)));
        String paragraph1 = "If you aren't already on an Income Driven repayment plan then you can probably lower your monthly payments to around $" + nf.format(IBRPayment) + ".";
        String paragraph2 = "If you're on the standard repayment plan then you're probably paying around $" + nf.format(StandardPayment) + " a month now. So By switching to the 'Income Based Repayment' plan you'll save about $" + nf.format(Savings) + " a month.";

        newPaymentParagraph.setText(paragraph1);
        savingsParagraph.setText(paragraph2);




//        chart = (ColumnChartView) rootLayoutView.findViewById(R.id.chart);
//        chart.setOnValueTouchListener(new ValueTouchListener());
//        generateData();

        return rootLayoutView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser)
        {

            Double IBRPayment = simpleIBRRepaymentCalc();
            Main2Activity.newMonthlyPayment=IBRPayment;
            Double Savings = simpleStandardRepaymentCalc()-simpleIBRRepaymentCalc();
            Main2Activity.projectedMonthlySavings=Savings;
            Double StandardPayment = simpleStandardRepaymentCalc();
            Main2Activity.currentStdPayment=StandardPayment;

            TextView newPaymentParagraph= (TextView) getView().findViewById(R.id.textView12);
            TextView savingsParagraph= (TextView) getView().findViewById(R.id.textView13);
            TextView topSavingsTitle = (TextView) getView().findViewById(R.id.topSavings);

            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(0);

            topSavingsTitle.setText("$" + String.valueOf(nf.format(Savings)));
            String paragraph1 = "If you aren't already on an Income Driven repayment plan then you can probably lower your monthly payments to around $" + nf.format(IBRPayment) + ".";
            String paragraph2 = "If you're on the standard repayment plan then you're probably paying around $" + nf.format(StandardPayment) + " a month now. So By switching to the 'Income Based Repayment' plan you'll save about $" + nf.format(Savings) + " a month.";

            newPaymentParagraph.setText(paragraph1);
            savingsParagraph.setText(paragraph2);

        }
        else
        {

        }

    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

    }

    @Override
    public void onResume()
    {

        super.onResume();

    }

}