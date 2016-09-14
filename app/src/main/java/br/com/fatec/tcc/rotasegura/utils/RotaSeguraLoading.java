package br.com.fatec.tcc.rotasegura.utils;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import br.com.fatec.tcc.rotasegura.R;

/**
 * Created by vilmar.filho on 1/5/16.
 */
public class RotaSeguraLoading extends ImageView{

    public RotaSeguraLoading(Context context) {
        super(context);
    }

    public RotaSeguraLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupLoading();
    }

    public RotaSeguraLoading(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupLoading();
    }

    private void setupLoading(){
        setBackgroundResource(R.drawable.loading_animated);

        post(new Runnable() {
            @Override
            public void run() {
                AnimationDrawable frameAnimation = (AnimationDrawable) getBackground();
                frameAnimation.start();
            }
        });
    }
}
