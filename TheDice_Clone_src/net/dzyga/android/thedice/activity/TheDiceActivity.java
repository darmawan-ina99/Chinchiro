package net.dzyga.android.thedice.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import java.util.Random;
import net.dzyga.android.homeadv.HomeAdv;
import net.dzyga.android.thedice.R;
import net.dzyga.android.thedice.model.Const;

/* loaded from: classes.dex */
public class TheDiceActivity extends Activity {
    private static final int ANIM_ROTATION = 200;
    private static final String AWAKE = "Awake";
    private static final String PREFS = "Dice preferences";
    protected static final String TAG = "TheDiceActivity";
    private Animation animHide;
    private Animation animShow;
    private Context context;
    private ImageView[] dice;
    private ImageView[] diceA;
    private LinearLayout diceLayout;
    private int diceNumber;
    private RelativeLayout diceThreeLay;
    private RelativeLayout diceTwoLay;
    private HomeAdv homeAd;
    private PowerManager pm;
    private int[] randArr;
    private SharedPreferences settings;
    private boolean[] stopped;
    private Tick200mils tick200;
    private PowerManager.WakeLock wl;
    private int secCounter = 0;
    private boolean awake = false;
    private boolean countdownFinished = false;

    static /* synthetic */ int access$108(TheDiceActivity theDiceActivity) {
        int i = theDiceActivity.secCounter;
        theDiceActivity.secCounter = i + 1;
        return i;
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dice);
        this.settings = getSharedPreferences(PREFS, 0);
        this.context = getApplicationContext();
        this.diceLayout = (LinearLayout) findViewById(R.id.dice_layout);
        CheckBox checkBox = (CheckBox) findViewById(R.id.check_awake);
        SharedPreferences sharedPreferences = this.settings;
        if (sharedPreferences != null) {
            this.awake = sharedPreferences.getBoolean(AWAKE, false);
        }
        checkBox.setChecked(this.awake);
        PowerManager powerManager = (PowerManager) getSystemService("power");
        this.pm = powerManager;
        this.wl = powerManager.newWakeLock(10, " ");
        int intExtra = getIntent().getIntExtra("Dices Number", 1);
        this.diceNumber = intExtra;
        this.dice = new ImageView[intExtra];
        this.diceA = new ImageView[intExtra];
        this.randArr = new int[intExtra];
        this.stopped = new boolean[intExtra];
        this.animHide = AnimationUtils.loadAnimation(this.context, R.anim.hide_left);
        this.animShow = AnimationUtils.loadAnimation(this.context, R.anim.show_right);
        this.diceTwoLay = (RelativeLayout) findViewById(R.id.dice_two_layout);
        this.diceThreeLay = (RelativeLayout) findViewById(R.id.dice_three_layout);
        this.dice[0] = (ImageView) findViewById(R.id.dice1);
        this.diceA[0] = (ImageView) findViewById(R.id.dice1a);
        ImageView[] imageViewArr = this.dice;
        if (imageViewArr.length > 1) {
            imageViewArr[1] = (ImageView) findViewById(R.id.dice2);
            this.diceA[1] = (ImageView) findViewById(R.id.dice2a);
            this.dice[1].setVisibility(0);
            this.diceTwoLay.setVisibility(0);
        }
        ImageView[] imageViewArr2 = this.dice;
        if (imageViewArr2.length > 2) {
            imageViewArr2[2] = (ImageView) findViewById(R.id.dice3);
            this.diceA[2] = (ImageView) findViewById(R.id.dice3a);
            this.dice[2].setVisibility(0);
            this.diceThreeLay.setVisibility(0);
        }
        HomeAdv homeAdv = (HomeAdv) findViewById(R.id.adv_layout);
        this.homeAd = homeAdv;
        homeAdv.StartAd(" ");
        this.tick200 = new Tick200mils();
        this.diceLayout.setOnClickListener(new View.OnClickListener() { // from class: net.dzyga.android.thedice.activity.TheDiceActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TheDiceActivity.this.secCounter = 5;
                Random random = new Random();
                int i = 0;
                while (i < TheDiceActivity.this.diceNumber) {
                    TheDiceActivity.this.randArr[i] = random.nextInt(6);
                    StringBuilder sb = new StringBuilder();
                    int i2 = i + 1;
                    sb.append(i2);
                    sb.append(" random number is ");
                    sb.append(TheDiceActivity.this.randArr[i] + 1);
                    Log.i(TheDiceActivity.TAG, sb.toString());
                    i = i2;
                }
                for (int i3 = 0; i3 < TheDiceActivity.this.dice.length; i3++) {
                    TheDiceActivity.this.dice[i3].setImageResource(Const.DICE_IMG[TheDiceActivity.this.secCounter]);
                    if (TheDiceActivity.this.secCounter != 5) {
                        TheDiceActivity.this.diceA[i3].setImageResource(Const.DICE_IMG[TheDiceActivity.this.secCounter + 1]);
                    } else {
                        TheDiceActivity.this.diceA[i3].setImageResource(Const.DICE_IMG[0]);
                    }
                    TheDiceActivity.this.dice[i3].setVisibility(0);
                    TheDiceActivity.this.dice[i3].setAnimation(TheDiceActivity.this.animHide);
                    TheDiceActivity.this.diceA[i3].setAnimation(TheDiceActivity.this.animShow);
                    TheDiceActivity.this.diceA[i3].setVisibility(0);
                    TheDiceActivity.this.dice[i3].startAnimation(TheDiceActivity.this.animHide);
                    TheDiceActivity.this.diceA[i3].startAnimation(TheDiceActivity.this.animShow);
                    TheDiceActivity.this.stopped[i3] = false;
                }
                TheDiceActivity.this.dice[0].removeCallbacks(TheDiceActivity.this.tick200);
                TheDiceActivity.this.dice[0].postDelayed(TheDiceActivity.this.tick200, 200L);
                TheDiceActivity.this.countdownFinished = false;
                TheDiceActivity.this.diceLayout.removeCallbacks(null);
                TheDiceActivity.this.diceLayout.postDelayed(new Runnable() { // from class: net.dzyga.android.thedice.activity.TheDiceActivity.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TheDiceActivity.this.countdownFinished = true;
                    }
                }, 3000L);
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: net.dzyga.android.thedice.activity.TheDiceActivity.2
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                SharedPreferences.Editor edit;
                TheDiceActivity.this.awake = z;
                if (TheDiceActivity.this.awake) {
                    TheDiceActivity.this.wl.acquire();
                } else {
                    TheDiceActivity.this.wl.release();
                }
                if (TheDiceActivity.this.settings == null || (edit = TheDiceActivity.this.settings.edit()) == null) {
                    return;
                }
                edit.putBoolean(TheDiceActivity.AWAKE, TheDiceActivity.this.awake);
                edit.commit();
            }
        });
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        if (this.awake) {
            this.wl.acquire();
        }
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
        if (this.awake) {
            this.wl.release();
        }
        super.onPause();
    }

    /* loaded from: classes.dex */
    private class Tick200mils implements Runnable {
        private Tick200mils() {
        }

        @Override // java.lang.Runnable
        public void run() {
            TheDiceActivity.access$108(TheDiceActivity.this);
            if (TheDiceActivity.this.secCounter >= 6) {
                TheDiceActivity.this.secCounter = 0;
            }
            boolean z = false;
            for (int i = 0; i < TheDiceActivity.this.dice.length; i++) {
                if (TheDiceActivity.this.countdownFinished && TheDiceActivity.this.secCounter == TheDiceActivity.this.randArr[i]) {
                    TheDiceActivity.this.stopped[i] = true;
                }
                TheDiceActivity.this.dice[i].clearAnimation();
                TheDiceActivity.this.diceA[i].clearAnimation();
                if (!TheDiceActivity.this.stopped[i]) {
                    TheDiceActivity.this.dice[i].setImageResource(Const.DICE_IMG[TheDiceActivity.this.secCounter]);
                    if (TheDiceActivity.this.secCounter != 5) {
                        TheDiceActivity.this.diceA[i].setImageResource(Const.DICE_IMG[TheDiceActivity.this.secCounter + 1]);
                    } else {
                        TheDiceActivity.this.diceA[i].setImageResource(Const.DICE_IMG[0]);
                    }
                    TheDiceActivity.this.dice[i].startAnimation(TheDiceActivity.this.animHide);
                    TheDiceActivity.this.diceA[i].startAnimation(TheDiceActivity.this.animShow);
                    z = true;
                }
            }
            TheDiceActivity.this.dice[0].removeCallbacks(TheDiceActivity.this.tick200);
            if (z) {
                TheDiceActivity.this.dice[0].postDelayed(TheDiceActivity.this.tick200, 200L);
            }
        }
    }
}
