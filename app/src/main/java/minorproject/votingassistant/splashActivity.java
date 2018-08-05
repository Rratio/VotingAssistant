package minorproject.votingassistant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class splashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    SharedPreferences sharedPreferences;
    Intent main, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = getSharedPreferences("LoginData", Context.MODE_PRIVATE);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sharedPreferences.getBoolean("IS_LOGIN",false)){
                    if(isOnline(getApplicationContext())) {
                        int Role = sharedPreferences.getInt("Role",0);
                        switch (Role){
                            case 0: main = new Intent(getApplicationContext(), ECActivity.class);
                                break;

                            case 1: main = new Intent(getApplicationContext(),DMActivity.class);
                                break;

                            case 2: main = new Intent(getApplicationContext(),SDMActivity.class);
                                break;

                            case 3: main = new Intent(getApplicationContext(),ZonalActivity.class);
                                break;

                            case 4: main = new Intent(getApplicationContext(),Navigation.class);
                                break;
                        }
                        startActivity(main);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"Please check your internet connection!",Toast.LENGTH_LONG).show();
                    }
                }else {
                     login = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(login);
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);

    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }

}
