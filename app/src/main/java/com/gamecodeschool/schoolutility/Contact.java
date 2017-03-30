package com.gamecodeschool.schoolutility;

import android.widget.QuickContactBadge;

/**
 * Created by wdwoo on 3/29/2017.
 */

//For now largely a placeholder class with getter and setter methods for crucial information which will someday be
//Held and recieved by contact objects
public class Contact {

    //Member Variables//
    String mName;
    String mInformation;
    String mNumber;
    String mEmail;
    QuickContactBadge mContactBadge;
    ////////////////////

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmInformation() {
        return mInformation;
    }

    public void setmInformation(String mInformation) {
        this.mInformation = mInformation;
    }

    public String getmNumber() {
        return mNumber;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public QuickContactBadge getmContactBadge() {
        return mContactBadge;
    }

    public void setmContactBadge(QuickContactBadge mContactBadge) {
        this.mContactBadge = mContactBadge;
    }
}
