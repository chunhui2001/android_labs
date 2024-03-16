package com.android_labs.foodsapp

import android.content.Context
import android.util.Log
import android.widget.Toast
import java.math.BigDecimal
import java.math.RoundingMode

class ManagementCart(
    private var context: Context,
) {
    private var tinyDb: TinyDB = TinyDB(context)

    fun totalMoney(): Double {

        var list = getFoodList()

        var bb= BigDecimal(0)

        for (f in list) {
            bb = bb.add((f.numberInCart * f.fee).toBigDecimal().setScale(2, RoundingMode.DOWN))
        }

        Log.d("totalMoney: ", "size: ${list.size}, $bb")

        return bb.toDouble()
    }

    fun addFood(food: FoodModel) {
        var list = getFoodList()
        var existsFood: FoodModel? = null

        for (f in list) {
            if (f.title == food.title) {
                f.numberInCart += food.numberInCart
                existsFood = f
                break
            }
        }

        if (existsFood == null) {
            list.add(food)
        }

        tinyDb.putListObject("foodList", list.toList())

        var msg = "Added to cart: " + food.title + ", cound=" + food.numberInCart

        if (existsFood != null) {
            msg = "Added to cart: " + existsFood.title + ", cound=" + existsFood.numberInCart
        }

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun getFoodList(): MutableList<FoodModel> {
        return this.tinyDb.getListObject("foodList", FoodModel::class.java)
    }

    fun updateFoodCount(foodName: String, count: Int) {
        var list = getFoodList()

        for (f in list) {
            if (f.title == foodName) {
                if (count < 0) {
                    f.numberInCart = 0
                } else {
                    f.numberInCart = count
                }
                break
            }
        }

        tinyDb.putListObject("foodList", list.toList())
    }
}