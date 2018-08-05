package minorproject.votingassistant.ECJava;

import android.content.Context;
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


public class DMAdd extends Fragment implements TextWatcher,Spinner.OnItemSelectedListener{


    private Spinner distict;
    private EditText DMname, DMnumber;
    private Button addDM;
    public String DATA_URL="http://bond-vehicles.000webhostapp.com/Voting/spinnerapi.php?all=true";
    public String Register_url="http://bond-vehicles.000webhostapp.com/Voting/addapi.php";
    private String Msg;
    Fragment fragment;
    public DmAdapter adapter;
    private boolean nv, fv, Success;
    DmModel ji;
    int ID = 0;
    public ArrayList<DmModel>sampel=new ArrayList<DmModel>();
   // String[] sample = {"Bhilwara", "Udaipur", "Jaipur"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dmadd, container, false);



        Bundle b = getArguments();
        if(b!=null) {
            String name = b.getString("ec_name");

        }

        distict = (Spinner) view.findViewById(R.id.district);
        DMname = (EditText) view.findViewById(R.id.name);
        DMnumber = (EditText) view.findViewById(R.id.number);
        addDM = (Button) view.findViewById(R.id.addDM);

        adapter = new DmAdapter(getActivity(), sampel);
        distict.setAdapter(adapter);




        distict.setOnItemSelectedListener(this);


//       SPINNER VALUES



        DMname.addTextChangedListener(this);
        DMnumber.addTextChangedListener(this);


        //        ADD BUTTON CLICK EVENT

        addDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                  nv = isValidPhoneNumber(DMnumber.getText().toString());
                fv = isValidName(DMname.getText().toString());

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
        final String sname = DMname.getText().toString();
        final String snumber = DMnumber.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Register_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Success = jsonObject.getBoolean("success");
                    Msg = jsonObject.getString("msg");
                    if (Success) {
                        Toast.makeText(getActivity(),Msg,Toast.LENGTH_LONG).show();
//                        fragment = new ECHomeScreen();
////                    getActivity().finish();
//
//                        getActivity().getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.fragment1, fragment)
//                                .commit();

                        getActivity().getSupportFragmentManager().popBackStack();
                        getActivity().getSupportFragmentManager().beginTransaction().commit();
                    } else {

                        Toast.makeText(getActivity(),Msg,Toast.LENGTH_LONG).show();
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

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("district_id", String.valueOf(ID));
                params.put("dm_name", sname);
                params.put("dm_number", snumber);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


    //Volly <code></code>


    private void download_Item()
    {
         StringRequest stringRequest = new StringRequest(Request.Method.POST, DATA_URL,
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
                                    ji = new DmModel();
                                    ji.setDistrict_id(json_data.getInt("district_id"));
                                    ji.setDistrict_name(json_data.getString("district_name"));
                                    Log.e("ID",":::::"+json_data.getInt("district_id"));

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

    final static boolean isValidName(String name) {
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

        if (s == DMnumber.getEditableText()) {
            boolean num = isValidPhoneNumber(DMnumber.getText().toString());
            if (num == false) {
                addDM.setBackground(this.getResources().getDrawable(R.drawable.invalidbutton));
            } else {
                addDM.setBackground(this.getResources().getDrawable(R.drawable.validbutton));
//            password.requestFocus();
            }
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ID = sampel.get(i).getDistrict_id();
//        Toast.makeText(getActivity(),"CHUTIYA"+ID,Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}





