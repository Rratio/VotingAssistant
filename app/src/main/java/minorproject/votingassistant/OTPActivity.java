package minorproject.votingassistant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class OTPActivity extends AppCompatActivity implements TextWatcher{


    private EditText otpnumber;
    Button login;
    private RelativeLayout OtpLayout;
    String id;
    TextView resend;
    Bundle data;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        otpnumber = (EditText) findViewById(R.id.otp);
//        otpnumber.requestFocus();
        login = (Button) findViewById(R.id.button);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        resend = (TextView) findViewById(R.id.resend);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Verify Number");
        toolbar.setNavigationIcon(R.drawable.evlogo_icon);

        sharedPreferences = getSharedPreferences("LoginData",MODE_PRIVATE);



        String first = "If you have not received an One Time Password, then you can ";
        String sname = "<font color='#0091E9'>Request a new one.</font>";
        resend.setText(Html.fromHtml(first + sname));

        otpnumber.addTextChangedListener(this);
        login.addTextChangedListener(this);

        OtpLayout = (RelativeLayout) findViewById(R.id.otplayout);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(otpnumber.getWindowToken(), 0);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);


        Random random = new Random();
        final String otp = String.format("%06d", random.nextInt(1000000));
        Toast.makeText(this,"Enter OTP::"+otp,Toast.LENGTH_LONG).show();
        otpnumber.setText(otp);

//        data = getIntent().getExtras();
//         Name = data.getString("dm_name");
//        id = data.getString("dm_id");
//        area = data.getString("district_id");
//        Toast.makeText(getApplicationContext(),"Name: "+Name+ " ID: "+id+" "+area, Toast.LENGTH_LONG).show();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean valid = isValidOtp(otpnumber.getText());
                Intent intent = null;
                if(valid) {
                    if (otp.equals( otpnumber.getText().toString())) {
//                       int key = getIntent().getExtras().getInt("Role");

                        int key = sharedPreferences.getInt("Role",0);
//                        Toast.makeText(getApplicationContext(),Name,Toast.LENGTH_LONG).show();
                        switch (key){
                            case 0:  intent = new Intent(getApplicationContext(), ECActivity.class);
                                break;
                            case 1: intent = new Intent(getApplicationContext(), DMActivity.class);
                                break;
                            case 2: intent = new Intent(getApplicationContext(), SDMActivity.class);
                                break;
                            case 3: intent = new Intent(getApplicationContext(), ZonalActivity.class);
                                break;
                            case 4: intent = new Intent(getApplicationContext(), Navigation.class);
                                break;
                        }
//
//                        intent.putExtras(data);
                        startActivity(intent);
                        finish();

//                    Fragment fragment = new ECHomeScreen();
//
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.fragment1, fragment);
//                    fragmentTransaction.commit();
                    }
                }
                else{

                    }

            }
        });




    }


//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        //you can set the title for your toolbar here for different fragments different titles
//        getActivity().setTitle("OTP");
//    }


    /**
     * Validation of Phone Number
     */
    public final static boolean isValidOtp(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            if (target.length() < 6 || target.length() > 6) {
                return false;
            } else {
                return android.util.Patterns.PHONE.matcher(target).matches();
            }
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

        if (s == otpnumber.getEditableText()) {
            boolean valid =  isValidOtp(otpnumber.getText().toString());
            if (!valid) {
                login.setBackground(this.getResources().getDrawable(R.drawable.invalidbutton));

            } else {
                login.setBackground(this.getResources().getDrawable(R.drawable.validbutton));

            }

        }

    }
}

