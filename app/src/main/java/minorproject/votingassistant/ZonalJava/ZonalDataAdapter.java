package minorproject.votingassistant.ZonalJava;

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

import minorproject.votingassistant.ECJava.ECHomeScreen;
import minorproject.votingassistant.R;
import minorproject.votingassistant.ZonalActivity;

/**
 * Created by RR on 05-09-2017.
 */

public class ZonalDataAdapter extends RecyclerView.Adapter<ZonalDataAdapter.MyViewHolder> {
    private Context mContext;
    private List<ZonalData> albumList;
    public int position;
    private ECHomeScreen activity;
    private ZonalData data;
    public ClipData.Item currentItem;
    Fragment fragment= new Fragment();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;
        public CardView cardview;


        public MyViewHolder(View view) {
            super(view);
            View v = view;
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
//            activity = (ECHomeScreen) view.getContext();
//            overflow = (ImageView) view.findViewById(R.id.overflow);
            cardview = (CardView) view.findViewById(R.id.card_view);


        }
    }



    public ZonalDataAdapter(Context mContext, List<ZonalData> albumList) {
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
                .inflate(R.layout.dmcard, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        data = albumList.get(position);
        holder.title.setText(data.getName());


//         loading album cover using Glide library
        Glide.with(mContext).load(data.getThumbnail()).into(holder.thumbnail);
        holder.cardview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//              position = holder.getAdapterPosition();


                Log.e("POSITION",":::::"+holder.getAdapterPosition());
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                switch(holder.getAdapterPosition()){
                    case 0:
                        fragment= new AddBoothOfficer();
                        activity.setTitle("Add BoothOfficer");
                        break;
                    case 1:
                        fragment = new UpdateBoothOfficer();
                        activity.setTitle("Update BoothOfficers");
                        break;
                    case 2:
                        fragment = new Boothvotingstatus();
                        activity.setTitle("BoothOfficers Voting Status");
                        break;
                    case 3:
                        fragment = new BoothOfficerList();
                        activity.setTitle("BoothOfficers List");
                        break;
                }


                ((ZonalActivity)v.getContext()).getSupportFragmentManager().beginTransaction()
              .replace(R.id.fragment1, fragment).addToBackStack(null)
               .commit();

            }
        });

    }




    @Override
    public int getItemCount() {
        return albumList.size();
    }
}


