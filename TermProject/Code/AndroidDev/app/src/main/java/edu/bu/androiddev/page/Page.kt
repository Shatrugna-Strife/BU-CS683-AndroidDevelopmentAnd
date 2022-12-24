package edu.bu.androiddev.page

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.bu.androiddev.components.*
import edu.bu.androiddev.datalayer.MyDatabase
import edu.bu.androiddev.datalayer.NoteDao
import edu.bu.androiddev.datalayer.Pages
import edu.bu.androiddev.model.Node
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.util.*


open class Page(var name: String?, var navController: NavHostController) {
    var notedao: NoteDao? = null
    var parentNode:String? = null
    lateinit var moveState:MutableState<Boolean>
    lateinit var moveIndex:MutableState<Int>
    lateinit var nodeList:SnapshotStateList<Node>
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnrememberedMutableState")
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun render(dataStore: MyDatabase?) {
        var imageUri by remember {
            mutableStateOf<Uri?>(null)
        }
        val context = LocalContext.current
        val bitmap =  remember {
            mutableStateOf<Bitmap?>(null)
        }
        val launcher = rememberLauncherForActivityResult(contract =
        ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
            var temp = uri?.let {
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap.value = MediaStore.Images
                        .Media.getBitmap(context.contentResolver,it)

                } else {
                    val source = ImageDecoder
                        .createSource(context.contentResolver,it)
                    bitmap.value = ImageDecoder.decodeBitmap(source)
                }
                val baos = ByteArrayOutputStream()
                bitmap.value?.compress(Bitmap.CompressFormat.PNG, 100, baos)
                var b = baos.toByteArray()
                while (b.size > 500000) {
                    val bitmap = BitmapFactory.decodeByteArray(b, 0, b.size)
                    val resized = Bitmap.createScaledBitmap(
                        bitmap,
                        (bitmap.width * 0.5).toInt(),
                        (bitmap.height * 0.5).toInt(),
                        true
                    )
                    val stream = ByteArrayOutputStream()
                    resized.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    b = stream.toByteArray()
                }
                val d = Base64.getEncoder().encodeToString(b)
                NodeImage(d, ::deleteNode, -1,null)
            }
            var id = notedao?.addPage(Pages(parentNode,"","node_to_do",false,nodeList.size))
//                        nodeList.add(temp)
            if (id != null) {
                temp!!.id = id
                nodeList.add(temp!!)
            }
        }


        if (dataStore != null) {
            notedao = dataStore.noteDao()
        }
        parentNode = name

        val scaffoldState = rememberScaffoldState()
        val createPageText = remember { mutableStateOf("") }
        moveState = remember { mutableStateOf(false)  }
        moveIndex = remember { mutableStateOf(-1)  }
        nodeList = mutableStateListOf<Node>()
        var tmpList = readDataAndPopulate(dataStore)
        val openDialog = remember { mutableStateOf(false)  }
        val openDialogTable = remember { mutableStateOf(false)  }
        val rowText = remember { mutableStateOf("")  }
        val columnText = remember { mutableStateOf("")  }

//        if(tmpList!=null) {
//            nodeList = mutableStateListOf<Node>(*tmpList.toTypedArray());
//        } else {
//            nodeList = mutableStateListOf()
//        }
//        val drawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
        val scope = rememberCoroutineScope()
        Scaffold(
            scaffoldState = scaffoldState,
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }) {
                    Icon(Icons.Filled.Add,"")
                }
            },
            drawerContent = {
                OutlinedButton(
                    onClick = {
                        var temp = NodeTextField("",
                            ::deleteNode,-1, null
                        )
                        var id = notedao?.addPage(Pages(parentNode,"","node_text_field",false,nodeList.size))
                        if (id != null) {
                            temp.id = id
                            nodeList.add(temp)
                        }
                      scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Unspecified),
                    border = BorderStroke(0.dp, Color.Unspecified),
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Text Field")
                }
                Divider()
                OutlinedButton(
                    onClick = {
//                        nodeList.add(NodeTextField())
                        openDialog.value = true
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    },
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Unspecified),
                    border = BorderStroke(0.dp, Color.Unspecified),
                ) {
                    Text(text = "Page Link")
                }
                Divider()
                OutlinedButton(
                    onClick = {
                        var temp = NodeToDo("", false, ::deleteNode, -1, null)
                        var id = notedao?.addPage(Pages(parentNode,"","node_to_do",false,nodeList.size))
//                        nodeList.add(temp)
                        if (id != null) {
                            temp.id = id
                            nodeList.add(temp)
                        }
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
//                        openDialog.value = true
                    },
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Unspecified),
                    border = BorderStroke(0.dp, Color.Unspecified),
                ) {
                    Text(text = "ToDo Node")
                }
                Divider()
                OutlinedButton(
                    onClick = {
                        launcher.launch("image/*")


//                        var temp = NodeImage(imageUri!!)
//                        var id = notedao?.addPage(Pages(parentNode,"","node_to_do",false))
////                        nodeList.add(temp)
//                        if (id != null) {
//                            temp.id = id
//                            nodeList.add(temp)
//                        }
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
//                        openDialog.value = true

                    },
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Unspecified),
                    border = BorderStroke(0.dp, Color.Unspecified),
                ) {
                    Text(text = "Image Node")
                }
                Divider()
                OutlinedButton(
                    onClick = {
//                        nodeList.add(NodeTextField())
                        openDialogTable.value = true
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    },
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Unspecified),
                    border = BorderStroke(0.dp, Color.Unspecified),
                ) {
                    Text(text = "Table")
                }
                Divider()
            },
