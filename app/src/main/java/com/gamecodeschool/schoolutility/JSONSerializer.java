package com.gamecodeschool.schoolutility;

import android.content.Context;

/**
 * Created by wdwoo on 3/25/2017.
 */

public class JSONSerializer {
    //Member variables//
    private String mFilename;
    private Context mContext;
    ////////////////////

    public JSONSerializer(String fn, Context c)
    {
        mFilename = fn;
        mContext = c;
    }

}
