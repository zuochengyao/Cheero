package com.icheero.app.custom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.icheero.app.R;
import com.icheero.sdk.util.Common;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class RecyclerStringAdapter extends Adapter<RecyclerStringAdapter.RecyclerViewHolder>
{
    private Context mContext;
    private List<String> mStringList;

    public RecyclerStringAdapter(Context context, List<String> stringList)
    {
        this.mContext = context;
        this.mStringList = stringList;
    }

    public void removeItem(int position)
    {
        mStringList.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new RecyclerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position)
    {
        holder.mTextView.setText(mStringList.get(position));
        // 设置随机高度
        //        ViewGroup.LayoutParams params = holder.mTextView.getLayoutParams();
        //        params.height = (int) (100 + Math.random() * 300);
        //        holder.mTextView.setLayoutParams(params);
        holder.setPosition(position);
    }

    @Override
    public int getItemCount()
    {
        return mStringList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.tv_item)
        TextView mTextView;

        private int mPosition;

        void setPosition(int position)
        {
            this.mPosition = position;
        }

        RecyclerViewHolder(@NonNull View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick
        void ItemClick()
        {
            Common.toast(mContext, "click: " + mTextView.getText(), Toast.LENGTH_SHORT);
        }

        @OnLongClick
        boolean ItemLongClick()
        {
            new AlertDialog.Builder(mContext).setTitle(mContext.getString(R.string.cheero_delete_confirm))
                                             .setNegativeButton(R.string.cheero_cancel, null)
                                             .setPositiveButton(R.string.cheero_ok, (dialog, which) -> removeItem(mPosition))
                                             .create()
                                             .show();
            return false;
        }
    }
}
