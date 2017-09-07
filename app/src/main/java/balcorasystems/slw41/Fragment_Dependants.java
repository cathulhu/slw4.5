package balcorasystems.slw41;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

/**
 * Created by L on 9/6/2017.
 */

public class Fragment_Dependants extends Fragment
{
    public static interface NextAfter
    {
        public void NextAfterSelection();
    }

    public NextAfter mListener;

    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState)
    {
        final View rootLayoutView = inflater.inflate(R.layout.dependants_selector, selectionContainer, false);
        NumberPicker dependantsSpinner = (NumberPicker) rootLayoutView.findViewById(R.id.dep_selector);
        dependantsSpinner.setMaxValue(100);
        dependantsSpinner.setMinValue(0);
        dependantsSpinner.setValue(1);
        dependantsSpinner.setWrapSelectorWheel(false);

        dependantsSpinner.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                Fragment_MasterQuestionSpawner.Borrower.taxSize=i+1;
            }
        });


        return rootLayoutView;
    }

}
