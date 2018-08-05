package minorproject.votingassistant.DMJava;

/**
 * Created by rk on 30/8/17.
 */

import android.content.ClipData;
import android.content.Context;
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


public class SDMDataAdapter extends RecyclerView.Adapter<SDMDataAdapter.MyViewHolder>{

    private Context mContext;
    private List<SDMData> albumList;
    public int position;
    private DMHomeScreen activity;
    private SDMData data;
    private Fragment fragment;
    public ClipData.Item currentItem;


    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView title, count;
        public ImageView thumbnail, overflow;
        public CardView cardview;


        public MyViewHolder(View view) {
            super(view);
            mContext = view.getContext();
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
//            activity = (ECHomeScreen) view.getContext();
//            overflow = (ImageView) view.findViewById(R.id.overflow);
            cardview = (CardView) view.findViewById(R.id.card_view);

        }
    }



    public SDMDataAdapter(Context mContext, List<SDMData> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sdmcardview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,  int position) {

        data = albumList.get(position);
        holder.title.setText(data.getName());


//         loading album cover using Glide library
        Glide.with(mContext).load(data.getThumbnail()).into(holder.thumbnail);
        holder.cardview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                AppCompatActivity activity = (AppCompatActivity) v.getContext();

                Log.e("POSITION",":::::"+holder.getAdapterPosition());
                switch(holder.getAdapterPosition()){
                    case 0:
                        Log.e("POSITION",":::::"+holder.getAdapterPosition());
                        fragment = new SDMAdd();
                        activity.setTitle("Add SDM");
                        break;

                    case 1 :
                        fragment = new SDMUpdate();
                        activity.setTitle("Update SDM");
                        break;

                    case 2 :
                        fragment = new SDMVotingStatus();
                        activity.setTitle("Voting Status");
                        break;
                    case 3:
                        fragment=new SDMList();
                        activity.setTitle("List Of SDM");
                        break;
                    case 4:
                        fragment=new ZonalList();
                        activity.setTitle("List Of Zonal");
                        break;

                    default:
                        fragment = new DMHomeScreen();
                        break;

                }



                //Create a bundle to pass data, add data, set the bundle to your fragment and:


                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment1, fragment)
                        .addToBackStack(null)
                        .commit();




            }
        });

    }




    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
