package edu.bu.androiddev.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import edu.bu.androiddev.model.Node
import edu.bu.androiddev.navigation.NavData

class NodePageLink(
    var name: String?, var navController: NavController, var saveData: ()->Unit,
    var deleteCallBack:(Int)->Unit,
    id: Long=-1,
    var setMoveIndex: ((Int) -> Unit)?
): Node {
    lateinit var text:MutableState<String>
    lateinit var focusRequester:FocusRequester
    override var id:Long = id

    override fun generateJsonMap(): MutableMap<String, *> {
        var tmpMap = mutableMapOf("data" to name, "node_type" to "node_page_link")
        return tmpMap
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun render(index: Int) {
        var popupControl by remember { mutableStateOf(false) }
        focusRequester = FocusRequester()
        text = remember { mutableStateOf("") }
//        focusRequester = FocusRequester()
//        BasicTextField(value = text.value, onValueChange = { str: String -> text.value = str },
//            textStyle = TextStyle(color = Color.LightGray),
//            cursorBrush = Brush.verticalGradient(0f to Color.LightGray, 1f to Color.LightGray),
//            modifier = Modifier.focusRequester(focusRequester).fillMaxWidth()
//        )
        Row(modifier = Modifier
            .height(IntrinsicSize.Max)
            , verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { popupControl=true }) {
                Icon(Icons.Filled.Menu,"",)
            }
            if (popupControl) {
                Popup(alignment = Alignment.CenterStart,
                    offset = IntOffset(20, 50),
                    onDismissRequest = { popupControl = false },) {
                    Column() {
                        OutlinedButton(
                            onClick = {
                                deleteCallBack(index)
                                popupControl = false
                            }
                        ){
                            Text("Delete")
                        }
                        if(setMoveIndex!=null) {
                            OutlinedButton(
                                onClick = {
                                    setMoveIndex?.let { it(index) }
                                    popupControl = false
                                }
                            ) {
                                Text("Move")
                            }
                        }
                    }
                }
            }

             OutlinedButton(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Unspecified),
                border = BorderStroke(0.dp, Color.Unspecified),
                onClick = {
                    navController.navigate(NavData.page.withArgs(name.toString()))
                    saveData()
                }
//                    .onKeyEvent {
//                            event: KeyEvent ->
//                        // handle backspace key
//                        if (event.type == KeyEventType.KeyUp &&
//                            event.key == Key.Backspace &&
//                            text.value.isEmpty()
//                        // also any additional checks of the "list" i.e isNotEmpty()
//                        ) {
//                            // TODO remove from list
//                            return@onKeyEvent true
//                        }
//                        false
//                    }

            ){
                Text(name?.split("\t07")?.get(0) ?: "")
            }
//            OutlinedTextField(value = text.value, onValueChange = { str:String-> text.value = str }, modifier = Modifier
//                .fillMaxWidth()
//                .focusRequester(focusRequester))
        }

    }

    override fun getDbId(): Long {
        return id
    }

//        TextField(value = text.value, onValueChange = { str:String-> text.value = str })
//        Text(text = text.value)
}
