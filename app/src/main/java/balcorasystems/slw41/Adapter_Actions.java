package balcorasystems.slw41;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by L on 11/4/2017.
 */

public class Adapter_Actions extends RecyclerView.Adapter<Adapter_Actions.ViewHolder>
{
    Integer coordinate = 0;
    ArrayList<String> Values;
    ArrayList<Date> Dates;

    Adapter_Actions(Integer passedCoordinate, ArrayList<String> passedValues, ArrayList<Date> passedDates)
    {
        coordinate=passedCoordinate;
        Values=passedValues;
        Dates=passedDates;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.actions_row_item, parent, false);

        final ViewHolder genericViewholder = new ViewHolder(view);
        return genericViewholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.actionText.setText(Values.get(position));
        holder.dateText.setText(String.valueOf(Dates.get(position)));
    }

    @Override
    public int getItemCount() {
        return Values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView actionText;
        TextView dateText;

        public ViewHolder(View itemView)
        {
            super(itemView);

            actionText = (TextView) itemView.findViewById(R.id.actionValue);
            dateText = (TextView) itemView.findViewById(R.id.dueDate);
        }
    }
}
