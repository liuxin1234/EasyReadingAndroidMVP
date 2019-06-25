package com.liux.easyreadingandroidmvp.ui.activity.moreFuntion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.liux.easyreadingandroidmvp.R;
import com.liux.easyreadingandroidmvp.common.Contants;
import com.liux.easyreadingandroidmvp.https.GlideApp;
import com.liux.easyreadingandroidmvp.ui.activity.AboutWeActivity;
import com.liux.easyreadingandroidmvp.utils.Utils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:
 * Created by guzhenfu on 2016/11/16 13:43.
 */

public class FuliDetailActivity extends AppCompatActivity {
    @BindView(R.id.image_id)
    ImageView imageId;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String imageUrl;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setEnterTransition(new Fade().setDuration(500));
            getWindow().setExitTransition(new Fade().setDuration(500));
        }
        setContentView(R.layout.activity_fuli_detail);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            imageUrl = getIntent().getStringExtra(Contants.FULI_DETAIL);
            GlideApp.with(this)
                    .asBitmap()
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_loading)
                    .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {

                        @Override
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                            ViewGroup.LayoutParams params = imageId.getLayoutParams();
                            params.height = bitmap.getHeight() * Utils.getWidthInPx(FuliDetailActivity.this) / bitmap.getWidth();
                            imageId.setLayoutParams(params);
                            imageId.setImageBitmap(bitmap);
                            file = Utils.getImageFile(bitmap);
                        }

                    });
        }

        setSupportActionBar(toolbar);
    }



    @OnClick(R.id.image_id)
    void ImageClick(){
        if (toolbar.getAlpha() == 0) {
            ViewCompat.animate(toolbar)
                    .alpha(1).translationY(10)
                    .setDuration(500)
                    .start();
        }else {
            ViewCompat.animate(toolbar)
                    .alpha(0).translationY(-100)
                    .setDuration(500)
                    .start();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                Utils.sharePic(this, file);
                return true;

            case R.id.action_about:
                startActivity(new Intent(this, AboutWeActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
