package com.example.githubtest.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "openedRepo")
class OpenedRepoEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val openedAt: Long
)