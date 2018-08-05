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
import minorproject.votingassistant.ZonalJava.BoothListModel;

/**
 * Created by dk on 10/10/17.
 */

public class BoothListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<BoothListModel> register;

    public BoothListAdapter(Activity activity, List<BoothListModel> bookItems) {
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
            convertView = inflater.inflate(R.layout.boothlistlayout, null);

        TextView boothname= (TextView) convertView.findViewById(R.id.BoothName);
        TextView boothofficername= (TextView) convertView.findViewById(R.id.BoothOfficerName);
        TextView boothofficernumber= (TextView) convertView.findViewById(R.id.BoothOfficerNumber);

        // getting Current_Info data for the row
        final BoothListModel m = register.get(position);
        // title
        boothname.setText(m.getB_name());
        boothofficername.setText(m.getBooth_name());
        boothofficernumber.setText( m.getBooth_number());

        return convertView;
    }
}
