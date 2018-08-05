package minorproject.votingassistant.ZonalJava;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import minorproject.votingassistant.R;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;


public class Boothwise extends Fragment {


    public String DATA_URL = "http://bond-vehicles.000webhostapp.com/Voting/listapi.php?zonal_id=";
    public BoothpercantageAdapter adapter;
    ProgressDialog progressDialog;
    ListView BoothListing;
    String zID;
    SharedPreferences sharedPreferences;
    public ArrayList<Boothpercantagemodel> sampel = new ArrayList<Boothpercantagemodel>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_boothwise, container, false);

       BoothListing = (ListView) view.findViewById(R.id.bothpe);
        adapter = new BoothpercantageAdapter(getActivity(), sampel);
        BoothListing.setAdapter(adapter);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        sharedPreferences = getActivity().getSharedPreferences("LoginData",MODE_PRIVATE);

        if(sharedPreferences!=null){

            zID = sharedPreferences.getString("zonal_id",null);
        }


        download_Item();



        return view;
    }

    private void download_Item() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DATA_URL+zID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();
                        onDestroy();
                        // Toast.makeText(SignIn.this,response,Toast.LENGTH_LONG).show();
                        JSONArray jArray = null;
                        try {
                            jArray = new JSONArray(response);
                            if (jArray.length() > 0) {
                                sampel.clear();
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    Boothpercantagemodel ji = new Boothpercantagemodel();
                                    ji.setPercentage(json_data.getString("percentage"));
                                    ji.setB_name(json_data.getString("b_name"));
                                    Log.e("percentage", ":::::" + json_data.getString("percentage"));
                                    Log.e("boothofficername", ":::::" + json_data.getString("booth_name"));
                                    Log.e("boothofficernumber", ":::::" + json_data.getString("booth_number"));
                                    //  Toast.makeText(getActivity(),"Name------"+DATA_URL+zID,Toast.LENGTH_LONG).show();
                                    //System.out.println(json_data.getString("name"));
                                    sampel.add(ji);

                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getActivity(), "Time Out", Toast.LENGTH_LONG).show();
                        hidePDialog();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }

    }


}




