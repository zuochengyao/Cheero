package com.icheero.reverse.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.icheero.reverse.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.core.reverse.so.Elf32;
import com.icheero.sdk.core.reverse.so.SoParser;
import com.icheero.sdk.util.Common;
import com.icheero.sdk.util.FileUtils;
import com.icheero.sdk.util.Log;

public class DisposeSoActivity extends BaseActivity implements View.OnClickListener
{
    private Button mDisposeProgram;
    private Button mDisposeSection;

    private Elf32 mElf32;
    private SoParser mSoParser;
    private byte[] mSoData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispose_so);
        byte[] data = new byte[4];
        data[0] = 0;
        data[1] = 0;
        data[2] = 4;
        data[3] = -34;
        int i = Common.byte2Int(data);
        Log.i(TAG, i + "");
        byte[] bytes = Common.copyBytes(data, 0, data.length);
        doInit();
    }

    private void doInit()
    {
        mDisposeProgram = findViewById(R.id.so_dispose_program);
        mDisposeProgram.setOnClickListener(this);
        mDisposeSection = findViewById(R.id.so_dispose_section);
        mDisposeSection.setOnClickListener(this);

        mSoData = FileUtils.readRawResource(this, R.raw.libhello);
        if (mSoData == null)
        {
            Log.e(TAG, "read file failed!");
            return;
        }
        mElf32 = new Elf32();
        mSoParser = new SoParser(mElf32);
        Log.i(TAG, "+++++++++++++++++++Elf Header+++++++++++++++++");
        mSoParser.parseHeader(mSoData);
        Log.i(TAG, "header:\n" + mElf32.hdr.toString());
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.so_dispose_program:
            {
                Log.i(TAG, "+++++++++++++++++++Program Header+++++++++++++++++");
                int p_header_offset = Common.byte2Int(Common.reverseBytes(mElf32.hdr.e_phoff)); // 52;
                Log.i(TAG, "offset:" + p_header_offset);
                mSoParser.parseProgramHeaderList(mSoData, p_header_offset);
                mElf32.printPhdrList();
                break;
            }
            case R.id.so_dispose_section:
            {
                Log.i(TAG, "+++++++++++++++++++Section Header++++++++++++++++++");
                int s_header_offset = Common.byte2Int(Common.reverseBytes(mElf32.hdr.e_shoff));// 12592;
                Log.i(TAG, "offset:" + s_header_offset);
                mSoParser.parseSectionHeaderList(mSoData, s_header_offset);
                mElf32.printShdrList();
                break;
            }
        }
    }
}
