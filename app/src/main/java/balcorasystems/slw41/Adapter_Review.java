package balcorasystems.slw41;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Review extends RecyclerView.Adapter<Adapter_Review.ViewHolder>
{
    Object_Borrower borrower = new Object_Borrower();
    List<String> foundSummaryTitles = new ArrayList<>();
    public static Integer choice =0;

    public interface OnGoToChangeValue
    {
        void toReviewQuestion();
    }
    public OnGoToChangeValue mListener;

    private ArrayList<String> adapterPlans;

    public Adapter_Review()
    {

        foundSummaryTitles=Fragment_MasterQuestionSpawner.summaryTitles;

        //removing review
        for (int i = 0; i < foundSummaryTitles.size(); i++)
        {
            if (foundSummaryTitles.get(i).equals("Review"))
            {
                foundSummaryTitles.remove(i);
            }
        }

    }


    @Override
    public Adapter_Review.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_row, viewGroup, false);
        final ViewHolder genericViewholder = new ViewHolder(view);
        mListener = (OnGoToChangeValue) view.getContext();       //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Number  " + genericViewholder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                choice=genericViewholder.getAdapterPosition();
                mListener.toReviewQuestion();
            }
        });

        return genericViewholder;
    }

    @Override
    public void onBindViewHolder(Adapter_Review.ViewHolder viewHolder, int i) {

//        this is behaviour that happens every time an element is added to the recycler_plans view I think
        borrower=Fragment_MasterQuestionSpawner.Borrower;


        String title = foundSummaryTitles.get(i);
        viewHolder.title.setText(title);

         if (title.equals("Repayment Status"))
        {
            viewHolder.value.setText(borrower.repaymentStatus);
        }
        else if (title.equals("Deceased Borrower"))
        {
            viewHolder.value.setText(String.valueOf(borrower.deceased));
        }
        else if (title.equals("Loan Rehabilitation"))
        {
            viewHolder.value.setText(String.valueOf(borrower.loanRehab));
        }
        else if (title.equals("First Loan Date"))
        {
            if (borrower.timeAfter14)
            {
                viewHolder.value.setText("After Oct 2014");
            }
            else if (borrower.timeBetween11to14)
            {
                viewHolder.value.setText("Between Oct 1998 and Oct 2014");
            }
            else if (borrower.timeBefore98)
            {
                viewHolder.value.setText("Before Oct 1998");
            }
            else if (borrower.timeBetween07to11)
            {
                viewHolder.value.setText("Between Oct 2007 and Oct 2011");
            }
            else if (borrower.timeBetween98to07)
            {
                viewHolder.value.setText("Between Oct 1998 and Oct 2007");
            }

        }
        else if (title.equals("Repayment Date"))
        {
            viewHolder.value.setText(String.valueOf(borrower.repaymentDate));
        }
        else if (title.equals("Debt Servicer"))
        {
            viewHolder.value.setText(String.valueOf(borrower.servicer));
        }
        else if (title.equals("Employment"))
        {
            viewHolder.value.setText(String.valueOf(borrower.employmentType));
        }
        else if (title.equals("Marital Status"))
        {
            if (borrower.isMarried)
            {
                viewHolder.value.setText("Married");
            }
            else
            {
                viewHolder.value.setText("Not married");
            }
        }
        else if (title.equals("Tax Status"))
        {
            viewHolder.value.setText(String.valueOf(borrower.taxStatus));
        }
        else if (title.equals("Tax Dependants"))
        {
            viewHolder.value.setText(String.valueOf(borrower.taxSize) + " dependants");
        }
        else if (title.equals("Income"))
        {
            String incomeString = new String();

            if (borrower.isMarried)
            {
                incomeString = "Spouse $" + borrower.startingSpouseIncome + "\n";
            }

            incomeString += "Borrower $" + borrower.startingPrimaryIncome;

            viewHolder.value.setText(incomeString);
        }
        else if (title.equals("Loan Details"))
        {
            String loansString = new String();

            for (Object_Loan loan:borrower.debtAndRepaymentObject.loanPortfolio)
            {
                    loansString += loan.type + " " + loan.currentBalance + " APR " + loan.interestRate + "%" + "\n";
            }

            viewHolder.value.setText(loansString);
        }


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