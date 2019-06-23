package emef4z.gmail.com.cns;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class MainActivity extends AppCompatActivity {

    EditText title_txt,date_txt,details_txt;
    Button reg,view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title_txt = (EditText) findViewById(R.id.title_txt);
        date_txt = (EditText) findViewById(R.id.date_txt);
        details_txt = (EditText) findViewById(R.id.details_txt);

        reg = (Button) findViewById(R.id.reg_btn);
        view = (Button) findViewById(R.id.viewInfo_btn);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = title_txt.getText().toString();
                String date = date_txt.getText().toString();
                String details = details_txt.getText().toString();
                final ProgressDialog pd;
                pd = new ProgressDialog(MainActivity.this);
                pd.setMessage("Sending Data...");
                pd.setCancelable(false);
                pd.show();
                final String url;

                url = Uri.parse("http://10.0.2.2/cns/data_insertion.php").buildUpon()
                        .appendQueryParameter("title",title)
                        .appendQueryParameter("date",date)
                        .appendQueryParameter("details",details)
                        .build().toString();

                Ion.with(getBaseContext())
                        .load(url)
                        .progressDialog(pd)
                        .asString()
                        .setCallback(new FutureCallback<String>() {
                            @Override
                            public void onCompleted(Exception e, String result) {

                                try
                                {
                                    Toast.makeText(getBaseContext(),result,Toast.LENGTH_SHORT).show();
                                }
                                catch (Exception ex)
                                {
                                    Toast.makeText(getBaseContext(),"Error! " + ex.getMessage() + "No connectivity",Toast.LENGTH_SHORT).show();
                                }
                                pd.dismiss();
                            }
                        });


            }
        });



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(),ViewInfo.class);
                startActivity(myIntent);
            }
        });


    }
}
