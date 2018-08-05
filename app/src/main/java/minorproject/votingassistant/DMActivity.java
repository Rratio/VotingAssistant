package minorproject.votingassistant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import minorproject.votingassistant.DMJava.DMHomeScreen;

public class DMActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    String name;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);



//        Bundle loginData = getIntent().getExtras();
//
//        String name = loginData.getString("dm_name");
//        Toast.makeText(getApplicationContext(),"name:::"+name,Toast.LENGTH_LONG).show();


        DMHomeScreen home = new DMHomeScreen();


        sharedPreferences = getSharedPreferences("LoginData",MODE_PRIVATE);

        if(sharedPreferences!=null){
            name = sharedPreferences.getString("dm_name",null);
            Toast.makeText(getApplicationContext(), "Welcome ! " + name, Toast.LENGTH_LONG).show();
        }


        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.fragment1, home);
        tx.commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        int count = getFragmentManager().getBackStackEntryCount();

        if (count <=-1) {
//            super.onBackPressed();
            Intent intent = new Intent(DMActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.logout) {
//            Toast.makeText(getApplicationContext(), "back to Login", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }


    private void displayScreen(int itemId) {

        Fragment fragment = null;

        switch (itemId) {

            case R.id.home:

                fragment = new DMHomeScreen();
                break;



            case R.id.add_election:

                fragment = new AddElection();
                break;

            case R.id.rule_book:

                fragment = new RuleBook();
                break;

            case R.id.report_problem:

                fragment = new ReportProblem();
                break;


            default:
                fragment = new DMHomeScreen();
                break;

        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment1, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    public boolean onNavigationItemSelected(MenuItem item) {
        displayScreen(item.getItemId());
        return true;
    }
}
