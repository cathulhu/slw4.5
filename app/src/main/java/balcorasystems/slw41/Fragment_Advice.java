package balcorasystems.slw41;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

public class Fragment_Advice extends Fragment

{

//    public interface OnNavigateAwayListener
//    {
//        public void navigateToChoice();
//    }
//
//    public Fragment_Selection.OnNavigateAwayListener mListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState)
    {
        View rootLayoutView = inflater.inflate(R.layout.simple_advice, selectionContainer, false);

//        mListener = (Fragment_Selection.OnNavigateAwayListener) getContext();       //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;


//        Button advancedLaunchButton;
//
//        simpleLaunchButton = (Button) rootLayoutView.findViewById(R.id.simpleButton);

//        simpleLaunchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getContext(), "Simple Mode Button Pressed", Toast.LENGTH_LONG).show();
//
//                mListener.navigateToChoice();
//            }
//        });

        return rootLayoutView;
    }

}