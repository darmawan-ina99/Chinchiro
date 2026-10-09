package net.dzyga.android.homeadv;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import net.dzyga.android.thedice.BuildConfig;
import net.dzyga.android.thedice.R;

/* loaded from: classes.dex */
public class CheckInternetActivity extends Activity implements View.OnClickListener, DialogInterface.OnDismissListener {
    private Dialog dialog;
    private Intent inIntent;

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.check_internet);
        this.inIntent = getIntent();
        if (isNetworkAvailable()) {
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + this.inIntent.getStringExtra(HomeAdv.ADV_PACKAGE))));
            } catch (ActivityNotFoundException unused) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + this.inIntent.getStringExtra(HomeAdv.ADV_PACKAGE))));
            }
            finish();
            return;
        }
        Dialog dialog = new Dialog(this);
        this.dialog = dialog;
        dialog.setContentView(R.layout.dialog);
        this.dialog.setTitle(BuildConfig.FLAVOR);
        this.dialog.show();
        this.dialog.setOnDismissListener(this);
        Button button = (Button) this.dialog.findViewById(R.id.int_check_cancel);
        Button button2 = (Button) this.dialog.findViewById(R.id.int_check_settings);
        Button button3 = (Button) this.dialog.findViewById(R.id.try_again);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }

    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.int_check_cancel /* 2130968594 */:
                this.dialog.dismiss();
                finish();
                return;
            case R.id.int_check_settings /* 2130968595 */:
                startActivity(new Intent("android.settings.WIFI_SETTINGS"));
                return;
            case R.id.progress /* 2130968596 */:
            case R.id.start_layout /* 2130968597 */:
            default:
                return;
            case R.id.try_again /* 2130968598 */:
                Intent intent = new Intent(this, (Class<?>) CheckInternetActivity.class);
                intent.addFlags(268435456);
                intent.putExtra(HomeAdv.ADV_PACKAGE, this.inIntent.getStringExtra(HomeAdv.ADV_PACKAGE));
                startActivity(intent);
                this.dialog.dismiss();
                finish();
                return;
        }
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        finish();
    }
}
