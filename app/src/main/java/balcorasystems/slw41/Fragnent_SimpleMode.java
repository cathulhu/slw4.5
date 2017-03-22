package balcorasystems.slw41;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


public class Fragnent_SimpleMode extends Fragment {

    RelativeLayout selectionLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState) {

        View rootLayoutView = inflater.inflate(R.layout.fragment_questions_simple, selectionContainer, false);
//            selectionLayout = (RelativeLayout) rootLayoutView.findViewById(R.id.);    //I guess the view is inflated just fine with this.
        return inflater.inflate(R.layout.fragment_questions_simple, selectionContainer, false);
    }
}
