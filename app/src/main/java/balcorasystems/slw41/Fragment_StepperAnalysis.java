package balcorasystems.slw41;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import me.drozdzynski.library.steppers.OnCancelAction;
import me.drozdzynski.library.steppers.OnChangeStepAction;
import me.drozdzynski.library.steppers.OnFinishAction;
import me.drozdzynski.library.steppers.SteppersItem;
import me.drozdzynski.library.steppers.SteppersView;


public class Fragment_StepperAnalysis extends Fragment
{

    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState)
    {

        View rootLayoutView = inflater.inflate(R.layout.resummary_detail, selectionContainer, false);

        ArrayList<SteppersItem> steps = new ArrayList<>();

        SteppersView steppersView = (SteppersView) rootLayoutView.findViewById(R.id.steppersView);
        SteppersView.Config steppersViewConfig = new SteppersView.Config();
        steppersViewConfig.setFragmentManager(getFragmentManager());
        steppersView.setConfig(steppersViewConfig);

        Fragment_Info testFrag1 = new Fragment_Info();
        Fragment_Info testFrag2 = new Fragment_Info();
        Fragment_Info testFrag3 = new Fragment_Info();
        Fragment_Info testFrag4 = new Fragment_Info();

        SteppersItem step1 = new SteppersItem();
        SteppersItem step2 = new SteppersItem();
        SteppersItem step3 = new SteppersItem();
        SteppersItem step4 = new SteppersItem();

        step1.setLabel("Loan Health");
        step1.setSubLabel("Subtitle of step");
        step1.setFragment(testFrag1);
        step1.setPositiveButtonEnable(true);

        step2.setLabel("Monthly Payment");
        step2.setSubLabel("Subtitle of step");
        step2.setFragment(testFrag2);
        step2.setPositiveButtonEnable(true);

        step3.setLabel("Loan Forgiveness");
        step3.setSubLabel("Subtitle of step");
        step3.setFragment(testFrag3);
        step3.setPositiveButtonEnable(true);

        step4.setLabel("Tax Stuff");
        step4.setSubLabel("Subtitle of step");
        step4.setFragment(testFrag4);
        step4.setPositiveButtonEnable(false);

        steps.add(step1);
        steps.add(step2);
        steps.add(step3);
        steps.add(step4);


        steppersView.setItems(steps);
        steppersView.build();











//
//        SteppersView.Config steppersViewConfig = new SteppersView.Config();
//        steppersViewConfig.setOnFinishAction(new OnFinishAction() {
//            @Override
//            public void onFinish()
//            {
//
//            }
//        });
//
//        steppersViewConfig.setOnCancelAction(new OnCancelAction() {
//            @Override
//            public void onCancel() {
//
//            }
//        });
//
//        steppersViewConfig.setOnChangeStepAction(new OnChangeStepAction() {
//            @Override
//            public void onChangeStep(int position, SteppersItem activeStep) {
//                Toast.makeText(getActivity(), "Step changed to: " + activeStep.getLabel() + " (" + position + ")",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        steppersViewConfig.setFragmentManager(getFragmentManager());
//        ArrayList<SteppersItem> steps = new ArrayList<>();
//
//        int i = 0;
//        while (i <= 10) {
//
//            final SteppersItem item = new SteppersItem();
//            item.setLabel("Step nr " + i);
//            item.setPositiveButtonEnable(i % 2 != 0);
//
//            if(i % 2 == 0) {
//                Fragment_Info blankFragment = new Fragment_Info();
//
//
//                item.setSubLabel("Fragment: " + blankFragment.getClass().getSimpleName());
//                item.setFragment(blankFragment);
//            }
//            else
//                {
//                    Fragment_Info blankSecondFragment = new Fragment_Info();
//                item.setSubLabel("Fragment: " + blankSecondFragment.getClass().getSimpleName());
//                item.setFragment(blankSecondFragment);
//            }
//
//            item.setPositiveButtonEnable(true);
//            steps.add(item);
//            i++;
//        }
//
//
//
//        SteppersView steppersView = (SteppersView) rootLayoutView.findViewById(R.id.steppersView);
//        steppersView.setConfig(steppersViewConfig);
//        steppersView.setItems(steps);
//        steppersView.build();


        return rootLayoutView;
    }

}
