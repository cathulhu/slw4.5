package balcorasystems.slw41;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;


public class Fragment_Info extends Fragment {

    public static Integer income =6;
    public static Integer debt =6;
    public static double IBRPayment =0.66;
    public static double StandardPayment =0.66;
    public static double Savings =0.66;
    double defaultMonthlyInterestRate = (0.0466 / 12);

//
//    public interface sendDebtToMain {
//        public void onRecieveDebtData(int debt);
//    }
//    public sendDebtToMain sendDebtListener;
//
//
//    public interface sendIncomeToMain {
//        public void onRecieveIncomeData(int income);
//    }
//    public sendIncomeToMain sendIncomeListener;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState) {
        View rootLayoutView = inflater.inflate(R.layout.simple_infohoriz, selectionContainer, false);

//        sendDebtListener = (sendDebtToMain) getContext();        //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;
//        sendIncomeListener = (sendIncomeToMain) getContext();    //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;

        SeekBar debtSeeker = (SeekBar) rootLayoutView.findViewById(R.id.seekBar2);
        SeekBar incomeSeeker = (SeekBar) rootLayoutView.findViewById(R.id.seekBar3);
        final TextView savings = (TextView) rootLayoutView.findViewById(R.id.textView6);
        final TextView debtText = (TextView) rootLayoutView.findViewById(R.id.textView29);
        final TextView incomeText = (TextView) rootLayoutView.findViewById(R.id.textView37);

//        final String[] incomes = new String[251];
//        final String[] debts = new String[1001];
//
//        for (int i = 0; i < 1001; i++) {
//            Integer debtValue = ((i + 1) * 1000);
//            debts[i] = debtValue.toString();
//        }
//
//        for (int i = 0; i < 251; i++) {
//            Integer incomeValue = (i * 1000);
//            incomes[i] = incomeValue.toString();
//        }

        debtSeeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

//                    sendDebtListener.onRecieveDebtData(i * 1000);
                debt=i*1000;
                debtText.setText("$" + String.valueOf(i * 1000));
                StandardPayment=simpleStandardRepaymentCalc();
                IBRPayment=simpleIBRRepaymentCalc();       //im pretty sure IBR wont change based on debt but ill leave this here for the moment.
                savings.setText("$" + IBRPayment);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        incomeSeeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

//                    sendIncomeListener.onRecieveIncomeData(i * 1000);
                income=i*1000;
                incomeText.setText("$" + String.valueOf(i * 1000));
                IBRPayment=simpleIBRRepaymentCalc();

                StandardPayment=simpleStandardRepaymentCalc();

                savings.setText("$" + IBRPayment);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//        NumberFormat nf = NumberFormat.getInstance();
//        nf.setMaximumFractionDigits(0);

        savings.setText("$" + String.valueOf(IBRPayment));

        return rootLayoutView;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        if(isVisibleToUser)
        {

//            SeekBar debtSeeker = (SeekBar) getView().findViewById(R.id.seekBar2);
//            SeekBar incomeSeeker = (SeekBar) getView().findViewById(R.id.seekBar3);
//            final TextView savings = (TextView) getView().findViewById(R.id.textView6);
//            final TextView debtText = (TextView) getView().findViewById(R.id.textView29);
//            final TextView incomeText = (TextView) getView().findViewById(R.id.textView37);

            //need to reformat these arrays to refelect logarithmic increase of debt so slider doesn't need 250 or 1000 positions!!




//            String paragraph1 = "If you aren't already on an Income Driven repayment plan then you can probably lower your monthly payments to around $" + nf.format(IBRPayment) + ".";
//            String paragraph2 = "If you're on the standard repayment plan then you're probably paying around $" + nf.format(StandardPayment) + " a month now. So By switching to the 'Income Based Repayment' plan you'll save about $" + nf.format(Savings) + " a month.";


        }


        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
    }








    public Double simpleStandardRepaymentCalc() {
        double repaymentTermMonhts = 120;

        double paymentCalcNumerator = defaultMonthlyInterestRate * debt;
        double paymentCalcDenominator = 1 - Math.pow(1 + defaultMonthlyInterestRate, -repaymentTermMonhts);
        double fixedPayment = paymentCalcNumerator / paymentCalcDenominator;
        Main2Activity.currentStdPayment = fixedPayment;

        return fixedPayment;
    }

    public Double simpleStandardRepaymentCalc(double passedDebt) {
        double repaymentTermMonhts = 120;

        double paymentCalcNumerator = defaultMonthlyInterestRate * passedDebt;
        double paymentCalcDenominator = 1 - Math.pow(1 + defaultMonthlyInterestRate, -repaymentTermMonhts);
        double fixedPayment = paymentCalcNumerator / paymentCalcDenominator;

        Main2Activity.currentStdPayment = fixedPayment;

        return fixedPayment;
    }

    public Long simpleIBRRepaymentCalc() {

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
        double totalAccruedInterest = 0;
        double financialHardshipLine = povertyLevelsingle48states2017 * 1.5;
        double discretionaryIncome = runningIncome - financialHardshipLine;
        double ibrPayment = (discretionaryIncome * 0.15) / 12;
        double standardPayment = simpleStandardRepaymentCalc(runningDebt);


        while (runningDebt > 0 && repaymentYear < 25)     //built in edge case detection if IBR gets more expensive than standard
        {
            discretionaryIncome = runningIncome - financialHardshipLine;

            for (int m = 0; m < 12; m++) {
                ibrPayment = (discretionaryIncome * 0.15) / 12;
//                ibrPayment = Math.floor(ibrPayment * 100) / 100;

                if (ibrPayment < 0) {
                    ibrPayment = 0;
                }


                double monthlyAccruedInterest = defaultMonthlyInterestRate * runningDebt;
//                standardPayment = simpleStandardRepaymentCalc(runningDebt);
                //disabling the standard payment recalc for now since this gives misleading results without differentiating between switch to standard from IBR and total spent doing only standard

                if (repaymentYear < 3 && (standardPayment > ibrPayment))  //ibr doesn't capitalize interest for first 36 months
                {
                    monthlyAccruedInterest = 0;
                }

                totalUnpaidInterest += monthlyAccruedInterest - ibrPayment;


                if (ibrPayment > standardPayment) {
                    ibrPayment = standardPayment;
                }

                runningDebt += monthlyAccruedInterest - ibrPayment;

                if (totalUnpaidInterest <= 0) {
                    totalUnpaidInterest = 0;
                }

                totalAccruedInterest += monthlyAccruedInterest - ibrPayment;

                if (totalAccruedInterest < 0) {
                    totalAccruedInterest = 0;
                }


                runningDebt -= ibrPayment;
                totalSpent += ibrPayment;
                totalSpent += ibrPayment;
                payments.add(ibrPayment);
            }

            repaymentYear++;
        }


        for (Double payment : payments) {
            totalSpent += payment;
        }

        forgiveness = runningDebt;

        Main2Activity.totalStdSpent = standardPayment * 120;
        Main2Activity.totalIdrSpent = totalSpent;
        Main2Activity.newMonthlyPayment = payments.get(0);


        return Math.round(payments.get(0));

    }

}
