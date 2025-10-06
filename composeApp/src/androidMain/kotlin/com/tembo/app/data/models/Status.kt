package com.tembo.app.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Status(
    val id: String,
    val content: String,
    val account: Account,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("media_attachments")
    val mediaAttachments: List<MediaAttachment> = emptyList(),
    @SerialName("favourites_count")
    val favouritesCount: Int = 0,
    @SerialName("reblogs_count")
    val reblogsCount: Int = 0,
    @SerialName("replies_count")
    val repliesCount: Int = 0,
    val favourited: Boolean = false,
    val reblogged: Boolean = false
)

@Serializable
data class Account(
    val id: String,
    val username: String,
    @SerialName("display_name")
    val displayName: String,
    val avatar: String,
    val acct: String
)

@Serializable
data class MediaAttachment(
    val id: String,
    val type: String,
    val url: String,
    @SerialName("preview_url")
    val previewUrl: String? = null,
    val description: String? = null
)