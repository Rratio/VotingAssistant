package minorproject.votingassistant;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityManagerCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity implements TextWatcher {


    public Fragment frag;
    private EditText number;
    private Button verify;
    private TextView instruct;
    private RelativeLayout myLayout;
    private int RoleSelected;
    private String Msg,Name,nam, loginID, areaID, Area, ID,seats;
    CoordinatorLayout snackbarCoordinatorLayout;
    Snackbar snackbar;
    Bundle loginData;
    Boolean Success;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String  LoginURL = "http://bond-vehicles.000webhostapp.com/Voting/loginapi.php", Role = null, Number = null;
    String[] role = {"Election Commision Login", "DM Login", "SDM Login", "Zonal Login", "Booth Officers Login"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        verify = (Button) findViewById(R.id.verifynumber);
        Spinner login = (Spinner) findViewById(R.id.spinnerlogin);
        number = (EditText) findViewById(R.id.mobilenumber);
        instruct = (TextView) findViewById(R.id.instruct);
        Toolbar toolbar = (Toolbar) findViewById(R.id.logintoolbar);
        snackbarCoordinatorLayout = (CoordinatorLayout)findViewById(R.id.snackbarCoordinatorLayout);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.logo32);
        toolbar.setTitle("Voting Assistant");

        sharedPreferences = getSharedPreferences("LoginData",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        myLayout = (RelativeLayout) findViewById(R.id.loginLayout);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(myLayout.getWindowToken(), 0);

        String first = "Before Logging In, Make sure that you have  ";
        String name = "<font color='#0091E9'>Selected Your Role </font>";
        String remain = "and using the ";
        String sname = "<font color='#0091E9'>Registered Mobile Number.</font>";
        String rest = "<br> You will get an OTP to complete the Login.";
        instruct.setText(Html.fromHtml(first + name +remain + sname + rest));

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);





        login.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), "ROLE SELECTED:::" + position, Toast.LENGTH_LONG).show();


                RoleSelected = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, role);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        login.setAdapter(adapter);
