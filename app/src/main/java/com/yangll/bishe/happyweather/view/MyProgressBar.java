package com.yangll.bishe.happyweather.view;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.yangll.bishe.happyweather.R;

public class MyProgressBar {

	public ProgressBar createMyProgressBar(Activity activity, Drawable drawable){
		FrameLayout rootContainer = (FrameLayout) activity.findViewById(android.R.id.content);
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;

		ProgressBar progressBar = new ProgressBar(activity);
		progressBar.setVisibility(View.GONE);
		progressBar.setLayoutParams(lp);

		if(drawable != null){
			progressBar.setIndeterminateDrawable(drawable);
		}else {
			progressBar.setIndeterminateDrawable(activity.getResources().getDrawable(R.drawable.progress_drawable));
		}

		rootContainer.addView(progressBar);
		return progressBar;
	}

//drawable可以自定义样式图片旋转速度等
//progressBar.setIndeterminateDrawable(activity.getResources().getDrawable(R.drawable.progress_drawable));
}
