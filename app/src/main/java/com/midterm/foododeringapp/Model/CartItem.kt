package com.midterm.foododeringapp.Model

import android.os.Parcel
import android.os.Parcelable

data class CartItem(
    var foodName: String? = null,
    var foodPrice: String? = null,
    var foodImage: String? = null,
//    var nameRest: String? = null,
    var foodDescription: String? = null,
    var foodQuantity: Int? = null,
    var foodIngredients: String? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        //    parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(foodName)
        parcel.writeString(foodPrice)
        parcel.writeString(foodImage)
        parcel.writeString(foodDescription)
        parcel.writeInt(foodQuantity!!)
        parcel.writeString(foodIngredients)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CartItem> {
        override fun createFromParcel(parcel: Parcel): CartItem {
            return CartItem(parcel)
        }

        override fun newArray(size: Int): Array<CartItem?> {
            return arrayOfNulls(size)
        }
    }
}
