package my.edu.tarc.tarcvote.data

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp

data class Vote(
    val id: String,
    val campaignId: String,
    val candidateId: String,
    val voterId: String,
    val timestamp: Timestamp
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readParcelable(Timestamp::class.java.classLoader)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(campaignId)
        parcel.writeString(candidateId)
        parcel.writeString(voterId)
        parcel.writeParcelable(timestamp, flags)
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





