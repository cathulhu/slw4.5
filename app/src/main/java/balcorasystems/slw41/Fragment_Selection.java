package balcorasystems.slw41;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class Fragment_Selection extends Fragment

{

    public interface OnSwitchSelectionListener
    {
        public void onModeSelection();
    }

    public OnSwitchSelectionListener mListener;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState)
    {
        View rootLayoutView = inflater.inflate(R.layout.selection, selectionContainer, false);
        mListener = (OnSwitchSelectionListener) getContext();       //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;

        Button simpleLaunchButton;
        Button advancedLaunchButton;

        simpleLaunchButton = (Button) rootLayoutView.findViewById(R.id.simpleButton);
        advancedLaunchButton = (Button) rootLayoutView.findViewById(R.id.advancedButton);

        simpleLaunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Simple Mode Button Pressed", Toast.LENGTH_LONG).show();

                mListener.onModeSelection();
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
//        if(context instanceof OnSwitchSelectionListener) {
//            mListener = (OnSwitchSelectionListener) context;
//        } else {
//            throw new IllegalArgumentException("Containing activity must implement OnSearchListener interface");
//        }
//    }
//END WIZARDRY (for some reason the interface stuff doesn't work unless I have this






