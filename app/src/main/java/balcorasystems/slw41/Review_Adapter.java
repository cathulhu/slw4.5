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
        public void toReviewQuestion();
    }
    public OnGoToChangeValue mListener;

    private ArrayList<String> adapterPlans;

    public Review_Adapter() {

        foundSummaryTitles=Fragment_MasterQuestionSpawner.summaryTitles;

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
                mListener.toReviewQuestion();
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