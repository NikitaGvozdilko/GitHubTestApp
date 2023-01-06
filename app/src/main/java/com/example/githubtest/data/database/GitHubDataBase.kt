package com.example.githubtest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubtest.data.database.GitHubDataBase.Companion.VERSION
import com.example.githubtest.data.database.dao.RepoHistoryDao
import com.example.githubtest.data.database.entity.OpenedRepoEntity

@Database(
    entities = [
        OpenedRepoEntity::class
    ],
    version = VERSION
)
abstract class GitHubDataBase() : RoomDatabase() {

    abstract val repoHistoryDao: RepoHistoryDao

    companion object {
        const val NAME = "GitHubDatabase"
        const val VERSION = 1
    }
}