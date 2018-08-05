package minorproject.votingassistant.ECJava;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import minorproject.votingassistant.DMJava.SdmDmWise;
import minorproject.votingassistant.R;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;


public class CheckVotingStatus extends Fragment {
    FragmentManager fm;

    TextView zonelwiseec, sdmwiseec, EcOverall;
    String ecID;
    String Ecper;
    SharedPreferences sharedPreferences;
    public String DATA_URL = "http://bond-vehicles.000webhostapp.com/Voting/votingapi.php?ec_id=";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_check_voting_status, container, false);
        zonelwiseec = (TextView) view.findViewById(R.id.zonalwise);
        sdmwiseec = (TextView) view.findViewById(R.id.boothwise);
        EcOverall = (TextView) view.findViewById(R.id.votes);


        sharedPreferences = getActivity().getSharedPreferences("LoginData",MODE_PRIVATE);

        if(sharedPreferences!=null){
            ecID = sharedPreferences.getString("ec_id",null);

        }


            zonelwiseec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new DmEcWise();
                    fm = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment1, fragment).addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });

            sdmwiseec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new SdmEcWise();
                    fm = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment1, fragment).addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });
            download_Item();

            return view;
        }


    private void download_Item() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DATA_URL+ecID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, response.toString());
                        // Toast.makeText(SignIn.this,response,Toast.LENGTH_LONG).show();

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);


                            EcOverall.setText(jsonObject.getString("ec_per"));
                            Log.e("subdistrictname", ":::::" + jsonObject.getString("ec_per"));


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getActivity(), "Time Out", Toast.LENGTH_LONG).show();
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


}

