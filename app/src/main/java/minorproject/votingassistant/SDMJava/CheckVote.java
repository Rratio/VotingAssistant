package minorproject.votingassistant.SDMJava;

import android.content.SharedPreferences;
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
import minorproject.votingassistant.ZonalJava.Boothwise;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;


public class CheckVote extends Fragment {
    TextView ZonalSdmper,BoothSdmper,SdmOverall;
    String sdmID;
    public String DATA_URL="http://bond-vehicles.000webhostapp.com/Voting/votingapi.php?sdm_id=";
    FragmentManager fm;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_check_vote, container, false);
        ZonalSdmper= (TextView) view.findViewById(R.id.zonalwise);
        BoothSdmper= (TextView) view.findViewById(R.id.boothwise);
        SdmOverall= (TextView) view.findViewById(R.id.votes);

        sharedPreferences = getActivity().getSharedPreferences("LoginData",MODE_PRIVATE);

        if(sharedPreferences!=null){

            sdmID = sharedPreferences.getString("sdm_id",null);

        }

        ZonalSdmper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ZonalSdmWise();
                fm=getFragmentManager();
                FragmentTransaction fragmentTransaction=fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment1,fragment).addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        BoothSdmper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new BoothSdmWise();
                fm=getFragmentManager();
                FragmentTransaction fragmentTransaction=fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment1,fragment).addToBackStack(null);
                fragmentTransaction.commit();

            }
        });


download_Item();

        return  view;

    }
    private void download_Item()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DATA_URL+sdmID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        // Toast.makeText(SignIn.this,response,Toast.LENGTH_LONG).show();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);


                            SdmOverall.setText(jsonObject.getString("sdm_per"));
                            Log.e("subdistrictname",":::::"+jsonObject.getString("sdm_per"));
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


