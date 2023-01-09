package my.edu.tarc.tarcvote.data

import android.os.Parcel
import android.os.Parcelable

data class Vote(
    val userId: String,
    val campaignId: String,
    val candidateId: String,
    val candidateName: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(campaignId)
        parcel.writeString(candidateId)
        parcel.writeString(candidateName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Vote> {
        override fun createFromParcel(parcel: Parcel): Vote {
            return Vote(parcel)
        }

        override fun newArray(size: Int): Array<Vote?> {
            return arrayOfNulls(size)
        }
    }
}






