package minorproject.votingassistant.ECJava;

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

import minorproject.votingassistant.ECActivity;
import minorproject.votingassistant.R;


public class DMDataAdapter extends RecyclerView.Adapter<DMDataAdapter.MyViewHolder>{

    private Context mContext;
    private List<DMData> albumList;
    public int position;
    private DMData data;
    private Fragment fragment;
    public ClipData.Item currentItem;
    AppCompatActivity activity;

    Bundle loginData;

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView title, count;
        public ImageView thumbnail, overflow;
        public CardView cardview;


        public MyViewHolder(View view) {
            super(view);
            mContext = view.getContext();
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

            cardview = (CardView) view.findViewById(R.id.card_view);
          ((ECActivity)mContext).getSupportFragmentManager().beginTransaction().commit();






        }
    }



    public DMDataAdapter(Context mContext, List<DMData> albumList) {
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
    public void onBindViewHolder(final MyViewHolder holder,  int position) {

        data = albumList.get(position);
        holder.title.setText(data.getName());



//        home = new ECHomeScreen();
//        loginData = home.getArguments();
//        Log.e("Name....","..."+loginData.getString("ec_name"));



//         loading album cover using Glide library
        Glide.with(mContext).load(data.getThumbnail()).into(holder.thumbnail);
        holder.cardview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                activity = (AppCompatActivity) v.getContext();


                
                switch(holder.getAdapterPosition()){
                    case 0:
                        Log.e("POSITION",":::::"+holder.getAdapterPosition());
                        fragment = new DMAdd();
                        activity.setTitle("Add DM");

                        break;

                    case 1 :
                             fragment = new DMUpdate();
                            activity.setTitle("Update DM");
                              break;

                    case 2 :
                        fragment = new CheckVotingStatus();
                        activity.setTitle("Check Voting Status");
                        break;
                    case 3:
                        fragment=new DMList();
                        activity.setTitle("List of DM");
                        break;
                    case 4:
                        fragment=new SDMList();
                        activity.setTitle("List of SDM");
                        break;

                    default:
                        fragment = new ECHomeScreen();
                        break;

                }

//                Toast.makeText(v.getContext(), "You have clicked " + v.getId(), Toast.LENGTH_SHORT).show();
//                AppCompatActivity activity1 = (AppCompatActivity) v.getContext();

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
