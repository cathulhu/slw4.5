package balcorasystems.slw41;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

/**
 * Created by L on 10/24/2017.
 */

public class Fragment_modeChoice extends Fragment
{
    public interface goToQuestions
    {
        void toQuestions();
    }

    public goToQuestions leaveListener;


    Boolean status=Boolean.FALSE;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.mode_choice, container, false);

        final Switch toggleModeSwitch = (Switch) rootView.findViewById(R.id.switch1);
        Button continueButton = (Button) rootView.findViewById(R.id.leave);

        toggleModeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (status==false)
                {
                    status=true;
                    FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
//                    fTransaction.setCustomAnimations(R.anim.slide_down_anim, R.anim.slide_down_anim);
                    fTransaction.replace(R.id.fragmentView, new Fragment_SLALogin(), "info");
                    fTransaction.addToBackStack(null);
                    fTransaction.commit();
                    toggleModeSwitch.setText("switch to low accuracy mode");
                }
                else
                {
                    status=false;
                    FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
//                    fTransaction.setCustomAnimations(R.anim.slide_down_anim, R.anim.slide_down_anim);
                    fTransaction.replace(R.id.fragmentView, new Fragment_Debt_Dialer(), "info");
                    fTransaction.addToBackStack(null);
                    fTransaction.commit();
                    toggleModeSwitch.setText("switch to high accuracy mode");
                }

            }
        });

        leaveListener = (goToQuestions) getContext();

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                leaveListener.toQuestions();
            }
        });

        toggleModeSwitch.performClick();

        return rootView;

    }
}
