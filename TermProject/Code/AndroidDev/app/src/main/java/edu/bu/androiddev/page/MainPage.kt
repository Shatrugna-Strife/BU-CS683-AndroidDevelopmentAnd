package edu.bu.androiddev.page

import edu.bu.androiddev.datalayer.MyDatabase

import edu.bu.androiddev.datalayer.Note
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.bu.androiddev.components.NodePageLink
import edu.bu.androiddev.datalayer.NoteDao

open class MainPage(var navController: NavHostController) {
    lateinit var tmpVal:Note
    var notedao:NoteDao? = null
    var a:Int? = 0
    lateinit var nodeList: SnapshotStateList<NodePageLink>
    @SuppressLint("UnrememberedMutableState")
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun render(dataStore: MyDatabase?) {
//        var dataStore = LocalContext.current.createDataStore(name="cheepu")
        nodeList = mutableStateListOf<NodePageLink>();
        var tmp = readDataAndPopulate(dataStore)
        val openDialog = remember { mutableStateOf(false)  }
        val createPageText = remember { mutableStateOf("") }
//        if(tmp!=null) {
//            nodeList = mutableStateListOf<NodePageLink>(*tmp);
//        } else {

//        }

        val scope = rememberCoroutineScope()
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { openDialog.value = true}) {
                    Icon(Icons.Filled.Add,"")
                }
            }
        ) {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background,
                /*
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
//                }*/
            ) {
                Column(modifier = Modifier
                    .padding(12.dp)
                    .verticalScroll(rememberScrollState())) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                    ) {
                        Text(
                            text = "Create Page"
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
                }
            }
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
                                if(createPageText.value.isNotEmpty()) {
//                                    navController.navigate(NavData.page.withArgs(createPageText.value))
                                    nodeList.add(NodePageLink(
                                        name = createPageText.value + "\t07" + System.currentTimeMillis(),
                                        navController,
                                        {},
                                        ::deleteNode,-1,null
                                    ))
                                    tmpVal = Note(createPageText.value)


//                                            MyDatabase.getDbInstance(contextOfMain)?.noteDao()?.addNote(Note("sai"))
                                    notedao?.addNote(tmpVal)
                                    a = notedao?.count()

                                    Log.i("sai" , a.toString())

                                    createPageText.value = ""
                                }
                            }) {
                            Text("Create")
                        }
                    },
                )
            }
        }
    }

    fun deleteNode(index:Int){
        notedao?.deleteNotesWithId(nodeList[index].getDbId())
        nodeList.removeAt(index)
    }


    private fun readDataAndPopulate(dataStore: MyDatabase?): Array<NodePageLink>? {
        var tmpNodeList: Array<NodePageLink>? = null

        var tmp:List<Note>? = null
        if (dataStore != null) {
            notedao = dataStore.noteDao()
        }
//            CoroutineScope(Dispatchers.IO).launch {
        tmp = notedao?.getAllNotes()
        a = notedao?.count()
        Log.i("sai","a here : " + a.toString() + " tmp. size" + tmp?.size)
        if (tmp?.size != 0) {
            var tmpList = tmp as MutableList<Note>?
            if (tmpList != null) {
                tmpNodeList = Array(tmpList.size) { NodePageLink(
                    "",
                    navController,
                    {},
                    ::deleteNode,-1, null
                ) }
                for (i in 0..tmpList.size -1) {
//                            tmpNodeList!![i] =
                    nodeList.add(NodePageLink(
                        name = tmpList?.get(i).name,
                        navController,
                        {},
                        ::deleteNode,
                        tmpList?.get(i).id,null
                    ));
                }
            }

        }
//            }
//                var tmp = it[Constant.MAIN_DATA_KEY]

//        }
        return null

    }

    @Composable
    fun displayNodes(nodeList: SnapshotStateList<NodePageLink>){
        Column(modifier = Modifier.padding(top = 10.dp)) {
//            nodeList.map { it.render() }
            for (i in 0..nodeList.size-1){
                nodeList[i].render(index = i)
            }

        }

    }
}
