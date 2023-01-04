package my.edu.tarc.tarcvote.data

import android.os.Parcel
import android.os.Parcelable

data class Candidate(
    val id: String = "",
    var name: String? = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Candidate> {
        override fun createFromParcel(parcel: Parcel): Candidate {
            return Candidate(parcel)
        }

        override fun newArray(size: Int): Array<Candidate?> {
            return arrayOfNulls(size)
        }
    }
}