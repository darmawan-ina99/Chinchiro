package net.dzyga.android.homeadv;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Random;
import net.dzyga.android.thedice.R;

/* loaded from: classes.dex */
public class HomeAdv extends LinearLayout implements View.OnClickListener, Animation.AnimationListener {
    public static final String ADV_PACKAGE = "AdvPackage";
    private static final String TAG = "HomeAdv";
    private boolean advGoUp;
    private ImageView advImage;
    private LinearLayout advLayout;
    private TextView advText;
    private String[] advTextArray;
    private Animation animSlideDown;
    private Animation animSlideUp;
    private String[] appPackageNames;
    private Context ctx;
    private int currentAd;
    private TimerTick timer;

    /* JADX INFO: Access modifiers changed from: private */
    public void admobRequest() {
    }

    public void destroy() {
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationRepeat(Animation animation) {
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationStart(Animation animation) {
    }

    public void pause() {
    }

    public void resume() {
    }

    public HomeAdv(Context context) {
        super(context);
        this.currentAd = 0;
        this.advGoUp = true;
        construct(context);
    }

    public HomeAdv(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.currentAd = 0;
        this.advGoUp = true;
        construct(context);
    }

    public HomeAdv(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.currentAd = 0;
        this.advGoUp = true;
        construct(context);
    }

    private void construct(Context context) {
        View.inflate(context, R.layout.home_ad, this);
        this.advImage = (ImageView) findViewById(R.id.home_ad_image);
        this.advText = (TextView) findViewById(R.id.home_ad_text);
        this.advLayout = (LinearLayout) findViewById(R.id.home_ad_layout);
        this.animSlideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        this.animSlideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        this.advTextArray = getResources().getStringArray(R.array.home_ad_text);
        this.appPackageNames = getResources().getStringArray(R.array.home_ad_links);
        this.ctx = context;
    }

    public void StartAd(String str) {
        this.currentAd = new Random().nextInt(this.advTextArray.length);
        this.advImage.setImageResource(Constants.adv_images[this.currentAd]);
        this.advText.setText(this.advTextArray[this.currentAd]);
        this.advLayout.setOnClickListener(this);
        this.animSlideUp.setAnimationListener(this);
        this.timer = new TimerTick();
        admobRequest();
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationEnd(Animation animation) {
        if (this.advGoUp) {
            this.advGoUp = false;
            int i = this.currentAd + 1;
            this.currentAd = i;
            if (i >= this.advTextArray.length) {
                this.currentAd = 0;
            }
            this.advText.setText(this.advTextArray[this.currentAd]);
            this.advImage.setImageResource(Constants.adv_images[this.currentAd]);
            this.advLayout.clearAnimation();
            this.advLayout.startAnimation(this.animSlideDown);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        Intent intent = new Intent(this.ctx, (Class<?>) CheckInternetActivity.class);
        intent.addFlags(268435456);
        intent.putExtra(ADV_PACKAGE, this.appPackageNames[this.currentAd]);
        this.ctx.startActivity(intent);
    }

    /* loaded from: classes.dex */
    private class TimerTick implements Runnable {
        private TimerTick() {
        }

        @Override // java.lang.Runnable
        public void run() {
            HomeAdv.this.advLayout.clearAnimation();
            HomeAdv.this.advLayout.startAnimation(HomeAdv.this.animSlideUp);
            HomeAdv.this.admobRequest();
            HomeAdv.this.advGoUp = true;
            HomeAdv.this.advLayout.removeCallbacks(HomeAdv.this.timer);
            HomeAdv.this.advLayout.postDelayed(HomeAdv.this.timer, Constants.AD_TIMER_STEP);
        }
    }
}
