package balcorasystems.slw41;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Example_Adapter extends RecyclerView.Adapter<Example_Adapter.ViewHolder>
{

    Object_Borrower masterBorrower = MainActivity.masterBorrower;

    @Override
    public Example_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_row_item, parent, false);

        final ViewHolder genericViewholder = new ViewHolder(view);
        return genericViewholder;
    }

    @Override
    public void onBindViewHolder(Example_Adapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {

        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
