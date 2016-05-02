package liu.com.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import liu.com.mobilesafe.R;


public class AToolsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atools);
    }


    public void addressQuery(View view) {

        startActivity(new Intent(this,AddressQueryActivity.class));
    }
}
