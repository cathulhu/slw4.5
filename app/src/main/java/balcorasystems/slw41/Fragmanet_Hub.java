package balcorasystems.slw41;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.triggertrap.seekarc.SeekArc;

/**
 * Created by L on 11/2/2017.
 */

public class Fragmanet_Hub extends Fragment
{

    @Nullable
    @Override
    public android.view.View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.hub, container, false);

        SeekArc healthArc = (SeekArc) rootView.findViewById(R.id.seekArc);
        healthArc.setTouchInSide(true);

        FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
        fTransaction.replace(R.id.trackingFrame, new Fragment_Tracking());
        fTransaction.commit();


        return rootView;

    }
}
