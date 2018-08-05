package minorproject.votingassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BoothActivity extends AppCompatActivity {

    private TextView boothno, totalseats;
    private EditText noofvotes;;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booth);

        boothno = (TextView) findViewById(R.id.boothno);
        totalseats = (TextView) findViewById(R.id.total);
        noofvotes = (EditText) findViewById(R.id.boothvotes);
        send = (Button) findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }
}
