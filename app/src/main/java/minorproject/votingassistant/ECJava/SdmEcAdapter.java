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
 * Created by dk on 21/12/17.
 */

public class SdmEcAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<SdmEcModel> register;

    public SdmEcAdapter(Activity activity, List<SdmEcModel> bookItems) {
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
            convertView = inflater.inflate(R.layout.dmzonalwiseper, null);


        TextView zonalareaname= (TextView) convertView.findViewById(R.id.ZonalDmName);
        TextView zonalname= (TextView) convertView.findViewById(R.id.ZonalDmNumber);
        // getting Current_Info data for the row
        final SdmEcModel m = register.get(position);
        // title
        zonalareaname.setText(m.getSubdistrict_name());
        zonalname.setText(m.getSdm_per());

        return convertView;
    }
}


