package com.chenzhihuiiiii.oneplusservice;


import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onResume() {
        super.onResume();
        boolean isAccessbilityServiceRunning = Utils.isStartAccessibilityService(getApplicationContext(), OneplusInstallerHelperService.class.getSimpleName());
        if (!isAccessbilityServiceRunning) {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle(R.string.attention).setMessage(R.string.please_open_service)
                    .setPositiveButton(getString(R.string.open_settings), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                        }
                    })
                    .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .create();
            alertDialog.show();
        }
    }
}
