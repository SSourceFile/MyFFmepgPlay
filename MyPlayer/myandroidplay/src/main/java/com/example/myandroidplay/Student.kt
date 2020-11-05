package com.example.myandroidplay

import android.os.Parcel
import android.os.Parcelable

class Student : Parcelable {
  var stdId: Int
  var stdName: String?
  override fun describeContents(): Int {
    return 0
  }

  override fun writeToParcel(out: Parcel, flags: Int) {
    out.writeInt(stdId)
    out.writeString(stdName)
  }

  constructor(studentId: Int, bookName: String?) {
    stdId = studentId
    stdName = bookName
  }

  private constructor(`in`: Parcel) {
    stdId = `in`.readInt()
    stdName = `in`.readString()
  }

  companion object {
    val CREATOR: Parcelable.Creator<Student?> = object : Parcelable.Creator<Student?> {
      override fun createFromParcel(source: Parcel): Student? {
        return Student(source)
      }

      override fun newArray(size: Int): Array<Student?> {
        return arrayOfNulls(size)
      }
    }
  }
}