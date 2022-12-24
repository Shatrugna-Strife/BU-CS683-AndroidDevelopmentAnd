package edu.bu.androiddev

import edu.bu.androiddev.datalayer.MyDatabase
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.preferences.createDataStore
import edu.bu.androiddev.navigation.navMesh
import edu.bu.androiddev.ui.theme.AndroidDevTheme

class MainActivity() : ComponentActivity() {
    val dataStore = createDataStore(name="cheepu")
    private var roomDbInst: MyDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        roomDbInst = MyDatabase.getDbInstance(this)
        setContent {
            AndroidDevTheme {
                navMesh(roomDbInst)
//                page.render()
//                Navigation()
            }

        }
    }
}

@Composable
fun Greeting(name: String) {

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidDevTheme {
        Greeting("Android")
    }
}