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




public class Fragment_Info extends Fragment
{

    public interface sendDebtToMain
    {
        public void onRecieveDebtData(int debt);
    }
    public sendDebtToMain sendDebtListener;


    public interface sendIncomeToMain
    {
        public void onRecieveIncomeData(int income);
    }
    public sendIncomeToMain sendIncomeListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState)
    {
        View rootLayoutView = inflater.inflate(R.layout.simple_info, selectionContainer, false);

        sendDebtListener = (sendDebtToMain) getContext();        //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;
        sendIncomeListener = (sendIncomeToMain) getContext();    //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;

        final NumberPicker incomePicker= (NumberPicker) rootLayoutView.findViewById(R.id.numberPicker2);
        final NumberPicker debtPicker = (NumberPicker) rootLayoutView.findViewById(R.id.numberPicker1);

        SeekBar debtSeeker = (SeekBar) rootLayoutView.findViewById(R.id.seekBar2);
        SeekBar incomeSeeker = (SeekBar) rootLayoutView.findViewById(R.id.seekBar3);

        final TextView debtText = (TextView) rootLayoutView.findViewById(R.id.textView29);
        final TextView incomeText = (TextView) rootLayoutView.findViewById(R.id.textView37);

        //need to reformat these arrays to refelect logarithmic increase of debt so slider doesn't need 250 or 1000 positions!!
        String[] incomes = new String[251];
        final String[] debts = new String[1001];

        for (int i = 0; i < 1001; i++)
        {
            Integer debtValue = ((i+1)*1000);
            debts[i]=debtValue.toString();
        }

        for (int i = 0; i < 251; i++)
        {
            Integer incomeValue = (i*1000);
            incomes[i]=incomeValue.toString();
        }



        debtPicker.setMinValue(1);
        debtPicker.setMaxValue(1000);
        debtPicker.setDisplayedValues(debts);
        debtPicker.setValue(32);
        debtPicker.setWrapSelectorWheel(false);

        incomePicker.setMinValue(0);
        incomePicker.setMaxValue(250);
        incomePicker.setDisplayedValues(incomes);
        incomePicker.setValue(28);
        incomePicker.setWrapSelectorWheel(false);

        debtSeeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                sendDebtListener.onRecieveDebtData(i*1000);
                debtText.setText("$" + String.valueOf(i*1000));
                debtPicker.setValue(i);
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

                sendIncomeListener.onRecieveIncomeData(i*1000);
                incomeText.setText("$" + String.valueOf(i*1000));
                incomePicker.setValue(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

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
