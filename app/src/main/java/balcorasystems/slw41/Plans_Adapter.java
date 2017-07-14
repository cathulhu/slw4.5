package balcorasystems.slw41;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Plans_Adapter extends RecyclerView.Adapter<Plans_Adapter.ViewHolder>
{

    public interface OnNavigateToDetail
    {
        public void recyclerToDetail();
    }

    public OnNavigateToDetail mListener;

    private ArrayList<String> adapterPlans;

    public Plans_Adapter(ArrayList<String> passedPlans) {
        this.adapterPlans = passedPlans;
    }


    @Override
    public Plans_Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerrow, viewGroup, false);
        final ViewHolder genericViewholder = new ViewHolder(view);
        mListener = (OnNavigateToDetail) view.getContext();       //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Number  " + genericViewholder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                mListener.recyclerToDetail();
            }
        });

        return genericViewholder;
    }

    @Override
    public void onBindViewHolder(Plans_Adapter.ViewHolder viewHolder, int i) {

        //this is behaviour that happens every time an element is added to the recycler view I think
        viewHolder.rowItem.setText(adapterPlans.get(i));

    }


    @Override
    public int getItemCount() {
        return adapterPlans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView rowItem;

        public ViewHolder(View view) {
            super(view);


            rowItem = (TextView) view.findViewById(R.id.rowItem);
        }
    }
}