package com.midterm.foododeringapp.Model

import android.os.Parcel
import android.os.Parcelable

data class FoodModel(
    var foodName: String? = null,
    var foodPrice: String? = null,
    var foodImage: String? = null,
//    var nameRest: String? = null,
    var foodDescription: String? = null,
    var foodIngredient: String? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
    //    parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(foodName)
        parcel.writeString(foodPrice)
        parcel.writeString(foodImage)
  //      parcel.writeString(nameRest)
        parcel.writeString(foodDescription)
        parcel.writeString(foodIngredient)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FoodModel> {
        override fun createFromParcel(parcel: Parcel): FoodModel {
            return FoodModel(parcel)
        }

        override fun newArray(size: Int): Array<FoodModel?> {
            return arrayOfNulls(size)
        }
    }
}