package edu.bu.projectportal.datalayer

import androidx.lifecycle.LiveData
import androidx.room.*
/*
This ProjectDao interface defines various CRUD operations
to access the projects table in the database
 */
@Dao
interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProject(project: Project)

    @Delete
    fun delProject(project: Project)

    @Update
    fun editProject(project: Project)

    @Query("SELECT count(*) From projects")
    fun count(): Int

    @Query("SELECT * FROM projects")
    fun getAllProjects(): List<Project>

    @Query("SELECT * FROM projects where favourite=1")
    fun getAllFavouriteProjects(): List<Project>

    @Query("SELECT * FROM projects where id = :projId")
    fun searchProjectById(projId: Long): Project

    @Query("SELECT * FROM projects WHERE title like :projTitle ")
    fun searchProjectsByTitle(projTitle:String): List<Project>

    @Update
    fun updateProject(project:Project)
}