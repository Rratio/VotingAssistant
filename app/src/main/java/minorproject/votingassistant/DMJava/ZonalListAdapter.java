package minorproject.votingassistant.DMJava;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import minorproject.votingassistant.R;
import minorproject.votingassistant.SDMJava.ZonalListModel;

/**
 * Created by dk on 10/10/17.
 */

public class ZonalListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ZonalListModel> register;

    public ZonalListAdapter(Activity activity, List<ZonalListModel> bookItems) {
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
            convertView = inflater.inflate(R.layout.zonallistlayout, null);

        TextView zonalareaname= (TextView) convertView.findViewById(R.id.ZonalAreaName);
        TextView zonalname= (TextView) convertView.findViewById(R.id.ZonalName);
        TextView zonalnumber= (TextView) convertView.findViewById(R.id.ZonalNumber);

        // getting Current_Info data for the row
        final ZonalListModel m = register.get(position);
        // title
        zonalareaname.setText(m.getZ_name());
        zonalname.setText(m.getZonal_name());
        zonalnumber.setText( m.getZonal_number());

        return convertView;
    }
}
