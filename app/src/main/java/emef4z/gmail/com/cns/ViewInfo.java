package emef4z.gmail.com.cns;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class ViewInfo extends AppCompatActivity {

    ListView list_news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_info);

        getRemoteData(); //Views the information gotten from external DB

        list_news = (ListView) findViewById(R.id.list_news);


    }


    public void getRemoteData()
    {
        final ProgressDialog pd;
        pd = new ProgressDialog(ViewInfo.this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        final String url =
                Uri.parse("http://10.0.2.2/cns/cnsjson.php").buildUpon().build().toString();
        Ion.with(getBaseContext())
                .load(url)
                .progressDialog(pd)
                .as(new TypeToken<List<CnsJson>>() {})
                .setCallback(new FutureCallback<List<CnsJson>>() {
                    @Override
                    public void onCompleted(Exception e, List<CnsJson> result) {

                        if (result != null)
                        {
                            try {
                                cns_db DB = new cns_db(getBaseContext());

                                for (CnsJson r : result) {
                                    String comm = DB.insertInfo(r.title, r.date, r.details);
                                    Toast.makeText(getBaseContext(),comm,Toast.LENGTH_LONG).show();

                                }

                                String[] columns ={"title","date"};
                                int[] labels = {android.R.id.text1,android.R.id.text2};
                                Cursor myList = DB.getInfo();

                                SimpleCursorAdapter myAdapter = new SimpleCursorAdapter(getBaseContext(), android.R.layout.simple_expandable_list_item_2,
                                        myList,columns,labels);

                                list_news.setAdapter(myAdapter);


                            } catch (Exception ex) {

                                Toast.makeText(getApplicationContext(), "Error!!" + ex.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                        pd.dismiss();

                    }
                });
    }
}
