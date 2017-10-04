package balcorasystems.slw41;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Report_Adapter extends RecyclerView.Adapter<Report_Adapter.ViewHolder>
{



    Object_Borrower masterBorrower = MainActivity.masterBorrower;
    Object_Loan eachLoan;

    @Override
    public Report_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_row_item, parent, false);


        final ViewHolder genericViewholder = new ViewHolder(view);
        return genericViewholder;
    }

    @Override
    public void onBindViewHolder(Report_Adapter.ViewHolder holder, int position)
    {
        eachLoan=masterBorrower.debtAndRepaymentObject.loanPortfolio.get(position);
        String loanTilteText;
        loanTilteText=eachLoan.type + " - " + String.valueOf(eachLoan.startingBalance);

        holder.loanHeadline.setText(loanTilteText);
    }

    @Override
    public int getItemCount()
    {
        Integer loanNumber = masterBorrower.debtAndRepaymentObject.loanPortfolio.size();
        return loanNumber;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView loanHeadline;


        public ViewHolder(View itemView)
        {
            super(itemView);

            loanHeadline=(TextView) itemView.findViewById(R.id.loanTitle);
        }
    }
}
