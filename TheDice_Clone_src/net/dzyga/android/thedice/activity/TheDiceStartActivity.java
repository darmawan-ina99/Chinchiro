package net.dzyga.android.thedice.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import net.dzyga.android.homeadv.HomeAdv;
import net.dzyga.android.thedice.R;

/* loaded from: classes.dex */
public class TheDiceStartActivity extends Activity {
    protected static final String DICE_NUMBER = "Dices Number";
    private static final String TAG = "Start Activity";
    private Button btnOne;
    private Button btnThree;
    private Button btnTwo;
    private HomeAdv homeAd;
    Intent intent;

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.start);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.start_layout);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        if (displayMetrics.heightPixels > displayMetrics.widthPixels) {
            linearLayout.setOrientation(1);
        } else {
            linearLayout.setOrientation(0);
        }
        this.btnOne = (Button) findViewById(R.id.btn_one);
        this.btnTwo = (Button) findViewById(R.id.btn_two);
        this.btnThree = (Button) findViewById(R.id.btn_three);
        this.intent = new Intent(getBaseContext(), (Class<?>) TheDiceActivity.class);
        HomeAdv homeAdv = (HomeAdv) findViewById(R.id.adv_layout);
        this.homeAd = homeAdv;
        homeAdv.StartAd(" ");
        this.btnOne.setOnClickListener(new View.OnClickListener() { // from class: net.dzyga.android.thedice.activity.TheDiceStartActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TheDiceStartActivity.this.intent.putExtra(TheDiceStartActivity.DICE_NUMBER, 1);
                TheDiceStartActivity theDiceStartActivity = TheDiceStartActivity.this;
                theDiceStartActivity.startActivity(theDiceStartActivity.intent);
            }
        });
        this.btnTwo.setOnClickListener(new View.OnClickListener() { // from class: net.dzyga.android.thedice.activity.TheDiceStartActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TheDiceStartActivity.this.intent.putExtra(TheDiceStartActivity.DICE_NUMBER, 2);
                TheDiceStartActivity theDiceStartActivity = TheDiceStartActivity.this;
                theDiceStartActivity.startActivity(theDiceStartActivity.intent);
            }
        });
        this.btnThree.setOnClickListener(new View.OnClickListener() { // from class: net.dzyga.android.thedice.activity.TheDiceStartActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TheDiceStartActivity.this.intent.putExtra(TheDiceStartActivity.DICE_NUMBER, 3);
                TheDiceStartActivity theDiceStartActivity = TheDiceStartActivity.this;
                theDiceStartActivity.startActivity(theDiceStartActivity.intent);
            }
        });
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        this.homeAd.resume();
    }

    @Override // android.app.Activity
    public void onDestroy() {
        this.homeAd.destroy();
        super.onDestroy();
    }

    @Override // android.app.Activity
    public void onPause() {
        this.homeAd.pause();
        super.onPause();
    }
}
