package minorproject.votingassistant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import minorproject.votingassistant.ECJava.DmModel;

import static android.content.Context.MODE_PRIVATE;


public class BoothHome extends Fragment implements TextWatcher{


    public String Register_url="http://bond-vehicles.000webhostapp.com/Voting/boothapi.php";
    public String DATA_url="http://bond-vehicles.000webhostapp.com/Voting/getapi.php";
    public String GET_url="http://bond-vehicles.000webhostapp.com/Voting/boothapi.php?time=true";
    private TextView boothno, totalvotes, time;
    private EditText status;
    private Button send;
    String name, id,booth,Msg,totalseats,percentage,seats;
    Boolean Success;
    Bundle data;
    String timedata;
    int hour, min;
    SharedPreferences sharedPreferences;


    private MenuItem logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_booth_home, container, false);


        sharedPreferences = getActivity().getSharedPreferences("LoginData",MODE_PRIVATE);

        if(sharedPreferences!=null){
            name = sharedPreferences.getString("booth_name",null);
            totalseats = sharedPreferences.getString("totalseats",null);
            booth = sharedPreferences.getString("booth_id",null);
        }



            boothno = (TextView) view.findViewById(R.id.boothno);
            totalvotes = (TextView) view.findViewById(R.id.total);
            status = (EditText) view.findViewById(R.id.boothvotes);
            send = (Button) view.findViewById(R.id.send);
            logout = (MenuItem) view.findViewById(R.id.logout);
            time = (TextView) view.findViewById(R.id.time);

//        Integer hour = timePicker.getCurrentHour();
//        Integer min = timePicker.getCurrentMinute();

//        calendar = Calendar.getInstance();
//        int hour = calendar.getTime().getHours();
//        int min = calendar.getTime().getMinutes();
//
//        timedata = hour+":"+min;


        time.setText(timedata);
        totalvotes.setText(totalseats);
        boothno.setText(booth);


            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);



                    seats = status.getText().toString();
                    Double per = Double.valueOf(seats)/Double.valueOf(totalseats);
                    percentage = String.valueOf(per*100);

                    Log.e("PERCENTAGE","____"+percentage);
                    Log.e("SEATS","____"+seats);
                    int value = seats.compareTo(totalseats);



                    if(value<=0) {


                        download_Item();

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Register_url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {

                                    JSONObject jsonObject = new JSONObject(response);
                                    Success = jsonObject.getBoolean("success");
                                    Msg = jsonObject.getString("msg");

                                    if (Success) {
                                        Toast.makeText(getActivity(), Msg, Toast.LENGTH_LONG).show();
                                        getActivity().getSupportFragmentManager().popBackStack();
                                        getActivity().getSupportFragmentManager().beginTransaction().commit();
                                    } else {
                                        Toast.makeText(getActivity(), "Something Unexpected Happened !", Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {


                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("data", String.valueOf(true));
                                params.put("booth_id", booth);
                                params.put("status", seats);
                                params.put("percentage",percentage);

                                return params;
                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                        requestQueue.add(stringRequest);

                    }else{
                        Toast.makeText(getActivity(),"Seats can't be higher than total voters.",Toast.LENGTH_LONG).show();
                    }
                }


            });

        time_item();



        return view;
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("BoothHomeScreen");
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            Toast.makeText(getActivity(), "back to Login", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);


    }

    private void download_Item()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DATA_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                                    Success = jsonObject.getBoolean("success");
                                    Msg = jsonObject.getString("msg");

                                    if (Success) {
                                        Toast.makeText(getActivity(), Msg, Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getActivity(), "Something Unexpected Happened !", Toast.LENGTH_LONG).show();
                                    }

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
                params.put("update", String.valueOf(true));
                params.put("booth_id", booth);
                params.put("time", timedata);
                params.put("totalseats",totalseats);
                params.put("seats",seats);
                params.put("percentage",percentage);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

public void time_item() {
    StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);

               hour = jsonObject.getInt("hour");
               min = jsonObject.getInt("min");
               if(hour>12){
                   hour = hour - 12;
               }
                timedata = hour+":"+min;
               time.setText(timedata);

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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
