// IStudent.aidl
package com.example.myandroidplay;

// Declare any non-default types here with import statements

interface IStudent {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
//
    List<Student> getList();
//    void addStudent(in Student student);
}
