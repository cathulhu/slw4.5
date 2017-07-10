package balcorasystems.slw41;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Plans_Adapter extends RecyclerView.Adapter<Plans_Adapter.ViewHolder>
{
    private ArrayList<String> adapterPlans;

    public Plans_Adapter(ArrayList<String> passedPlans) {
        this.adapterPlans = passedPlans;
    }

    @Override
    public Plans_Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerrow, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Plans_Adapter.ViewHolder viewHolder, int i) {

        viewHolder.rowItem.setText(adapterPlans.get(i));
    }

    @Override
    public int getItemCount() {
        return adapterPlans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView rowItem;
        public ViewHolder(View view) {
            super(view);

            rowItem = (TextView)view.findViewById(R.id.rowItem);
        }
    }

}
