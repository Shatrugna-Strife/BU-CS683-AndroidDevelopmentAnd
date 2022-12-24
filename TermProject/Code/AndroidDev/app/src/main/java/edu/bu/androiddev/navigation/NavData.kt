package edu.bu.androiddev.navigation

sealed class NavData(val navigate:String) {
    object mainPage : NavData("main_screen")
    object page :NavData("detail_screen")

    fun withArgs(vararg args:String):String {
        return buildString {
            append(navigate)
            args.forEach {arg->
                append("/$arg")
            }
        }
    }
}