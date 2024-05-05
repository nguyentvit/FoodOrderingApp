package com.midterm.foododeringapp.Model

class FoodModel(
    private var nameFood: String,
    private var price: String,
    private var image: Int,
    private var nameRest: String
) {
    fun getNameFood(): String{
        return nameFood
    }
    fun setNameFood(nameFood: String){
        this.nameFood = nameFood
    }
    fun getPrice():String{
        return price
    }
    fun setPrice(price: String){
        this.price = price
    }
    fun getImage(): Int{
        return image
    }
    fun setImage(image: Int){
        this.image = image
    }
    fun getNameRest(): String{
        return nameRest
    }
    fun setNameRest(nameRest: String){
        this.nameRest = nameRest
    }
}