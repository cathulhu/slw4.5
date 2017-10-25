package balcorasystems.slw41;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_LoanIssues extends RecyclerView.Adapter<Adapter_LoanIssues.ViewHolder>
{

    Object_Borrower masterBorrower = MainActivity.masterBorrower;
    ArrayList<String> issues = Logic_LoanIssues.CheckOutstanding(masterBorrower);

    @Override
    public Adapter_LoanIssues.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loan_issue_row_item, parent, false);

        final ViewHolder genericViewholder = new ViewHolder(view);
        return genericViewholder;
    }

    @Override
    public void onBindViewHolder(Adapter_LoanIssues.ViewHolder holder, int position)
    {
        holder.issue.setText(issues.get(position));
    }

    @Override
    public int getItemCount() {

        return issues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView issue;

        public ViewHolder(View itemView)
        {
            super(itemView);

            issue = (TextView) itemView.findViewById(R.id.loanIssueItem);
        }
    }
}
