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




public class Fragment_Report extends Fragment
{
    public interface toRepaymentChoices
    {
        void gotoPlans();
    }

    public toRepaymentChoices mListener;

    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState) {
        final View rootLayoutView = inflater.inflate(R.layout.loan_report, selectionContainer, false);

        Button nextButton = (Button) rootLayoutView.findViewById(R.id.nextButton);
        Button previousButton = (Button) rootLayoutView.findViewById(R.id.prevButton);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mListener = (toRepaymentChoices) rootLayoutView.getContext();
                mListener.gotoPlans();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        final RecyclerView recyclerView = (RecyclerView) rootLayoutView.findViewById(R.id.loans_detail_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new Adapter_Report();
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        return  rootLayoutView;
    }
}
