package edu.bu.androiddev.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import edu.bu.androiddev.model.Node

class NodeTable(
    var rowCount: Int,
    var colCount:Int,
    var data:String,
    var deleteCallBack: (Int) -> Unit,
    id: Long = -1,
): Node {
    lateinit var bitmap:MutableState<Bitmap?>
    //    lateinit var uri:MutableState<Uri>
    lateinit var focusRequester:FocusRequester
    val nodeType = "node_text_field"
    override var id:Long = id
    lateinit var mutStringList:List<MutableState<String>>

    override fun generateJsonMap(): MutableMap<String, *> {
        var temp = ""
        for(i in 0 until mutStringList.size-1){
            temp+=mutStringList[i].value+"\t07"
        }
        temp+=mutStringList[mutStringList.size-1].value
        var tmpMap = mutableMapOf("data" to mutableMapOf("data" to temp, "row" to rowCount, "col" to colCount) , "node_type" to "node_table")
        return tmpMap
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun render(index:Int):Unit {

        if(data.isNotEmpty()){
            var list = data.split("\t07")
            mutStringList = List(list.size) { remember { mutableStateOf(list[it]) } }
        }else{
            mutStringList = List(rowCount*colCount) { remember { mutableStateOf("") } }
        }
        var popupControl by remember { mutableStateOf(false) }
        val context = LocalContext.current
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
                    }
                }
            }
            val column1Weight = .3f // 30%
            val column2Weight = .7f // 70%
            Column(
                Modifier
                    .fillMaxSize()
                    .horizontalScroll(rememberScrollState())
//                    .padding(16.dp)
            ) {
//                // Here is the header
////                item {
//                Row(Modifier.background(Color.Gray)) {
//                    TableCell(text = "Column 1", weight = column1Weight)
//                    TableCell(text = "Column 2", weight = column2Weight)
//                }
//                }
                // Here are all the lines of your table.
                for(i in 0 until  rowCount){
                    Row(Modifier.fillMaxWidth()) {
                        for(j in 0 until colCount){
                            TableCell(textState = mutStringList[i*colCount+j], weight = column1Weight)
                        }
                        BasicTextField(value = "", onValueChange = {},modifier = Modifier
                            .width(100.dp).height(30.dp)){}
                    }
                }

//                Row(Modifier.fillMaxWidth()) {
//                    for(j in 0 until colCount){
//                        BasicTextField(
//                            value = "", onValueChange = {}, modifier = Modifier
//                                .width(100.dp).height(10.dp)
//                                .border(BorderStroke(1.dp, Color.LightGray))
//                        ) {}
//                    }
//                }
//                Text(text = "gwhe")
            }

        }
        Divider()
//        TextField(value = text.value, onValueChange = { str:String-> text.value = str })
//        Text(text = text.value)
    }

    @Composable
    fun RowScope.TableCell(
        textState: MutableState<String>,
        weight: Float
    ) {
        BasicTextField(value = textState.value, onValueChange = {textState.value = it}, modifier = Modifier
            .width(100.dp).height(30.dp)
            .horizontalScroll(
                rememberScrollState()
            ).padding(horizontal = 1.dp)
            .border(BorderStroke(1.dp, Color.LightGray)),textStyle = TextStyle(color = Color.LightGray),
            cursorBrush = Brush.verticalGradient(0f to Color.White, 1f to Color.White),)
    }

    override fun getDbId(): Long {
        return id
    }

}