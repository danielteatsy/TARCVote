package my.edu.tarc.tarcvote.data

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp

data class Campaign(
    val id: String,
    var title: String,
    var endDateTime: Timestamp,
    val candidate1: Candidate,
    val candidate2: Candidate,
    val candidate3: Candidate,
): Parcelable {





    constructor() : this("", "", Timestamp.now(), Candidate(), Candidate(), Candidate())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeParcelable(endDateTime, flags)
        parcel.writeParcelable(candidate1, flags)
        parcel.writeParcelable(candidate2, flags)
        parcel.writeParcelable(candidate3, flags)
    }

    override fun describeContents(): Int = 0


    companion object CREATOR : Parcelable.Creator<Campaign> {
        override fun createFromParcel(parcel: Parcel): Campaign {
            return Campaign(
                parcel.readString()!!,
                parcel.readString()!!,
                parcel.readParcelable(Timestamp::class.java.classLoader)!!,
                parcel.readParcelable(Candidate::class.java.classLoader)!!,
                parcel.readParcelable(Candidate::class.java.classLoader)!!,
                parcel.readParcelable(Candidate::class.java.classLoader)!!
            )
        }

        override fun newArray(size: Int): Array<Campaign?> = arrayOfNulls(size)
    }
}
