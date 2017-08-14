package balcorasystems.slw41;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class Plans_RecyclerFragment extends Fragment{

    ArrayList plans;


    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState) {
        final View rootLayoutView = inflater.inflate(R.layout.recycler_plans, selectionContainer, false);


        final RecyclerView recyclerView = (RecyclerView) rootLayoutView.findViewById(R.id.recyclerList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        plans = new ArrayList<>();
        plans.add("Standard Repayment");
        plans.add("Simple IBR");
        plans.add("Opportunistic IBR");

        RecyclerView.Adapter adapter = new Plans_Adapter(plans);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());



        return rootLayoutView;

    }





}
