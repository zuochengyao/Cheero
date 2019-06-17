package com.icheero.app.custom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CardViewFragment extends BaseFragment  implements SeekBar.OnSeekBarChangeListener
{
    @BindView(R.id.seek_bar_radius)
    SeekBar mSeekBarRadius;
    @BindView(R.id.seek_bar_shadow)
    SeekBar mSeekBarShadow;
    @BindView(R.id.seek_bar_content_padding)
    SeekBar mSeekBarContentPadding;
    @BindView(R.id.card_view)
    CardView mCardView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_card_view, container, false);
        ButterKnife.bind(this, view);
        mSeekBarRadius.setOnSeekBarChangeListener(this);
        mSeekBarShadow.setOnSeekBarChangeListener(this);
        mSeekBarContentPadding.setOnSeekBarChangeListener(this);
        return view;
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
