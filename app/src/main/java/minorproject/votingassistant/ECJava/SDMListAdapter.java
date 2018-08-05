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

public class  SDMListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<SDMListModel> register;

    public SDMListAdapter(Activity activity, List<SDMListModel> bookItems) {
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
            convertView = inflater.inflate(R.layout.sdmlistlayout, null);

        TextView sdmdistrict= (TextView) convertView.findViewById(R.id.SubDistrict);
        TextView sdmname= (TextView) convertView.findViewById(R.id.SdmName);
        TextView sdmnumber= (TextView) convertView.findViewById(R.id.SdmNumber);

        // getting Current_Info data for the row
        final SDMListModel m = register.get(position);
        // title
        sdmdistrict.setText(m.getSubdistrict_name());
        sdmname.setText(m.getSdm_name());
        sdmnumber.setText( m.getSdm_number());

        return convertView;
    }
}
