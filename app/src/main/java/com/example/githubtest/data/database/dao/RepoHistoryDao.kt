package com.example.githubtest.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubtest.data.database.entity.OpenedRepoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RepoHistoryDao {

    @Query("SELECT * FROM openedRepo")
    fun getOpenedRepositories(): Flow<List<OpenedRepoEntity>>

    @Query("SELECT * FROM openedRepo ORDER BY openedAt DESC LIMIT 20")
    fun getOpenedRepositoriesSorted(): Flow<List<OpenedRepoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: OpenedRepoEntity)
}