//            floatingActionButton = {
//                FloatingActionButton(onClick = {scope.launch {
//                    drawerState.open()
//                }}) {
//                    Icon(Icons.Filled.Add,"")
//                }
//            },
        ) {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background,
//                onClick = {
//                    if(nodeList.size!=0){
//                        var tmp = nodeList[nodeList.size-1]
//                        if(tmp is NodeTextField) {
//                            tmp.focusRequester.requestFocus()
//                        }
//                    }else{
//                        var temp = NodeTextField()
//                        nodeList.add(temp)
////                        temp.focusRequester.requestFocus()
//                    }
//                }
            ) {
                Column(){
                    if(moveState.value) {
                        Button(onClick = { moveState.value = false }) {
                            Text(text = ("cancel"))
                        }
                    }
                    Column(
                        modifier = Modifier
//                            .padding(12.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = {
    //                            if(nodeList.size!=0) {
    //                                for (i in 0 until nodeList.size) {
    //                                    var temp = nodeList[i].generateJsonMap()
    //
    //                                    if(temp["node_type"]?.equals("node_to_do") == true) {
    //                                        var tmp = Pages(parentNode,(temp["data"] as MutableMap<String, String>)["data"].toString(),temp["node_type"].toString(),(temp["data"] as MutableMap<String, String>)["checked"] as Boolean)
    //                                        tmp.id = nodeList[i].id
    //                                        notedao?.updatePages(tmp)
    //                                    } else if (temp["node_type"]?.equals("node_text_field") == true){
    //                                        var tmp = Pages(parentNode,temp["data"].toString(),temp["node_type"].toString())
    //                                        tmp.id = nodeList[i].id
    //                                        notedao?.updatePages(tmp)
    //                                    } else if (temp["node_type"]?.equals("node_page_link") == true){
    //                                        var tmp = Pages(parentNode,temp["data"].toString(),temp["node_type"].toString())
    //                                        tmp.id = nodeList[i].id
    //                                        notedao?.updatePages(tmp)
    //                                    } else if (temp["node_type"]?.equals("node_image") == true){
    //                                        var tmp = Pages(parentNode,temp["data"].toString(),temp["node_type"].toString())
    //                                        tmp.id = nodeList[i].id
    //                                        notedao?.updatePages(tmp)
    //                                    }
    //                                }
                                saveData()
    //                                Log.i("sai " ,tmpList.get(""))
    //                                var tmpString = Singleton.objectMapper.writeValueAsString(tmpMap)
    
    
    //                                runBlocking {
    //                                    dataStore.edit {
    //                                        var key = name?.let { it1 -> preferencesKey<String>(it1) }
    //                                            ?: preferencesKey<String>("")
    //                                        it[key] = Singleton.objectMapper.writeValueAsString(tmpMap)
    //                                    }
    //                                }
    //                            }
                                navController.popBackStack()
                            }) {
                                Icon(Icons.Filled.ArrowBack, "backPage")
                            }
                            Text(
                                text = name?.split("\t07")?.get(0) ?:""
                            )
                            Text(text = "")
                            Text(
                                text = ""
                            )
                            Text(
                                text = "",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.End
                            )
                        }
                        Divider(thickness = 1.dp, color = Color.DarkGray)
                        displayNodes(nodeList = nodeList)
                        Box(
                            modifier = Modifier
                                .height(500.dp)
                                .fillMaxWidth()
                        ){}
                    }
                }
