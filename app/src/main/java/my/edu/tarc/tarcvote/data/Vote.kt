package my.edu.tarc.tarcvote.data

import android.os.Parcel
import android.os.Parcelable

data class Vote(
    val userUId: String,
    val campaignId: String,
    val candidate: String,
    val voteNumber: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userUId)
        parcel.writeString(campaignId)
        parcel.writeString(candidate)
        parcel.writeInt(voteNumber)
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






