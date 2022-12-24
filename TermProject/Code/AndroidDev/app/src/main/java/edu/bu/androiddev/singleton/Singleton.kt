package edu.bu.androiddev.singleton

import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.fasterxml.jackson.databind.ObjectMapper

class Singleton {
    companion object{
        var objectMapper = ObjectMapper()
    }
}