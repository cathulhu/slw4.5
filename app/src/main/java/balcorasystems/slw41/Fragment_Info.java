package balcorasystems.slw41;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;


public class Fragment_Info extends Fragment

{

    public interface onSaveMoneyNowButton
    {
        public void onIncomeFinished(int income, int debt);
    }
    public onSaveMoneyNowButton mListener;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState)
    {
        View rootLayoutView = inflater.inflate(R.layout.simple_info, selectionContainer, false);
        mListener = (onSaveMoneyNowButton) getContext();       //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;

        final NumberPicker incomePicker;
        final NumberPicker debtPicker;
        Button doneButton;

        incomePicker = (NumberPicker) rootLayoutView.findViewById(R.id.numberPicker2);
        debtPicker = (NumberPicker) rootLayoutView.findViewById(R.id.numberPicker1);
        doneButton = (Button) rootLayoutView.findViewById(R.id.buttonDone);

        String[] incomes = new String[251];
        String[] debts = new String[1001];

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

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mListener.onIncomeFinished(incomePicker.getValue(), debtPicker.getValue());
            }
        });

        return rootLayoutView;
    }

}













// WHAT THE HELL IS THIS WIZARDRY
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        if(context instanceof onSaveMoneyNowButton) {
//            mListener = (onSaveMoneyNowButton) context;
//        } else {
//            throw new IllegalArgumentException("Containing activity must implement OnSearchListener interface");
//        }
//    }
//END WIZARDRY (for some reason the interface stuff doesn't work unless I have this






