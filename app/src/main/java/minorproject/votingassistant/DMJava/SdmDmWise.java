package minorproject.votingassistant.DMJava;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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


public class SdmDmWise extends Fragment {


    public String DATA_URL = "http://bond-vehicles.000webhostapp.com/Voting/listapi.php?dm_id=";
    Fragment fragment;
    public SdmDmAdapter adapter;
    ProgressDialog progressDialog;
    ListView SDMList;
    String dmID;
    SharedPreferences sharedPreferences;
    public ArrayList<SdmDmmodel> sampel = new ArrayList<SdmDmmodel>();
    // String[] sample = {"Bhilwara", "Udaipur", "Jaipur"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sdm_dm_wise, container, false);
        SDMList = (ListView) view.findViewById(R.id.SdmDmList);

        adapter = new SdmDmAdapter(getActivity(), sampel);
        SDMList.setAdapter(adapter);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        sharedPreferences = getActivity().getSharedPreferences("LoginData",MODE_PRIVATE);

        if(sharedPreferences!=null){

            dmID = sharedPreferences.getString("dm_id",null);

        }

//




//        DMList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                int Id=sampel.get(+position).getDm_number();
//                String
//                Toast.makeText(getActivity(),id+"",Toast.LENGTH_LONG).show();
//            }
//        });

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
                        hidePDialog();
                        // Toast.makeText(SignIn.this,response,Toast.LENGTH_LONG).show();
                        JSONArray jArray = null;
                        try {
                            jArray = new JSONArray(response);
                            if(jArray.length()>0) {
                                sampel.clear();
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    SdmDmmodel ji = new SdmDmmodel();
                                    ji.setSubdistrict_name(json_data.getString("subdistrict_name"));
                                    ji.setSdm_per(json_data.getString("sdm_per"));
                                   // ji.setSdm_number(json_data.getString("sdm_number"));
                                    Log.e("subdistrictname",":::::"+json_data.getString("subdistrict_name"));
                                    Log.e("sdmper",":::::"+json_data.getString("sdm_per"));
                                    Log.e("sdmnumber",":::::"+json_data.getString("sdm_number"));
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

                        Toast.makeText(getActivity(),"Time Out", Toast.LENGTH_LONG).show();
                        hidePDialog();
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



