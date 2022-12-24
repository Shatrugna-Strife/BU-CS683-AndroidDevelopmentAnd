package edu.bu.projectportal

data class Project(var id:Int,var title: String ="",var authors:String="", var links:String="", var favourite:Boolean=false, var keywords:String="", var description: String=""){
    companion object {
        // val project = Project(0, "Weather Forecast", "Weather Forcast is an app ...")
        val projects = mutableListOf(
            Project(0, "Weather Forecast","shatru", "www.facebook.com", true, "weather, forecast", "An app to Forecast Weather"),
        )
    }
}