//                BottomDrawer(drawerState = drawerState,
//                    drawerContent = {
//                        // add your UI code
//                        Button(
//                            onClick = {
//                                nodeList.add(NodeTextField())
//                                scope.launch {
//                                    drawerState.close();
//                                }
//                            },
//                            modifier = Modifier
//                                .height(40.dp)
//                                .fillMaxWidth()
//                        ) {
//                            Text(text = "Text Field")
//                        }
//                        Divider()
//                        Button(
//                            onClick = {
////                        nodeList.add(NodeTextField())
//                            },
//                            modifier = Modifier
//                                .height(40.dp)
//                                .fillMaxWidth()
//                        ) {
//                            Text(text = "Text Field")
//                        }
//                    },
//                    gesturesEnabled = drawerState.isOpen,
//                    scrimColor = if (!drawerState.isOpen) Color.Unspecified else Color(0.4f,0.4f,0.4f,0.6f)
//                ){}
            }

//            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
//                ModalDrawer(
//                    drawerState = drawerState,
//                    gesturesEnabled = false,
//                    drawerContent = {
//                        // Drawer content
//                        Button(
//                            onClick = {
//                                nodeList.add(NodeTextField())
//                                scope.launch {
//                                    drawerState.close();
//                                }
//                            },
//                            modifier = Modifier.height(40.dp).fillMaxWidth()
//                        ) {
//                            Text(text = "Text Field")
//                        }
//                        Divider()
//                        Button(
//                            onClick = {
////                        nodeList.add(NodeTextField())
//                            },
//                            modifier = Modifier.height(40.dp).fillMaxWidth()
//                        ) {
//                            Text(text = "Text Field")
//                        }
//                    }
//                ) {
//
//                }
//            }

            if (openDialog.value) {

                AlertDialog(
                    onDismissRequest = {
                        // Dismiss the dialog when the user clicks outside the dialog or on the back
                        // button. If you want to disable that functionality, simply use an empty
                        // onCloseRequest.
                        openDialog.value = false
                    },
                    title = {
                        Text(text = "Enter Page Name")
                    },
                    text = {
                        OutlinedTextField(value = createPageText.value, onValueChange = { str:String-> createPageText.value = str }, modifier = Modifier.fillMaxWidth())
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                                if(createPageText.value.length!=0) {
//                                    navController.navigate(NavData.page.withArgs(createPageText.value))
                                    var temp = NodePageLink(
                                        name = createPageText.value + "\t07" + System.currentTimeMillis(),
                                        navController,
                                        ::saveData,
                                        ::deleteNode,-1
                                        , null
                                    )
//                                    var tempData = temp.generateJsonMap()
                                    var id = notedao?.addPage(Pages(parentNode,createPageText.value,"node_page_link",false,nodeList.size))
//                                    nodeList.add(temp)
                                    if (id != null) {
                                        temp.id = id
                                        nodeList.add(temp)
                                    }
//                                    if(nodeList.size!=0) {
//                                        var tmpMap: MutableMap<String, *> =
//                                            mutableMapOf("data" to mutableListOf<MutableMap<String, *>>())
//                                        var tmpList =
//                                            tmpMap.get("data") as MutableList<MutableMap<String, *>>
//                                        for (i in 0 until nodeList.size) {
//                                            tmpList.add(nodeList[i].generateJsonMap())
//                                            notedao?.addPage(Pages(parentNode,tmpList[i]["data"].toString(),tmpList[i]["node_type"].toString()))
//                                        }
//                                    }
                                    createPageText.value = ""
                                }
                            }) {
                            Text("Create")
                        }
                    },
                )
            }
            if (openDialogTable.value) {

                AlertDialog(
                    onDismissRequest = {
                        // Dismiss the dialog when the user clicks outside the dialog or on the back
                        // button. If you want to disable that functionality, simply use an empty
                        // onCloseRequest.
                        openDialogTable.value = false
                    },
                    title = {
                        Text(text = "Enter Page Name")
                    },
                    text = {
                        Column() {
                            Row(){
                                Text(text = "Enter Number of Rows")
                                OutlinedTextField(value = rowText.value, onValueChange = { str:String-> rowText.value = str }, modifier = Modifier.fillMaxWidth())
                            }
                            Row() {
                                Text(text = "Enter Number of Cols")
                                OutlinedTextField(value = columnText.value, onValueChange = { str:String-> columnText.value = str }, modifier = Modifier.fillMaxWidth())
                            }
                        }

                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialogTable.value = false
                                if(rowText.value.isNotEmpty() && columnText.value.isNotEmpty() && isNumeric(rowText.value) && isNumeric(columnText.value) && rowText.value.toInt()>0 && columnText.value.toInt()>0) {
//                                    navController.navigate(NavData.page.withArgs(createPageText.value))
                                    var temp = NodeTable(
                                        rowText.value.toInt(),
                                        columnText.value.toInt(),
                                        "",
                                        ::deleteNode,-1
                                    )
//                                    var tempData = temp.generateJsonMap()
                                    var id = notedao?.addPage(Pages(parentNode,"","node_table",false,nodeList.size, rowCount = rowText.value.toInt(), columnCount = columnText.value.toInt()))
//                                    nodeList.add(temp)
                                    if (id != null) {
                                        temp.id = id
                                        nodeList.add(temp)
                                    }
                                }
                            }) {
                            Text("Create")
                        }
                    },
                )
            }
        }
    }

    @Composable
    fun displayNodes(nodeList: SnapshotStateList<Node>) {
        Column(modifier = Modifier.padding(top = 10.dp)) {

            for(i in 0 until nodeList.size){
                if(moveState.value) {
                    Box(modifier = Modifier
                        .clickable { }
                        .fillMaxWidth(1.0f)
                        .height(20.dp)
                        .background(
                            color = Color(1.0f, 0.0f, 1.0f, 0.231f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    if (moveIndex.value != i && moveIndex.value + 1 != i) {
                                        val temp = nodeList[moveIndex.value]
                                        nodeList.removeAt(moveIndex.value)
                                        nodeList.add(i, temp)
//                                        nodeList.addAll(tmp)
                                    }
//                                    var tmp = nodeList.toList();
//                                    nodeList.clear()
//                                    nodeList.addAll(tmp)
                                    moveState.value = false
                                }
                            )
                        }) {

                    }
                }
                nodeList[i].render(index = i)
            }
            if(moveState.value) {
                Box(modifier = Modifier
                    .clickable { }
                    .fillMaxWidth(1.0f)
                    .height(20.dp)
                    .background(
                        color = Color(1.0f, 0.0f, 1.0f, 0.231f),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                if (moveIndex.value != nodeList.size - 1) {
                                    var tmp = nodeList.toMutableList()
                                    nodeList.clear()
                                    var temp = tmp[moveIndex.value]
                                    tmp.removeAt(moveIndex.value)
                                    tmp.add(temp)
                                    nodeList.addAll(tmp)
                                }
//                                var tmp = nodeList.toList();
//                                nodeList.clear()
//                                nodeList.addAll(tmp)
                                moveState.value = false
                            }
                        )
                    }) {

                }
            }
        }
    }

    fun deleteNode(index:Int){
        notedao?.deletePagesWithId(nodeList[index].getDbId())
        nodeList.removeAt(index)
    }

    fun setMoveIndex(index:Int){
        moveIndex.value = index
        moveState.value = true
    }

    fun saveData(){
        if(nodeList.size!=0) {
            for (i in 0 until nodeList.size) {
                var temp = nodeList[i].generateJsonMap()

                if(temp["node_type"]?.equals("node_to_do") == true) {
                    var tmp = Pages(parentNode,(temp["data"] as MutableMap<String, String>)["data"].toString(),temp["node_type"].toString(),(temp["data"] as MutableMap<String, String>)["checked"] as Boolean,i)
                    tmp.id = nodeList[i].id
                    notedao?.updatePages(tmp)
                } else if (temp["node_type"]?.equals("node_text_field") == true){
                    var tmp = Pages(parentNode,temp["data"].toString(),temp["node_type"].toString(),false,i)
                    tmp.id = nodeList[i].id
                    notedao?.updatePages(tmp)
                } else if (temp["node_type"]?.equals("node_page_link") == true){
                    var tmp = Pages(parentNode,temp["data"].toString(),temp["node_type"].toString(),false,i)
                    tmp.id = nodeList[i].id
                    notedao?.updatePages(tmp)
                } else if (temp["node_type"]?.equals("node_image") == true){
                    var tmp = Pages(parentNode,temp["data"].toString(),temp["node_type"].toString(),false,i)
                    tmp.id = nodeList[i].id
                    notedao?.updatePages(tmp)
                }else if (temp["node_type"]?.equals("node_table") == true){
                    var tmp = Pages(parentNode,(temp["data"] as MutableMap<String,String>)["data"],temp["node_type"].toString(),false,i, (temp["data"] as MutableMap<String,String>)["row"] as Int, (temp["data"] as MutableMap<String,String>)["col"] as Int)
                    tmp.id = nodeList[i].id
                    notedao?.updatePages(tmp)
                }
            }
//                                Log.i("sai " ,tmpList.get(""))
//                                var tmpString = Singleton.objectMapper.writeValueAsString(tmpMap)


//                                runBlocking {
//                                    dataStore.edit {
//                                        var key = name?.let { it1 -> preferencesKey<String>(it1) }
//                                            ?: preferencesKey<String>("")
//                                        it[key] = Singleton.objectMapper.writeValueAsString(tmpMap)
//                                    }
//                                }
        }
    }

    fun isNumeric(toCheck: String): Boolean {
        return toCheck.all { char -> char.isDigit() }
    }


    @Composable
    private fun readDataAndPopulate(dataStore: MyDatabase?): MutableList<Node>? {
//        var tmpNodeList: Array<Node>? = null
        var tmpList:List<Pages>? = null
        tmpList = notedao?.getPageWithParentName(parentNode)

        if (tmpList != null) {
            if(tmpList.isNotEmpty()) {
//                tmpList = tmpList.sortedByDescending { it.order }
                for(i in tmpList.indices) {
                    if(tmpList[i].nodeType.equals("node_text_field")) {
                        nodeList.add(NodeTextField(tmpList[i].name, ::deleteNode, tmpList[i].id, null))
                    }else if(tmpList[i].nodeType.equals("node_page_link")) {
                        nodeList.add(NodePageLink(tmpList[i].name, navController,::saveData, ::deleteNode, tmpList[i].id, null))
                    }else if(tmpList[i].nodeType.equals("node_to_do")) {
                        nodeList.add(NodeToDo(tmpList[i].name,  tmpList[i].isChecked, ::deleteNode, tmpList[i].id, null))
                    }else if(tmpList[i].nodeType.equals("node_image")){
                        tmpList[i].name?.let { NodeImage(it, ::deleteNode, tmpList!![i].id, null) }?.let { nodeList.add(it) }
                    }else if(tmpList[i].nodeType.equals("node_table")){
                        tmpList[i].name?.let { NodeTable(tmpList!![i].rowCount, tmpList!![i].columnCount,it, ::deleteNode, tmpList!![i].id) }?.let { nodeList.add(it) }
                    }
                }
            }
        }
        tmpList = null
        return null
//        runBlocking {
//            dataStore.edit {
//                var key = name?.let { it1 -> preferencesKey<String>(it1) }?:preferencesKey<String>("")
//                var tmp = it[key]
//                if (tmp != null) {
//                    var tmpList = Singleton.objectMapper.readValue(tmp, MutableMap::class.java)
//                        .get("data") as MutableList<MutableMap<String,*>>
//                    tmpNodeList = Array(tmpList.size) { null }
//                    for (i in 0..tmpList.size-1) {
//                        if((tmpList.get(i).get("node_type") as String).equals("node_text_field")){
//                            tmpNodeList!![i] = NodeTextField(tmpList.get(i).get("data") as String)
//                        }
//                        if((tmpList.get(i).get("node_type") as String).equals("node_page_link")){
//                            tmpNodeList!![i] = NodePageLink(tmpList.get(i).get("data") as String, navController)
//                        }
//                        if((tmpList.get(i).get("node_type") as String).equals("node_to_do")){
//                            tmpNodeList!![i] = NodeToDo((tmpList.get(i).get("data") as Map<String,*>).get("data") as String, (tmpList.get(i).get("data") as Map<String,*>).get("checked") as Boolean)
//                        }
//                    }
//                }
//            }
//        }
//        var finalMutableList:MutableList<Node> = mutableListOf();
//        if(tmpNodeList==null) return null
//        for(i in 0..tmpNodeList!!.size-1){
//            var tmp = tmpNodeList!!.get(i)
//            if(tmp!=null)
//                finalMutableList.add(tmp)
//        }
//        if(finalMutableList.size == 0)return null
//        return finalMutableList
    }
}
