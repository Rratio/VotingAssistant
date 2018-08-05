package minorproject.votingassistant.ECJava;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import minorproject.votingassistant.R;

/**
 * Created by dk on 10/10/17.
 */

public class DmAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<DmModel> register;

    public DmAdapter(Activity activity, List<DmModel> bookItems) {
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
        final DmModel m = register.get(position);
        // title
        dk.setText(m.getDistrict_name());

        return convertView;
    }
}
