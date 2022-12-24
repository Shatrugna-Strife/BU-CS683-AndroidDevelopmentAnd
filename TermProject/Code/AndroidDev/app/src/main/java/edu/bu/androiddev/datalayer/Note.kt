package edu.bu.androiddev.datalayer

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="notes")
data class Note(
    var name:String? = null,
    var nodeType:String?=null,
    var isChecked:Boolean = false,

) {
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0
}