package com.gamecodeschool.schoolutility;

/**
 * Created by wdwoo on 3/15/2017.
 */

public class HomeworkAssignment {

    String assignmentName;
    String assignmentDescription;
    String assignmentType;
    String classType;

    //Placeholder class which will be used to be homework assignments, which will have the information from DialogAssignHomework stored
    //in it and then reproduced wherever it is deemed necessary

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

    String dueDate;

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

}
