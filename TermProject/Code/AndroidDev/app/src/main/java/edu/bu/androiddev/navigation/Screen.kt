package edu.bu.androiddev.navigation

sealed class Screen(val navigate:String) {
    object MainScreen : Screen("main_screen")
    object DetailScreen : Screen("detail_screen")

    fun withArgs(vararg args:String):String {
        return buildString {
            append(navigate)
            args.forEach {arg->
                append("/$arg")
            }
        }
    }
}
