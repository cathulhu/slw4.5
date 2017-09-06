package balcorasystems.slw41;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;


public class Fragment_Loans extends Fragment
{
    public static Boolean firstRun=true;
    RecyclerView.Adapter adapter = new Loans_Adapter();

    public void addLoan()
    {
        Fragment_MasterQuestionSpawner.masterBorrower.debtObject.addBlankLoan();
        adapter.notifyDataSetChanged();
    }

    //the code to populate values in a loan goes in the adapter not here

    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState) {
        final View rootLayoutView = inflater.inflate(R.layout.loans_layout, selectionContainer, false);

        final RecyclerView recyclerView = (RecyclerView) rootLayoutView.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (firstRun==true)
        {
            addLoan();
            firstRun=false;

        }

        Button newLoan = (Button) rootLayoutView.findViewById(R.id.newLoanButton);

        newLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(view.getContext(), "new", Toast.LENGTH_SHORT).show();
                addLoan();
            }
        });

        return rootLayoutView;
    }

}
