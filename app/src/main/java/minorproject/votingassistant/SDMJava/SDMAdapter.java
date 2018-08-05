package minorproject.votingassistant.SDMJava;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import minorproject.votingassistant.R;
import minorproject.votingassistant.SDMActivity;

//created by dk on 30/08/2017
public class SDMAdapter extends RecyclerView.Adapter<SDMAdapter.MyViewHolder>
{
    Fragment fragment;
    private android.content.Context Context;
    private List<SDMModel> sdmModelList;
    Bundle loginData;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imagecard;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.sdmcard);
            textView = (TextView) view.findViewById(R.id.sdmtext);
            imagecard = (ImageView) view.findViewById(R.id.sdmimage);
        }
    }


    public SDMAdapter(android.content.Context mContext, List<SDMModel> sdmModelList) {
        this.Context = mContext;
        this.sdmModelList = sdmModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sdmcard, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        SDMModel model = sdmModelList.get(position);
        holder.textView.setText(model.getName());
        Log.e("POSITION",":::::"+holder.getAdapterPosition());

        Glide.with(Context).load(model.getThmbnail()).into(holder.imagecard);
        holder.cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e("POSITIONg",":::::"+holder.getAdapterPosition());


//                position = holder.getAdapterPosition();

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                switch(holder.getAdapterPosition()){
                    case 0:
                        fragment= new AddZonal();
                        activity.setTitle("Add Zonal");
                        break;
                    case 1:
                        fragment= new UpdateZonal();
                        activity.setTitle("Update Zonal");
                        break;
                    case 2:
                        fragment=new CheckVote();
                        activity.setTitle("Zonal Voting Status");
                        break;
                    case 3:
                        fragment=new ZonalList();
                        activity.setTitle("List of Zonal");
                        break;
                    case 4:
                        fragment=new Boothlist();
                        activity.setTitle("BoothOfficers List");
                        break;
                }

                  ((SDMActivity)v.getContext()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment1, fragment)
                          .addToBackStack(null)
                .commit();

            }
        });


    }

    @Override
    public int getItemCount() {
        return sdmModelList.size();
    }
}


