package com.gamecodeschool.schoolutility;

/**
 * Created by wdwoo on 5/10/2017.
 */

public class SchoolClass {

    String className;
    String classPeriod;

    public SchoolClass() {
    }

    public SchoolClass(String className) {
        this.className = className;
    }

    public SchoolClass(String className, String classPeriod) {
        this.className = className;
        this.classPeriod = classPeriod;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassPeriod() {
        return classPeriod;
    }

    public void setClassPeriod(String classPeriod) {
        this.classPeriod = classPeriod;
    }
}
