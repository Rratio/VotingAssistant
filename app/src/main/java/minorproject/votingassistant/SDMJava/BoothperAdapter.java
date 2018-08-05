package minorproject.votingassistant.SDMJava;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import minorproject.votingassistant.R;
import minorproject.votingassistant.ZonalJava.*;
import minorproject.votingassistant.ZonalJava.BoothListModel;

/**
 * Created by dk on 20/12/17.
 */

public class BoothperAdapter  extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<BoothperModel> register;

    public BoothperAdapter(Activity activity, List<BoothperModel> bookItems) {
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
            convertView = inflater.inflate(R.layout.zonalsdmpercentage, null);

        TextView zonalareaname= (TextView) convertView.findViewById(R.id.ZonalSdmName);
        TextView zonalname= (TextView) convertView.findViewById(R.id.ZonalSdmPercantage);
        TextView zonalnumber= (TextView) convertView.findViewById(R.id.Zonalpercentage);

        // getting Current_Info data for the row
        final BoothperModel m = register.get(position);
        // title
        zonalareaname.setText(m.getB_name());
        zonalname.setText(m.getPercentage());


        return convertView;
    }
}

