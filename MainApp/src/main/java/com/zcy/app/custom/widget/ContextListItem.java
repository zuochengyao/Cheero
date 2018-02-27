package com.zcy.app.custom.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.zcy.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zuochengyao on 2018/2/27.
 */

public class ContextListItem extends LinearLayout implements PopupMenu.OnMenuItemClickListener
{
    private PopupMenu mPopupMenu;
    @BindView(R.id.list_item_text)
    TextView mTextView;
    @BindView(R.id.list_item_context)
    ImageView mImageView;

    public ContextListItem(Context context)
    {
        this(context, null);
    }

    public ContextListItem(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public ContextListItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        ButterKnife.bind(this);
        mPopupMenu = new PopupMenu(getContext(), mImageView);
        mPopupMenu.setOnMenuItemClickListener(this);
        mPopupMenu.inflate(R.menu.context_list);
    }

    @OnClick({R.id.list_item_context})
    public void OnClickEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.list_item_context:
            {
                mPopupMenu.show();
                break;
            }
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        String itemTxt = mTextView.getText().toString();
        switch (item.getItemId())
        {
            case R.id.menu_list_delete:
            {
                Toast.makeText(getContext(), "Delete" + itemTxt, Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.menu_list_edit:
            {
                Toast.makeText(getContext(), "Edit" + itemTxt, Toast.LENGTH_SHORT).show();
                break;
            }
        }
        return true;
    }
}
