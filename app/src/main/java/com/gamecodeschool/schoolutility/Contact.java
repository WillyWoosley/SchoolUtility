package com.gamecodeschool.schoolutility;

import android.widget.QuickContactBadge;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    boolean mIsTeacher;
    boolean mIsLeader;
    QuickContactBadge mContactBadge;
    ////////////////////

    public Contact() {
    }

    public Contact(String username, boolean isTeacher, boolean isLeader, String email) {
        mName = username;
        mIsLeader = isLeader;
        mIsTeacher = isTeacher;
        mEmail = email;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getInformation() {
        return mInformation;
    }

    public void setInformation(String mInformation) {
        this.mInformation = mInformation;
    }

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String mNumber) {
        this.mNumber = mNumber;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public QuickContactBadge getContactBadge() {
        return mContactBadge;
    }

    public void setContactBadge(QuickContactBadge mContactBadge) {
        this.mContactBadge = mContactBadge;
    }
}
