package balcorasystems.slw41;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Fragment_Summary extends Fragment
{

    public interface goProButtonListener
    {
        public void goPro();
    }

    public goProButtonListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState) {
        View rootLayoutView = inflater.inflate(R.layout.simple_summary, selectionContainer, false);
        mListener = (goProButtonListener) getContext();       //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;

        List<String> loanServicers = new ArrayList<String>();
        loanServicers.add("Nelnet");
        loanServicers.add("Great Lakes Educational Loan Services");
        loanServicers.add("Navient");
        loanServicers.add("MOHELA");
        loanServicers.add("HESC/EdFinancial");
        loanServicers.add("CornerStore");
        loanServicers.add("Granite State - GSMR");
        loanServicers.add("OSLA Servicing");
        loanServicers.add("Debt Management and Collections System");

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, loanServicers);
        ListView loanServicersList = (ListView)rootLayoutView.findViewById(R.id.servicersListView);
        loanServicersList.setAdapter(adapter);

        return rootLayoutView;
    }

}
