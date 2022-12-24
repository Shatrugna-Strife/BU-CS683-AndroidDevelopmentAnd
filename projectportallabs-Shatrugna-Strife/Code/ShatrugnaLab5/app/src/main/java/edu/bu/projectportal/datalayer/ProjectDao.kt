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
    suspend fun addProject(project: Project)

    @Delete
    fun delProject(project: Project)

    @Update
    suspend fun editProject(project: Project)

    @Query("SELECT count(*) From projects")
    fun count(): Int

    @Query("SELECT * FROM projects")
    suspend fun getAllProjects(): List<Project>

    @Query("SELECT * FROM projects where favourite=1")
    suspend fun getAllFavouriteProjects(): List<Project>

    @Query("SELECT * FROM projects where id = :projId")
    suspend fun searchProjectById(projId: Long): Project

    @Query("SELECT * FROM projects WHERE title like :projTitle ")
    fun searchProjectsByTitle(projTitle:String): List<Project>

    @Update
    suspend fun updateProject(project:Project)
}