package balcorasystems.slw41;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by L on 7/12/2017.
 */

public class Fragment_PlanDetail extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState)
    {
        View rootLayoutView = inflater.inflate(R.layout.plan_detail, selectionContainer, false);

//        final RelativeLayout StepperFragmentLayout = (RelativeLayout) rootLayoutView.findViewById(R.id.stepperFragmentArea);
        FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
        fTransaction.replace(R.id.stepperFragmentArea, new Fragment_StepperAnalysis(), "questions");
        fTransaction.addToBackStack(null);
        fTransaction.commit();





        return rootLayoutView;
    }
}
