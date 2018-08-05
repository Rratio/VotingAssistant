package minorproject.votingassistant.SDMJava;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
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

import minorproject.votingassistant.DMJava.SDMadapter;
import minorproject.votingassistant.DMJava.SDMmodel;
import minorproject.votingassistant.R;

import static android.content.Context.MODE_PRIVATE;


public class AddZonal extends Fragment implements TextWatcher, Spinner.OnItemSelectedListener {
   private TextInputLayout autoname,autonumber;
    private EditText zonalname,zonalnumber;
    private Button zonalsubmit;
    Fragment fragment;
    private String[] addzonal;
    public String DATA_URL="http://bond-vehicles.000webhostapp.com/Voting/spinnerapi.php?subdistrict_id=";
    public String Register_url="http://bond-vehicles.000webhostapp.com/Voting/zonalapi.php";
    public ZonalAdapter adapter;
    private boolean nv, fv,Success;
    ZonalModel model;
    String id, name, sdmID,Msg;
    Spinner spinner;
    int ID =0;
    SharedPreferences sharedPreferences;
    public ArrayList<ZonalModel> sample=new ArrayList<ZonalModel>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_add_zonal, container, false);

        sharedPreferences = getActivity().getSharedPreferences("LoginData",MODE_PRIVATE);

        if(sharedPreferences!=null){
            name = sharedPreferences.getString("sdm_name",null);
            sdmID = sharedPreferences.getString("sdm_id",null);
            id = sharedPreferences.getString("subdistrict_id",null);
        }



      spinner= (Spinner) view.findViewById(R.id.zonalarea);
        autoname= (TextInputLayout) view.findViewById(R.id.textinputzonalname);
        autonumber= (TextInputLayout) view.findViewById(R.id.textinputzonalnumber);
        zonalname= (EditText) view.findViewById(R.id.name);
        zonalnumber= (EditText) view.findViewById(R.id.number);
        zonalsubmit= (Button) view.findViewById(R.id.add);



        zonalname.addTextChangedListener(this);
        zonalnumber.addTextChangedListener(this);

        adapter = new ZonalAdapter(getActivity(), sample);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);



        //        ADD BUTTON CLICK EVENT

        zonalsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);



                nv = isValidPhoneNumber(zonalnumber.getText().toString());
                fv = isValidName(zonalname.getText().toString());

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
        final String sname = zonalname.getText().toString();
        final String snumber = zonalnumber.getText().toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Register_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Success = jsonObject.getBoolean("success");
                    Msg = jsonObject.getString("msg");
                    if (Success) {
                        Toast.makeText(getActivity(),Msg,Toast.LENGTH_LONG).show();
//                        fragment = new SDMHomeScreen();
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
                params.put("z_id", String.valueOf(ID));
                params.put("sdm_id",sdmID);
                params.put("zonal_name", sname);
                params.put("zonal_number", snumber);
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
                                    model = new ZonalModel();
                                    model.setZ_id(json_data.getInt("z_id"));
                                    model.setZ_name(json_data.getString("z_name"));
                                    Log.e("ID",":::::"+json_data.getInt("z_id"));
                                    Log.e("NAME",":::::"+json_data.getString("z_name"));

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

        if(s == zonalnumber.getEditableText()){
            boolean num =   isValidPhoneNumber(zonalnumber.getText().toString());
            if(num==false){
                zonalsubmit.setBackground(this.getResources().getDrawable(R.drawable.invalidbutton));
            }
            else {
                zonalsubmit.setBackground(this.getResources().getDrawable(R.drawable.validbutton));
//            password.requestFocus();
            }
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ID = sample.get(position).getZ_id();
        Log.e("SUB::",":::"+ID);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}































































































































































































































































































































































































































































































































































































