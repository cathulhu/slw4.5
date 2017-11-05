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


public class Fragment_Plans extends Fragment{

    ArrayList plans;


    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState) {
        final View rootLayoutView = inflater.inflate(R.layout.recycler_plans, selectionContainer, false);


        final RecyclerView recyclerView = (RecyclerView) rootLayoutView.findViewById(R.id.recyclerList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        plans = new ArrayList<>();
        plans.add("Standard Repayment");
        plans.add("PAYE");
        plans.add("REPAYE");

        //TODO: Instead of just saying ok make 3 plans or whatever, I should run a calculation that checks a few different strategies first then decides how many plans and perhaps of what type should be spawned first.

        RecyclerView.Adapter adapter = new Adapter_Plans(plans);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootLayoutView;
    }





}
