package com.icheero.app.activity.ui.lollipop;

import android.os.Bundle;
import android.widget.SeekBar;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;

import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CardViewActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener
{
    @BindView(R.id.seek_bar_radius)
    SeekBar mSeekBarRadius;
    @BindView(R.id.seek_bar_shadow)
    SeekBar mSeekBarShadow;
    @BindView(R.id.seek_bar_content_padding)
    SeekBar mSeekBarContentPadding;
    @BindView(R.id.card_view)
    CardView mCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        doInitView();
    }

    private void doInitView()
    {
        ButterKnife.bind(this);
        mSeekBarRadius.setOnSeekBarChangeListener(this);
        mSeekBarShadow.setOnSeekBarChangeListener(this);
        mSeekBarContentPadding.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        switch (seekBar.getId())
        {
            case R.id.seek_bar_radius:
                mCardView.setRadius(progress);
                break;
            case R.id.seek_bar_shadow:
                mCardView.setCardElevation(progress);
                break;
            case R.id.seek_bar_content_padding:
                mCardView.setContentPadding(progress, progress, progress, progress);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar)
    {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar)
    {

    }
}