//        Log.e("ADAPTER", "::::" + adapter);
        if (!role.equals(null)) {
            int spinnerPosition = adapter.getPosition(role);
            login.setSelection(spinnerPosition);
//            Log.e("SPINNER POSITION", "::::"+spinnerPosition);
        }

        number.addTextChangedListener(this);
        verify.addTextChangedListener(this);


        verify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                boolean valid = isValidPhoneNumber(number.getText());
                if (valid) {
                    switch (RoleSelected){
                        case 0: Role = "eclogin";
                                Number = "ec_number";
                                Name = "ec_name";
                                loginID = "ec_id";
                                login();
                            break;


                        case 1: Role = "dmlogin";
                            loginID = "dm_id";
                            areaID = "district_id";
                            Number = "dm_number";
                            Name = "dm_name";
                            login();
                            break;


                        case 2: Role = "sdmlogin";
                            loginID = "sdm_id";
                            areaID = "subdistrict_id";
                            Number = "sdm_number";
                            Name = "sdm_name";
                            login();
                            break;


                        case 3: Role = "zonallogin";
                            loginID = "zonal_id";
                            areaID = "z_id";
                            Number = "zonal_number";
                            Name = "zonal_name";
                            login();
                            break;


                        case 4: Role = "boothlogin";
                            loginID = "booth_id";
                            areaID = "b_id";
                            Number = "booth_number";
                            Name = "booth_name";
                            login();
                            break;
                    }

                }
                else{
                    snackbar = Snackbar
                            .make(snackbarCoordinatorLayout, "Enter Valid Number !", Snackbar.LENGTH_LONG);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(ContextCompat.getColor(snackBarView.getContext(), R.color.colorPrimaryDark));
//                          TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
//                          textView.setTextColor(ContextCompat.getColor(snackBarView.getContext(), R.color.colorAccent));
                    snackbar.show();
                }
            }
        });
    }







    public void login(){


          final String loginNumber = number.getText().toString();

          StringRequest stringRequest = new StringRequest(Request.Method.POST, LoginURL, new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {

                  try {
                      JSONObject jsonObject = new JSONObject(response);
                      Msg = jsonObject.getString("msg");
                      Success = jsonObject.getBoolean("success");

                      loginData = new Bundle();
//                      Toast.makeText(getApplicationContext(),"WELCOME:::"+nn,Toast.LENGTH_LONG).show();
                      if (Success){
                          switch (RoleSelected){
                              case 0: nam = jsonObject.getString(Name);
                                  ID = jsonObject.getString(loginID);
//                                  loginData.putString("ec_name",nam);
//                                  loginData.putString("ec_id",ID);
                                    editor.putString("ec_name",nam);
                                    editor.putString("ec_id",ID);
                                    editor.putInt("Role",RoleSelected);
                                    editor.putBoolean("IS_LOGIN", true);
                                  gotoOTP();
                                  break;

                              case 1: nam = jsonObject.getString(Name);
                                  ID = jsonObject.getString(loginID);
                                  Area = jsonObject.getString(areaID);
                                  editor.putString("dm_id",ID);
                                  editor.putString("dm_name",nam);
                                  editor.putString("district_id",Area);
                                  editor.putInt("Role",RoleSelected);
                                  editor.putBoolean("IS_LOGIN", true);
                                  gotoOTP();
                                  break;

                              case 2: nam = jsonObject.getString(Name);
                                  ID = jsonObject.getString(loginID);
                                  Area = jsonObject.getString(areaID);
                                  editor.putString("sdm_id",ID);
                                  editor.putString("sdm_name",nam);
                                  editor.putString("subdistrict_id",Area);
                                  editor.putInt("Role",RoleSelected);
                                  editor.putBoolean("IS_LOGIN", true);
                                  gotoOTP();
                                  break;

                              case 3: nam = jsonObject.getString(Name);
                                  ID = jsonObject.getString(loginID);
                                  Area = jsonObject.getString(areaID);
                                  editor.putString("zonal_id",ID);
                                  editor.putString("zonal_name",nam);
                                  editor.putString("z_id",Area);
                                  editor.putInt("Role",RoleSelected);
                                  editor.putBoolean("IS_LOGIN", true);
                                  gotoOTP();
                                  break;

                              case 4: nam = jsonObject.getString(Name);
                                  ID = jsonObject.getString(loginID);
                                  Area = jsonObject.getString(areaID);
                                  seats = jsonObject.getString("totalseats");
                                  editor.putString("booth_id",ID);
                                  editor.putString("booth_name",nam);
                                  editor.putString("b_id",Area);
                                  editor.putString("totalseats",seats);
                                  editor.putInt("Role",RoleSelected);
                                  editor.putBoolean("IS_LOGIN", true);
                                  gotoOTP();
                                  break;

                              default: Toast.makeText(getApplicationContext(),"Something is wrong. Sit tight :)", Toast.LENGTH_LONG).show();
                          }
                      }

                     else
                      {
                          snackbar = Snackbar
                                  .make(snackbarCoordinatorLayout, "Error: "+Msg, Snackbar.LENGTH_LONG);
                          View snackBarView = snackbar.getView();
                          snackBarView.setBackgroundColor(ContextCompat.getColor(snackBarView.getContext(), R.color.colorPrimaryDark));
//                          TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
//                          textView.setTextColor(ContextCompat.getColor(snackBarView.getContext(), R.color.colorAccent));
                          snackbar.show();

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


              @Override
              protected Map<String, String> getParams() throws AuthFailureError {
                  Map<String, String> params = new HashMap<String, String>();
                  params.put(Number, loginNumber);
                  params.put(Role,String.valueOf(true));
                  return params;
              }
          };

          RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
          requestQueue.add(stringRequest);




      }

      public void gotoOTP(){

          Intent intent = new Intent(getApplicationContext(), OTPActivity.class);
//          bundle.putInt("Role",RoleSelected);
//          intent.putExtras(bundle);
          editor.commit();
          startActivity(intent);
          finish();
      }
            


    /**
     * Validation of Phone Number
     */
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


    @Override
    public void onBackPressed() {
            Log.e("getBackStackEntryCount", ":::" + getSupportFragmentManager().getBackStackEntryCount());
            if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
                alertDialog();
            } else {
                super.onBackPressed();
            }

    }

    public boolean alertDialog() {
            final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Alert!");
            alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setMessage("Are you sure you want to exit?");

            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int arg1) {

                    dialog.dismiss();
//                    int pid = android.os.Process.myPid();
//                    android.os.Process.killProcess(pid);
                    finish();


                }
            });
            alertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int arg1) {

                    dialog.dismiss();

                }
            });

            android.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return true;
        }




    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {


        if (s == number.getEditableText()) {
            boolean valid = isValidPhoneNumber(number.getText().toString());
            if (valid == false) {
                verify.setBackground(this.getResources().getDrawable(R.drawable.invalidbutton));

            } else {
                verify.setBackground(this.getResources().getDrawable(R.drawable.validbutton));

            }

        }
    }
}

