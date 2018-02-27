package com.zcy.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.zcy.app.R;
import com.zcy.app.custom.widget.SimpleSectionAdapter;

public class SectionsActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ListView list = new ListView(this);
        SimpleSectionAdapter<String> adapter = new SimpleSectionAdapter<String>(list, R.layout.list_header, android.R.layout.simple_list_item_1)
        {
            @Override
            public void onSectionItemClick(String item)
            {
                Toast.makeText(SectionsActivity.this, item, Toast.LENGTH_SHORT).show();
            }
        };
        adapter.addSection("Fruits", new String[]{"Apple", "Orange", "Banana"});
        adapter.addSection("Vegetables", new String[]{"Carrots", "Peas", "Broccoli"});
        adapter.addSection("Meats", new String[]{"Pork", "Beef", "Chicken", "Lamb"});

        list.setAdapter(adapter);
        setContentView(list);
    }
}
