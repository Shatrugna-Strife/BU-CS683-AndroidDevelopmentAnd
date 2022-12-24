package edu.bu.androiddev.constant

import androidx.datastore.preferences.core.preferencesKey

class Constant {
    companion object{
        val MAIN_DATA_KEY = preferencesKey<String>("main_page")
    }
}