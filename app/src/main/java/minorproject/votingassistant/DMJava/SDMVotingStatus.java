package minorproject.votingassistant.DMJava;

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

import java.util.HashMap;
import java.util.Map;

import minorproject.votingassistant.R;
import minorproject.votingassistant.SDMJava.ZonalSdmWise;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class SDMVotingStatus extends Fragment {
    TextView SdmDm,ZonalDm,Votes;
    FragmentManager fm;
    String dmID;
    SharedPreferences sharedPreferences;
    public String DATA_URL = "http://bond-vehicles.000webhostapp.com/Voting/votingapi.php?dm_id=";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sdmvoting_status, container, false);
        SdmDm= (TextView) view.findViewById(R.id.SDMWise);
        ZonalDm= (TextView) view.findViewById(R.id.ZonalWise);
        Votes= (TextView) view.findViewById(R.id.votes);

        sharedPreferences = getActivity().getSharedPreferences("LoginData",MODE_PRIVATE);

        if(sharedPreferences!=null){

            dmID = sharedPreferences.getString("dm_id",null);

        }



        SdmDm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new SdmDmWise();
                fm=getFragmentManager();
                FragmentTransaction fragmentTransaction=fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment1,fragment).addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        ZonalDm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ZonalDmWise();
                fm=getFragmentManager();
                FragmentTransaction fragmentTransaction=fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment1,fragment).addToBackStack(null);
                fragmentTransaction.commit();

            }
        });


download_Item();
        return view;

    }

    private void download_Item()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DATA_URL+dmID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        // Toast.makeText(SignIn.this,response,Toast.LENGTH_LONG).show();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);


                            Votes.setText(jsonObject.getString("dm_per"));
                            Log.e("subdistrictname",":::::"+jsonObject.getString("dm_per"));
                            //  System.out.println(Ecper);





                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getActivity(),"Time Out", Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


}
