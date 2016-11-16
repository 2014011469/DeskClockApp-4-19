package com.cn.daming.deskclock;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class SetBackGround extends Activity implements
             AdapterView.OnItemSelectedListener, ViewSwitcher.ViewFactory, OnClickListener{

	private static final String SET_BACK_GROUND = "getDrawable";
	private ImageSwitcher mSwitcher;
	private Gallery mGallery;
	
	private Integer[] mThumbIds = {
            R.drawable.alarm_small_bg1, R.drawable.alarm_small_bg2,
            R.drawable.alarm_small_bg3, R.drawable.alarm_small_bg4,
            R.drawable.alarm_small_bg5, R.drawable.alarm_small_bg6,
            R.drawable.alarm_small_bg7, R.drawable.alarm_small_bg8,
            R.drawable.alarm_small_bg9, R.drawable.alarm_small_bg10,
            R.drawable.alarm_small_bg11,R.drawable.alarm_small_bg12};

    private Integer[] mImageIds = {
            R.drawable.alarm_bg1, R.drawable.alarm_bg2, 
            R.drawable.alarm_bg3, R.drawable.alarm_bg4, 
            R.drawable.alarm_bg5, R.drawable.alarm_bg6,
            R.drawable.alarm_bg7, R.drawable.alarm_bg8, 
            R.drawable.alarm_bg9, R.drawable.alarm_bg10,
            R.drawable.alarm_bg11,R.drawable.alarm_bg12};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.alarm_setbg);
		
		findViewById(R.id.setButton_bg).setOnClickListener(this);
		mSwitcher = (ImageSwitcher) findViewById(R.id.switcher);
        mSwitcher.setFactory(this);
        mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in));
        mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out));
        mGallery = (Gallery) findViewById(R.id.gallery);
        //给gallery设置适配器
        mGallery.setAdapter(new ImageAdapter(this));
        //给gallery的每一项设置监听
        mGallery.setOnItemSelectedListener(this);
	}

	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		mSwitcher.setImageResource(mImageIds[position]);
		
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		
	}

	public class ImageAdapter extends BaseAdapter {
		private LayoutInflater mLayoutInflater;
		
        public ImageAdapter(SetBackGround context) {
            mLayoutInflater = context.getLayoutInflater();
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
        	ImageView image;
            if (convertView == null) {
                image = (ImageView) mLayoutInflater.inflate(R.layout.wallpaper_item, parent, false);
            } else {
                image = (ImageView) convertView;
            }
        	
            image.setImageResource(mThumbIds[position]);
            Drawable thumbDrawable = image.getDrawable();
            if (thumbDrawable != null) {
                thumbDrawable.setDither(true);
            } else {
                Log.e("wangxianming", "Error decoding thumbnail resId=" + mThumbIds[position] + " for wallpaper #"
                        + position);
            }
            return image;
        }
    }

	//设置button【设置背景】事件的监听
	public void onClick(View v) {
		//得到选中每一项的下标，下标从0开始，图片alarm_bg1的值是从1开始，所以+1;
		int position = mGallery.getSelectedItemPosition() + 1;
		Resources res=getResources();
		//通过映射的方法得到该图片对应R的值
		int id = res.getIdentifier("alarm_bg"+position, "drawable", getPackageName());
		Log.v("wangxianming", "SetBackGround ---> onClick(View v)  id == "+id);
	    Intent intent = new Intent();
        Bundle bundle = new Bundle();   
        bundle.putInt(SET_BACK_GROUND, id);   
        intent.putExtras(bundle); 
        setResult(RESULT_OK, intent);
        //释放对象
        intent = null; bundle = null;
        SetBackGround.this.finish();
	}

	public View makeView() {
		ImageView imageview = new ImageView(this);
		imageview.setBackgroundColor(0xFF000000);
		imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
		imageview.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));
        return imageview;
	}
}
