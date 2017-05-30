package com.gamecodeschool.schoolutility;

import android.provider.ContactsContract;
import android.support.annotation.StringDef;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wdwoo on 3/15/2017.
 */

//TODO:Implement the rest of the variables which can be assigned to a homework assignment and make sure to implement them in all the necesary areas
public class HomeworkAssignment {

    //Member Variables//
    private String className;
    private String assignmentName;
    private String assignmentDescription;
    private String assignmentType;
    private String classType;
    private String dueDate;
    private String hwUid;
    ////////////////////

    public String getHwUid() {
        return hwUid;
    }

    public HomeworkAssignment(String assignmentName, String assignmentDescription, String dueDate, String className, String hwUid) {
        this.className = className;
        this.assignmentName = assignmentName;
        this.assignmentDescription = assignmentDescription;
        this.dueDate = dueDate;
        this.hwUid = hwUid;
    }

    public HomeworkAssignment(String assignmentName, String assignmentDescription, String dueDate, String className) {
        this.className = className;
        this.assignmentName = assignmentName;
        this.assignmentDescription = assignmentDescription;
        this.dueDate = dueDate;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getAssignmentDescription() {
        return assignmentDescription;
    }

    public void setAssignmentDescription(String assignmentDescription) {
        this.assignmentDescription = assignmentDescription;
    }

    public String getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(String assignmentType) {
        this.assignmentType = assignmentType;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public void setHwUid(String hwUid) {
        this.hwUid = hwUid;
    }

    public HomeworkAssignment() {
        //empty constructor, necesary to have one which can have a JSON object fed
    }
}
