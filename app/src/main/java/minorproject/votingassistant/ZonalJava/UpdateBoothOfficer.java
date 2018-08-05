package minorproject.votingassistant.ZonalJava;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import minorproject.votingassistant.R;

import static android.content.Context.MODE_PRIVATE;


public class UpdateBoothOfficer extends Fragment implements AdapterView.OnItemSelectedListener,TextWatcher {
    Spinner spinner;
    private EditText Boothname, Boothnumber,totalseats;
    private Button updateBooth;
    public String Register_url="http://bond-vehicles.000webhostapp.com/Voting/boothapi.php";
    public String DATA_URL="http://bond-vehicles.000webhostapp.com/Voting/spinnerapi.php?z_id=";
    public String UPDATE_URL="http://bond-vehicles.000webhostapp.com/Voting/updateapi.php?b_id=";
    ProgressDialog progressDialog;
    Fragment fragment;
    public BoothAdapter adapter;
    private boolean nv, fv, Success,Update;
    BoothModel ji;
    SharedPreferences sharedPreferences;
    String id,area, name ,Msg,Message, BoothName,BoothNumber,TotalSeats;
    int ID = 0;
    public ArrayList<BoothModel> sampel=new ArrayList<BoothModel>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_booth_officer, container, false);

        sharedPreferences = getActivity().getSharedPreferences("LoginData",MODE_PRIVATE);

        if(sharedPreferences!=null){

            name = sharedPreferences.getString("zonal_name",null);
            id = sharedPreferences.getString("zonal_id",null);
            area = sharedPreferences.getString("z_id",null);

        }


        spinner = (Spinner)view.findViewById(R.id.spinner);
        Boothname = (EditText) view.findViewById(R.id.boothname);
        Boothnumber = (EditText) view.findViewById(R.id.boothnumber);
        totalseats = (EditText) view.findViewById(R.id.totalseats);

        updateBooth = (Button) view.findViewById(R.id.updatebooth);
        adapter = new BoothAdapter(getActivity(), sampel);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(this);
        spinner.setOnItemSelectedListener(this);

        Boothname.addTextChangedListener(this);
        Boothnumber.addTextChangedListener(this);
        updateBooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);




                nv = isValidPhoneNumber(Boothnumber.getText().toString());
                fv = isValidName(Boothname.getText().toString());

                if (nv && fv) {
                    callAPI();
                }else {
                    Toast.makeText(getActivity(),"Enter Valid Number !",Toast.LENGTH_LONG).show();
                }
            }
        });

        download_Item();
        return view;
    }


    private void callAPI(){

        final String sname = Boothname.getText().toString();
        final String snumber = Boothnumber.getText().toString();
        final String seats = totalseats.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Register_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Success = jsonObject.getBoolean("success");
                    Msg = jsonObject.getString("msg");
                    if (Success) {
                        Toast.makeText(getActivity(),Msg,Toast.LENGTH_LONG).show();
//                        fragment = new ZonalHomeScreen();
////                    getActivity().finish();
//
//                        getActivity().getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.fragment1, fragment)
//                                .commit();

                        getActivity().getSupportFragmentManager().popBackStack();
                        getActivity().getSupportFragmentManager().beginTransaction().commit();
                    }
                    else{
                        Toast.makeText(getActivity(),Msg,Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){



            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
//                        params.put("zonal_id", id);
                params.put("update",String.valueOf(true));
                params.put("b_id",String.valueOf(ID) );
                params.put("booth_name", sname);
                params.put("booth_number", snumber);
                params.put("totalseats", seats);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }



    private void download_Item()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DATA_URL+area,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(SignIn.this,response,Toast.LENGTH_LONG).show();
                        JSONArray jArray = null;
                        sampel.clear();
                        try {
                            jArray = new JSONArray(response);
                            if(jArray.length()>0) {
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    ji = new BoothModel();
                                    ji.setZ_id(json_data.getInt("b_id"));
                                    ji.setZ_name(json_data.getString("b_name"));
                                    Log.e("ID",":::::"+json_data.getInt("b_id"));
                                    Log.e("NMAE",":::::"+json_data.getString("b_name"));

                                    //  System.out.println(json_data.getString("district_name"));
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


    public final static boolean isValidPhoneNumber(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            if (target.length() < 10 || target.length() > 10) {
                return false;
            } else {
                return android.util.Patterns.PHONE.matcher(target).matches();
            }
        }
    }

    final static boolean isValidName(String name){
        if (name == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if(s == Boothnumber.getEditableText()){
            boolean num =   isValidPhoneNumber(Boothnumber.getText().toString());
            if(num==false){
                updateBooth.setBackground(this.getResources().getDrawable(R.drawable.invalidbutton));
            }
            else {
                updateBooth.setBackground(this.getResources().getDrawable(R.drawable.validbutton));
//            password.requestFocus();
            }
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


        ID = sampel.get(position).getZ_id();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        getDATA();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getDATA(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UPDATE_URL+ID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {


                    JSONObject jsonObject = new JSONObject(response);
                    Update = jsonObject.getBoolean("success");

                    progressDialog.dismiss();
                    if (Update) {
                        BoothName = jsonObject.getString("booth_name");
                        BoothNumber = jsonObject.getString("booth_number");
                        TotalSeats = jsonObject.getString("totalseats");
                        Boothname.setText(BoothName);
                        Boothnumber.setText(BoothNumber);
                        totalseats.setText(TotalSeats);
                    }
                    else{
                        Message = jsonObject.getString("msg");
                        Boothname.setText("");
                        Boothnumber.setText("");
                        totalseats.setText("");
                        Toast.makeText(getActivity(),Message, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
//


        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}
