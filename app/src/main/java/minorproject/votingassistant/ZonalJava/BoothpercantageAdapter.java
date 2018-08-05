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

/**
 * Created by dk on 18/12/17.
 */

public class BoothpercantageAdapter extends BaseAdapter {


    private Activity activity;
    private LayoutInflater inflater;
    private List<Boothpercantagemodel> register;

    public BoothpercantageAdapter(Activity activity, List<Boothpercantagemodel> bookItems) {
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
            convertView = inflater.inflate(R.layout.boothwisepercentage, null);

        TextView boothname = (TextView) convertView.findViewById(R.id.BoothName);
        TextView boothpercentage= (TextView) convertView.findViewById(R.id.BoothOfficerNumber);

        // getting Current_Info data for the row
        final Boothpercantagemodel m = register.get(position);
        // title
        boothname.setText(m.getB_name());
        boothpercentage.setText(m.getPercentage());

        return convertView;
    }
}

