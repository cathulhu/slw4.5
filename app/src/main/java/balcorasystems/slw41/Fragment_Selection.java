package balcorasystems.slw41;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;


public class Fragment_Selection extends Fragment

{

    public interface OnNavigateAwayListener
    {
        public void navigateToChoice(Integer choice);
    }

    public OnNavigateAwayListener mListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState)
    {
        View rootLayoutView = inflater.inflate(R.layout.selection, selectionContainer, false);
        mListener = (OnNavigateAwayListener) getContext();       //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;

        CardView tracking = (CardView) rootLayoutView.findViewById(R.id.trackingCard);

        Button simpleLaunchButton;
        Button advancedLaunchButton;

        simpleLaunchButton = (Button) rootLayoutView.findViewById(R.id.simpleButton);
        advancedLaunchButton = (Button) rootLayoutView.findViewById(R.id.advancedButton);

        simpleLaunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mListener.navigateToChoice(0);
            }
        });

        advancedLaunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mListener.navigateToChoice(1);
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

