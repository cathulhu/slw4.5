package balcorasystems.slw41;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Review_Adapter extends RecyclerView.Adapter<Review_Adapter.ViewHolder>
{

    List<String> foundSummaryTitles = new ArrayList<>();
    final List<String> loanValues = new ArrayList<>();
    public static Integer choice =0;

    public interface OnGoToChangeValue
    {
        public void recyclerToDetail();
    }

    public OnGoToChangeValue mListener;

    private ArrayList<String> adapterPlans;

    public Review_Adapter() {

        foundSummaryTitles=Fragment_MasterQuestionSpawner.summaryTitles;

//        summaryTitles.add("Loan Default");
//        summaryTitles.add("Loan Delinquency");
//        summaryTitles.add("Deceased Borrower");
//        summaryTitles.add("Consolidated Loans");
//        summaryTitles.add("Loan Rehabilitation");
//        summaryTitles.add("Parent PLUS Loans");
//        summaryTitles.add("FFEL Loans");
//        summaryTitles.add("Perkins Loans");
//        summaryTitles.add("Direct Loans");
//        summaryTitles.add("Employment");
//        summaryTitles.add("First Loan Date");
//        summaryTitles.add("Debt, Income, and Payment");
//        summaryTitles.add("Tax Status");
//        summaryTitles.add("Tax Dependants");
//        summaryTitles.add("Debt Types");
//        summaryTitles.add("Debt Servicer");
//
//        loanValues.add(String.valueOf(MainActivity.masterLoan.inDefault));
//        loanValues.add(String.valueOf(MainActivity.masterLoan.inDelinquincy));
//        loanValues.add(String.valueOf(MainActivity.masterLoan.deceased));
//        loanValues.add(String.valueOf(MainActivity.masterLoan.hasConsolidated));
//        loanValues.add(String.valueOf(MainActivity.masterLoan.loanRehab));
//        loanValues.add(String.valueOf(MainActivity.masterLoan.parentLoans));
//        loanValues.add(String.valueOf(MainActivity.masterLoan.ffelLoans));
//        loanValues.add(String.valueOf(MainActivity.masterLoan.perkinsLoans));
//        loanValues.add(String.valueOf(MainActivity.masterLoan.directLoans));
//        loanValues.add(String.valueOf(MainActivity.masterLoan.employmentType));
//        loanValues.add(String.valueOf(MainActivity.masterLoan.loanDate));
//        loanValues.add(String.valueOf(MainActivity.masterLoan.debt));
//        loanValues.add(String.valueOf(MainActivity.masterLoan.taxStatus));
//        loanValues.add(String.valueOf(MainActivity.masterLoan.taxSize));
//        loanValues.add("not implemented types yet");
//        loanValues.add(String.valueOf(MainActivity.masterLoan.servicer));

    }


    @Override
    public Review_Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_row, viewGroup, false);
        final ViewHolder genericViewholder = new ViewHolder(view);
        mListener = (OnGoToChangeValue) view.getContext();       //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Number  " + genericViewholder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                choice=genericViewholder.getAdapterPosition();
                mListener.recyclerToDetail();
            }
        });

        return genericViewholder;
    }

    @Override
    public void onBindViewHolder(Review_Adapter.ViewHolder viewHolder, int i) {

//        this is behaviour that happens every time an element is added to the recycler_plans view I think
//        viewHolder.rowItem.setText(adapterPlans.get(i));

        viewHolder.title.setText(foundSummaryTitles.get(i));
//        viewHolder.value.setText(loanValues.get(i));


    }


    @Override
    public int getItemCount() {
        return foundSummaryTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView value;

        public ViewHolder(View view) {
            super(view);


            title = (TextView) view.findViewById(R.id.title);
            value = (TextView) view.findViewById(R.id.value);
        }
    }
}