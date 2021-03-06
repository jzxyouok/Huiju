package kevinz.huiju.ui.home;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import com.facebook.drawee.view.SimpleDraweeView;

import kevinz.huiju.R;
import kevinz.huiju.bean.Collections;
import kevinz.huiju.database.DataBaseHelper;
import kevinz.huiju.support.DisplayUtil;
import kevinz.huiju.ui.base.BaseDetailsActivity;

/**
 * Created by Administrator on 2016/11/18.
 */

public class CollectDetailsActivity extends BaseDetailsActivity {
    Collections collections;
    protected SimpleDraweeView topImage;
    ScrollView scrollView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        collections = (Collections) getIntent().getSerializableExtra("details");
        topImage = (SimpleDraweeView)findViewById(R.id.topImage);
        scrollView = (ScrollView)findViewById(R.id.scroll);
        initView();
        Log.d("image url ::",collections.getImage());
        topImage.setImageURI(Uri.parse(collections.getImage()));
        isCollected = collections.getIfcollected();
    }

    @Override
    protected void initView() {
        super.initView();
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                topImage.setTranslationY(Math.max(-scrollY / 2, -DisplayUtil.dip2px(getBaseContext(), 170)));
            }
        });
//        contentView.loadUrl(articleBean.getUrl());
        if(collections.getUrl()!=null){
            contentView.loadUrl(collections.getUrl());
        }else {
            cover.setVisibility(View.GONE);
            contentView.loadDataWithBaseURL(null, collections.getDescription(), "text/html", "utf-8", null);
        }
    }


    @Override
    protected String getShareInfo() {
        return "分享自汇聚";
    }

    @Override
    protected void addToCollection() {
    }

    @Override
    protected void removeFromCollection() {
        super.removeFromCollection();
        DataBaseHelper helper = new DataBaseHelper(this,"huiju",null,1);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from collections where title = '"+ collections.getTitle()+"'");
    }
}
