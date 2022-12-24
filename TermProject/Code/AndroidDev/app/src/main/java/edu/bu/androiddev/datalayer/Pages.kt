package edu.bu.androiddev.datalayer

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName="pages",indices = [Index(value = ["parent"])])
data class Pages(
    var parent:String? = null,
    var name:String? = null,
    var nodeType:String?=null,
    var isChecked:Boolean = false,
    var order:Int,
    var rowCount:Int = 0,
    var columnCount:Int = 0
    ) {
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0
}