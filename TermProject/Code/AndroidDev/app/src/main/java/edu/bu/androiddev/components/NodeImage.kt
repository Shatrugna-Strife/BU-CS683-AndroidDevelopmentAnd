package edu.bu.androiddev.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import edu.bu.androiddev.model.Node

class NodeImage(
    var base64Image: String,
    var deleteCallBack: (Int) -> Unit,
    id: Long = -1,
    var setMoveIndex: ((Int) -> Unit)?
): Node {
    lateinit var bitmap:MutableState<Bitmap?>
//    lateinit var uri:MutableState<Uri>
    lateinit var focusRequester:FocusRequester
    val nodeType = "node_text_field"
    override var id:Long = id

    override fun generateJsonMap(): MutableMap<String, *> {
        var tmpMap = mutableMapOf("data" to base64Image, "node_type" to "node_image")
        return tmpMap
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun render(index:Int):Unit {
        var popupControl by remember { mutableStateOf(false) }
        val context = LocalContext.current
        bitmap =  remember {
            mutableStateOf<Bitmap?>(null)
        }
        bitmap.value = BitmapFactory.decodeByteArray(Base64.decode(base64Image, 0), 0, Base64.decode(base64Image, 0).size)
        focusRequester = FocusRequester()
//        focusRequester = FocusRequester()
//        BasicTextField(value = text.value, onValueChange = { str: String -> text.value = str },
//            textStyle = TextStyle(color = Color.LightGray),
//            cursorBrush = Brush.verticalGradient(0f to Color.LightGray, 1f to Color.LightGray),
//            modifier = Modifier.focusRequester(focusRequester).fillMaxWidth()
//        )
        Divider()
        Row(modifier = Modifier.height(IntrinsicSize.Max), verticalAlignment = Alignment.CenterVertically) {
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
            bitmap.value?.let {  btm ->
                Image(bitmap = btm.asImageBitmap(),
                    contentDescription =null,
                    modifier = Modifier.size(400.dp))
            }

        }
        Divider()
//        TextField(value = text.value, onValueChange = { str:String-> text.value = str })
//        Text(text = text.value)
    }

    override fun getDbId(): Long {
        return id
    }

}