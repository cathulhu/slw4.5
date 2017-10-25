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


public class Fragment_Issues extends Fragment
{

    public interface gotoReport
    {
        void toReport();
    }

    public interface backToReview
    {
        void NextAfterSelection(String reviewString);
    }

    public gotoReport mListener;
    public backToReview reviewListener;

    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState) {
        final View rootLayoutView = inflater.inflate(R.layout.issues, selectionContainer, false);




        Button nextButton = (Button) rootLayoutView.findViewById(R.id.nextButton);
        Button previousButton = (Button) rootLayoutView.findViewById(R.id.prevButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener = (gotoReport) rootLayoutView.getContext();
                mListener.toReport();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reviewListener = (backToReview) rootLayoutView.getContext();
                reviewListener.NextAfterSelection("review");

            }
        });


        final RecyclerView recyclerView = (RecyclerView) rootLayoutView.findViewById(R.id.loanSummaryList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new Adapter_LoanIssues();
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootLayoutView;
    }





}
