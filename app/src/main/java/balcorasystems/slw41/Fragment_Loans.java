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


public class Fragment_Loans extends Fragment
{

//    public void addLoan()
//    {
//        //add a new object to the arraylist above
//        this.notifyDataSetChanged();
//        //this is good performance I guess but trivial benefit for data set this small I think
//        //mAdapter.notifyItemInserted(mItems.size() - 1);
//
//        //call a function that will reload the fragment I guess, whether I need to do this depends on if notify changed actually resets the view also and handles size properly
//    }

    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState) {
        final View rootLayoutView = inflater.inflate(R.layout.loans_layout, selectionContainer, false);

        final RecyclerView recyclerView = (RecyclerView) rootLayoutView.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new Loans_Adapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Button newLoan = (Button) rootLayoutView.findViewById(R.id.newLoanButton);
        newLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                RecyclerView.Adapter adapter = new Loans_Adapter();
//                adapter.something.add //add a new Object_Loan to the arraylist of loan objects
//                adapter.notifyDataSetChanged();   //do I actually need this if I'm reloading the adapter? If that doesn't work I may need to set a flag that gets the adapter to update from within or disable this and let it relaunch with new data if that'll work
//                recyclerView.setAdapter(adapter);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
            }
        });

        Button deleteLoan = (Button) rootLayoutView.findViewById(R.id.deleteLoanButton);
        newLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //are you sure dialogube box first perhaps?
                //adapter.something.remove //remove a new Object_Loan to the array list of loan objects
                //adapter.notifyDataSetChanged();   //do I actually need this if I'm reloading the adapter? If that doesn't work I may need to set a flag that gets the adapter to update from within or disable this and let it relaunch with new data if that'll work

            }
        });

        return rootLayoutView;
    }

}
