package balcorasystems.slw41;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Adapter_Report extends RecyclerView.Adapter<Adapter_Report.ViewHolder>
{




    Object_Borrower masterBorrower = MainActivity.masterBorrower;
    Object_Loan eachLoan;

    @Override
    public Adapter_Report.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_row_item, parent, false);


        final ViewHolder genericViewholder = new ViewHolder(view);
        return genericViewholder;
    }

    @Override
    public void onBindViewHolder(Adapter_Report.ViewHolder holder, int position)
    {
        eachLoan=masterBorrower.debtAndRepaymentObject.loanPortfolio.get(position);
        String balanceValue = String.valueOf(eachLoan.currentBalance);
        balanceValue = formatNumbers(balanceValue);
        String loanTilteText;
        loanTilteText=eachLoan.type + " - $" + balanceValue;

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



    public String formatNumbers(String input) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(input);
        NumberFormat nf = NumberFormat.getInstance();
        StringBuffer sb = new StringBuffer();
        while(m.find()) {
            String g = m.group();
            m.appendReplacement(sb, nf.format(Double.parseDouble(g)));
        }
        return m.appendTail(sb).toString();
    }
}
