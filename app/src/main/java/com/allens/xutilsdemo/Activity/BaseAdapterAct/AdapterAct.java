package com.allens.xutilsdemo.Activity.BaseAdapterAct;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.allens.library.BaseAdapter.CommonAdapter;
import com.allens.library.BaseAdapter.ViewHolder;
import com.allens.xutilsdemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by allens on 2017/6/21.
 */

public class AdapterAct extends AppCompatActivity {

    @BindView(R.id.activity_adapter_ListView)
    ListView activityAdapterListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter);
        ButterKnife.bind(this);
        List<User> users = new ArrayList<>();
        users.add(new User("jhy1", R.mipmap.ic_launcher, "男"));
        users.add(new User("jhy2", R.mipmap.ic_launcher_round, "男"));
        users.add(new User("jhy3", R.mipmap.error, "男"));
        users.add(new User("jhy4", R.mipmap.loading, "男"));
        users.add(new User("jhy5", R.mipmap.ic_launcher, "男"));

        activityAdapterListView.setAdapter(new CommonAdapter<User>(getApplicationContext(), users, R.layout.item_text) {
            @Override
            public void convert(ViewHolder helper, User item) {
                helper.setText(R.id.item_text, item.name);
                helper.setImageResource(R.id.item_img, item.headId);
                helper.setText(R.id.item_textName, item.sex);
            }
        });
    }
}
