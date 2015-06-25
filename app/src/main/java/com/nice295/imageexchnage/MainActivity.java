package com.nice295.imageexchnage;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends ActionBarActivity {
    @InjectView(R.id.flList0)
    FrameLayout flList0;
    @InjectView(R.id.imageView)
    ImageView imageView;
    @InjectView(R.id.imageView2)
    ImageView imageView2;

    private Animation animation;

    int bigHeight = 0;
    int smallHeight = 0;
    Boolean mExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        Log.e("Small - ", String.valueOf(imageView.getHeight()));
        Log.e("Large - ", String.valueOf(imageView2.getHeight()));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        Log.w("Small - ", String.valueOf(imageView.getHeight()));
        Log.w("Large - ", String.valueOf(imageView2.getHeight()));
        bigHeight = imageView2.getHeight();
        smallHeight = imageView.getHeight();

        flList0.getLayoutParams().height = smallHeight;
        flList0.requestLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.flList0)
    public void clickFrame(View v) {
        if (mExpanded == false) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration(500);
            imageView.startAnimation(alphaAnimation);


            AlphaAnimation alphaAnimation1 = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation1.setDuration(500);

            imageView2.startAnimation(alphaAnimation1);

            expand(flList0);

            mExpanded = true;
        }
        else {
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration(500);
            imageView2.startAnimation(alphaAnimation);


            AlphaAnimation alphaAnimation1 = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation1.setDuration(500);

            imageView.startAnimation(alphaAnimation1);

            collapse(flList0);

            mExpanded = false;

        }
    }

    private void collapse(final View v)
    {
        final int initialHeight = v.getMeasuredHeight();

        animation = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t)
            {
                v.getLayoutParams().height = (interpolatedTime == 1) ? smallHeight : bigHeight - (int)((bigHeight - smallHeight) * interpolatedTime);
                v.requestLayout();

                if (interpolatedTime == 1) {
                    flList0.bringChildToFront(imageView);
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setDuration(500);
        v.startAnimation(animation);
    }

    private void expand(final View v)
    {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = bigHeight;

        animation = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t)
            {
                v.getLayoutParams().height = (interpolatedTime == 1) ? bigHeight : (int) ((targetHeight - smallHeight) * interpolatedTime) + smallHeight;
                v.requestLayout();

                if (interpolatedTime == 1) {
                    flList0.bringChildToFront(imageView2);
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setDuration(500);
        v.startAnimation(animation);
    }

}
