package minorproject.votingassistant.ZonalJava;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import minorproject.votingassistant.R;
import minorproject.votingassistant.ZonalJava.BoothModel;

/**
 * Created by lenovo-pc on 10/26/2017.
 */


    public class BoothAdapter extends BaseAdapter {

        private Activity activity;
        private LayoutInflater inflater;
        private List<BoothModel> register;

        public BoothAdapter(Activity activity, List<BoothModel> bookItems) {
            this.activity = activity;
            this.register = bookItems;
        }

        public int getCount() {
            return register.size();
        }


        public Object getItem(int location) {
            return register.get(location);
        }


        public long getItemId(int position) {
            return position;
        }


        public View getView(int position, View convertView, ViewGroup parent) {

            if (inflater == null)
                inflater = (LayoutInflater) activity
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null)
                convertView = inflater.inflate(R.layout.dmlayout, null);

            TextView dk= (TextView) convertView.findViewById(R.id.gk);

            // getting Current_Info data for the row
            final BoothModel m = register.get(position);
            // title
            dk.setText(m.getZ_name());

            return convertView;
        }
    }


