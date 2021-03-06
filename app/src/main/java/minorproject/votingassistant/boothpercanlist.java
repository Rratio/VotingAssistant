package minorproject.votingassistant;

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

import minorproject.votingassistant.ZonalJava.BoothListAdapter;
import minorproject.votingassistant.ZonalJava.BoothListModel;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;


public class boothpercanlist extends Fragment {
    public String DATA_URL = "http://bond-vehicles.000webhostapp.com/Voting/getapi.php?booth_id=";
    Fragment fragment;
    public BoothPercenAdapter adapter;
    ProgressDialog progressDialog;
    ListView BoothListing;
    String zID;
    SharedPreferences sharedPreferences;
    public ArrayList<Boothpercanmodel> sampel = new ArrayList<Boothpercanmodel>();
    // String[] sample = {"Bhilwara", "Udaipur", "Jaipur"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_boothpercanlist, container, false);
        BoothListing = (ListView) view.findViewById(R.id.boothpercan);

        adapter = new BoothPercenAdapter(getActivity(), sampel);
        BoothListing.setAdapter(adapter);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        sharedPreferences = getActivity().getSharedPreferences("LoginData",MODE_PRIVATE);

        if(sharedPreferences!=null){

            zID = sharedPreferences.getString("booth_id",null);
        }





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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DATA_URL+zID,
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
                                    Boothpercanmodel ji = new Boothpercanmodel();
                                    ji.setTime(json_data.getString("time"));
                                    ji.setTotalseats(json_data.getString("totalseats"));
                                    ji.setSeats(json_data.getString("seats"));
                                    ji.setPercentage(json_data.getString("percentage"));
                                    Log.e("boothname",":::::"+json_data.getString("time"));
                                    Log.e("boothofficername",":::::"+json_data.getString("totalseats"));
                                    Log.e("boothofficernumber",":::::"+json_data.getString("seats"));
                                    Log.e("boothper","::::::"+json_data.getString("percentage"));
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