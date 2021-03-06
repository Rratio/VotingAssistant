package minorproject.votingassistant.DMJava;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
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


public class SDMUpdate extends Fragment implements TextWatcher,Spinner.OnItemSelectedListener {


    private Spinner area;
    private EditText SDMname, SDMnumber;
    private Button updateSDM;
    Fragment fragment;
    public String DATA_URL="http://bond-vehicles.000webhostapp.com/Voting/spinnerapi.php?district_id=";
    public String Register_url="http://bond-vehicles.000webhostapp.com/Voting/sdmapi.php";
    public String UPDATE_URL="http://bond-vehicles.000webhostapp.com/Voting/updateapi.php?subdistrict_id=";
    public SDMadapter adapter;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    private boolean nv, fv,Success,Update;
    SDMmodel model;
    String id, name, dmID,Msg,Message, SdmName,SdmNumber;
    int ID =0;
    public ArrayList<SDMmodel> sample=new ArrayList<SDMmodel>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_sdmupdate, container, false);

        sharedPreferences = getActivity().getSharedPreferences("LoginData",MODE_PRIVATE);

        if(sharedPreferences!=null){
            name = sharedPreferences.getString("dm_name",null);
            dmID = sharedPreferences.getString("dm_id",null);
            id = sharedPreferences.getString("district_id",null);
        }
//        Toast.makeText(getActivity(),"Name------"+name,Toast.LENGTH_LONG).show();
//        Toast.makeText(getActivity(),"ID------"+id,Toast.LENGTH_LONG).show();

        area = (Spinner) view.findViewById(R.id.district);
        SDMname = (EditText) view.findViewById(R.id.update_sdmname);
        SDMnumber = (EditText) view.findViewById(R.id.update_sdmnumber);
        updateSDM = (Button) view.findViewById(R.id.updateSDM);


        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.logintoolbar);

        adapter = new SDMadapter(getActivity(), sample);
        area.setAdapter(adapter);

        area.setOnItemSelectedListener(this);


        SDMname.addTextChangedListener(this);
        SDMnumber.addTextChangedListener(this);




        updateSDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);



                nv = isValidPhoneNumber(SDMnumber.getText().toString());
                fv = isValidName(SDMname.getText().toString());

                if (nv && fv) {
                    callAPI();
                }
                else{
                    Toast.makeText(getActivity(),"Enter Valid Number !",Toast.LENGTH_LONG).show();
                }
            }
        });
        download_Item();

        return view;
    }

    private void callAPI(){
        final String sname = SDMname.getText().toString();
        final String snumber = SDMnumber.getText().toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Register_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Success = jsonObject.getBoolean("success");
                    Msg = jsonObject.getString("msg");
                    if (Success) {

                        Toast.makeText(getActivity(),Msg,Toast.LENGTH_LONG).show();
//                        fragment = new DMHomeScreen();
////                    getActivity().finish();
//
//                        getActivity().getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.fragment1, fragment)
//                                .commit();

                        getActivity().getSupportFragmentManager().popBackStack();
                        getActivity().getSupportFragmentManager().beginTransaction().commit();
                    }
//
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
                params.put("subdistrict_id", String.valueOf(ID));
                params.put("update",String.valueOf(true));
                params.put("dm_id",dmID);
                params.put("sdm_name", sname);
                params.put("sdm_number", snumber);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }


    private void download_Item()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DATA_URL+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(SignIn.this,response,Toast.LENGTH_LONG).show();
                        JSONArray jArray = null;
                        sample.clear();
                        try {
                            jArray = new JSONArray(response);
                            if(jArray.length()>0) {
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    model = new SDMmodel();
                                    model.setSubdistrict_id(json_data.getInt("subdistrict_id"));
                                    model.setSubdistrict_name(json_data.getString("subdistrict_name"));
                                    Log.e("ID",":::::"+json_data.getInt("subdistrict_id"));
                                    Log.e("NAME",":::::"+json_data.getString("subdistrict_name"));

                                    //  System.out.println(json_data.getString("district_name"));
                                    sample.add(model);


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

        if(s == SDMnumber.getEditableText()){
            boolean num =   isValidPhoneNumber(SDMnumber.getText().toString());
            if(num==false){
                updateSDM.setBackground(this.getResources().getDrawable(R.drawable.invalidbutton));
            }
            else {
                updateSDM.setBackground(this.getResources().getDrawable(R.drawable.validbutton));
//            password.requestFocus();
            }
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


        ID = sample.get(i).getSubdistrict_id();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        getDATA();
        Log.e("SUB::",":::"+ID);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
                        SdmName = jsonObject.getString("sdm_name");
                        SdmNumber = jsonObject.getString("sdm_number");
                        Log.e("NAME",":::::"+SdmName);
                        SDMname.setText(SdmName);
                        SDMnumber.setText(SdmNumber);
                    }
                    else{
                        Message = jsonObject.getString("msg");
                        SDMname.setText("");
                        SDMnumber.setText("");
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

