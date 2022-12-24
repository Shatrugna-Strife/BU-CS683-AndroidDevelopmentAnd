package edu.bu.androiddev.datalayer
import edu.bu.androiddev.datalayer.Note
import edu.bu.androiddev.datalayer.NoteDao
import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Note::class,Pages::class], version = 1)
abstract class MyDatabase:RoomDatabase() {
    abstract fun noteDao():NoteDao
    companion object{
        private var dbInst:MyDatabase? = null
        fun getDbInstance(context:Context) : MyDatabase? {
            if(dbInst == null) {
                try {
                    synchronized(MyDatabase::class.java) {
                        dbInst = Room.databaseBuilder(
                            context,
                            MyDatabase::class.java,
                            "androidDevAppDb",
                        )
//                            .addCallback(object : RoomDatabase.Callback() {
//                                override fun onCreate(db: SupportSQLiteDatabase) {
//                                    super.onCreate(db)
//                                    addInitProjects()
//                                }
//                            })
                            .allowMainThreadQueries()
//                            .fallbackToDestructiveMigration()
                            .build()
                        return dbInst
                    }
                } catch(e:Exception){
                    Log.i("sai","before printstacktrace")
                    e.printStackTrace()
                    Log.i("sai","after printstacktrace")

                }
            }
            return dbInst
        }
//        fun addInitProjects() {
//            // need to execute in a separate thread
//            CoroutineScope(Dispatchers.IO).launch {
//                dbInst?.noteDao()?.addNote(
//                    Note(
//                        "Quick Link"
//                    )
//                )
//            }
//        }
    }


}