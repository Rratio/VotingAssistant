package minorproject.votingassistant;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import minorproject.votingassistant.ZonalJava.Boothpercantagemodel;

/**
 * Created by lenovo-pc on 1/15/2018.
 */

public class BoothPercenAdapter extends BaseAdapter {


    private Activity activity;
    private LayoutInflater inflater;
    private List<Boothpercanmodel> register;

    public BoothPercenAdapter(Activity activity, List<Boothpercanmodel> bookItems) {
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
            convertView = inflater.inflate(R.layout.boothlayoutlist, null);

        TextView boothtime = (TextView) convertView.findViewById(R.id.boothtime);
        TextView boothtotalseats= (TextView) convertView.findViewById(R.id.boothtotalvoter);
        TextView boothvotes = (TextView) convertView.findViewById(R.id.boothvotes);
        TextView boothpe= (TextView) convertView.findViewById(R.id.boothpercentage);


        // getting Current_Info data for the row
        final Boothpercanmodel m = register.get(position);
        // title
        boothtime.setText(m.getTime());
        boothtotalseats.setText(m.getTotalseats());
        boothvotes.setText(m.getSeats());
        boothpe.setText(m.getPercentage());

        return convertView;
    }
}




