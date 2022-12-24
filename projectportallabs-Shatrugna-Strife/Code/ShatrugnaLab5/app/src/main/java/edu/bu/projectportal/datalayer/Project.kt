package edu.bu.projectportal.datalayer


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
//class Project(var id:Int,var title: String ="",var authors:String="",
// var links:String="", var favourite:Boolean=false, var keywords:String="",
// var description: String=""){

@Entity(tableName="projects")
data class Project(

    var title: String,
    var authors:String,
    var links:String,
    var favourite:Boolean,
    var keywords:String="",
    @ColumnInfo(name="desc")
    var description: String){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
//{
//    companion object {
//         // val project = Project(0, "Weather Forecast", "Weather Forcast is an app ...")
//        val projects = mutableListOf(
//            Project(0, "Weather Forecast", "Weather Forcast is an app ..."),
//            Project(1, "Connect Me", "Connect Me is an app ... "),
//            Project(2, "What to Eat", "What to Eat is an app ..."),
//            Project(3, "Project Portal", "Project Portal is an app ..."),
//            Project(4, "Smart Sense", "Project Portal is an app ..."))
//
//
//    }
//}


