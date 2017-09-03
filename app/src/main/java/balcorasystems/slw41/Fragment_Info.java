package balcorasystems.slw41;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;


public class Fragment_Info extends Fragment
{

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
        View rootLayoutView = inflater.inflate(R.layout.debt_income, selectionContainer, false);

//        sendDebtListener = (sendDebtToMain) getContext();        //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;
//        sendIncomeListener = (sendIncomeToMain) getContext();    //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;



        SeekBar debtSeeker = (SeekBar) rootLayoutView.findViewById(R.id.seekBar2);
        SeekBar incomeSeeker = (SeekBar) rootLayoutView.findViewById(R.id.seekBar3);
        final TextView savings = (TextView) rootLayoutView.findViewById(R.id.textView6);
        final TextView debtText = (TextView) rootLayoutView.findViewById(R.id.textView29);
        final TextView incomeText = (TextView) rootLayoutView.findViewById(R.id.textView37);
        final EditText currentPaymentBox = (EditText) rootLayoutView.findViewById(R.id.editTextPayment1);

//        incomeSeeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//
//                MainActivity.simpleIncome=(double) i*1000;
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });

        debtSeeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

//                    sendDebtListener.onRecieveDebtData(i * 1000);
                debt=i*1000;
                debtText.setText("$" + String.valueOf(i * 1000));
                StandardPayment=Calculations.simpleStandardRepaymentCalc(debt);
                currentPaymentBox.setHint("Estimated $" + String.valueOf(Calculations.simpleStandardRepaymentCalc(debt)));
                MainActivity.simpleDebt= (double) debt;
                MainActivity.currentStdPayment=StandardPayment;

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


                income=i*1000;
                incomeText.setText("$" + String.valueOf(i * 1000));
                StandardPayment=Calculations.simpleStandardRepaymentCalc(debt);
                MainActivity.simpleIncome= Double.valueOf(income);

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

        return rootLayoutView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
