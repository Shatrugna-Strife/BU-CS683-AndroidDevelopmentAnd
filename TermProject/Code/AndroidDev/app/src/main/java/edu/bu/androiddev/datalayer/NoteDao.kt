package edu.bu.androiddev.datalayer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Insert
    fun addNote(note:Note)
    @Insert
    fun addPage(page:Pages):Long

    @Update
    suspend fun editNote(note:Note)
    @Update
    suspend fun editPage(page:Pages)

    @Query("DELETE FROM pages WHERE id = :id")
    fun deletePagesWithId(id:Long)

    @Query("DELETE FROM notes WHERE id = :id")
    fun deleteNotesWithId(id:Long)

    @Query("SELECT count(*) FROM notes")
    fun count(): Int

    @Query("SELECT count(*) FROM pages")
    fun countOfPages(): Int

    @Query("SELECT * FROM notes")
    fun getAllNotes():List<Note>

    @Query("SELECT * FROM pages WHERE parent LIKE :name")
    fun getPageWithParentName(name:String?):List<Pages>

    @Query("DELETE FROM notes")
    fun deleteAllNotes()

    @Query("DELETE FROM pages")
    fun deleteAllPages()
    
    @Update
    fun updatePages(pages:Pages)

}