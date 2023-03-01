package com.example.githubtest.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RepositoryDto(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("full_name")
    @Expose
    val fullName: String? = null,
    @SerializedName("private")
    @Expose
    val isPrivate: Boolean = false,
    @SerializedName("html_url")
    @Expose
    val htmlUrl: String? = null,
    @SerializedName("description")
    @Expose
    val description: String? = null,
    @SerializedName("fork")
    @Expose
    val isFork: Boolean = false,
    @SerializedName("url")
    @Expose
    val url: String? = null,
    @SerializedName("forks_url")
    @Expose
    val forksUrl: String? = null,
    @SerializedName("keys_url")
    @Expose
    val keysUrl: String? = null,
    @SerializedName("collaborators_url")
    @Expose
    val collaboratorsUrl: String? = null,
    @SerializedName("teams_url")
    @Expose
    val teamsUrl: String? = null,
    @SerializedName("hooks_url")
    @Expose
    val hooksUrl: String? = null,
    @SerializedName("issue_events_url")
    @Expose
    val issueEventsUrl: String? = null,
    @SerializedName("events_url")
    @Expose
    val eventsUrl: String? = null,
    @SerializedName("assignees_url")
    @Expose
    val assigneesUrl: String? = null,
    @SerializedName("branches_url")
    @Expose
    val branchesUrl: String? = null,
    @SerializedName("tags_url")
    @Expose
    val tagsUrl: String? = null,
    @SerializedName("blobs_url")
    @Expose
    val blobsUrl: String? = null,
    @SerializedName("git_tags_url")
    @Expose
    val gitTagsUrl: String? = null,
    @SerializedName("git_refs_url")
    @Expose
    val gitRefsUrl: String? = null,
    @SerializedName("trees_url")
    @Expose
    val treesUrl: String? = null,
    @SerializedName("statuses_url")
    @Expose
    val statusesUrl: String? = null,
    @SerializedName("languages_url")
    @Expose
    val languagesUrl: String? = null,
    @SerializedName("stargazers_url")
    @Expose
    val stargazersUrl: String? = null,
    @SerializedName("contributors_url")
    @Expose
    val contributorsUrl: String? = null,
    @SerializedName("subscribers_url")
    @Expose
    val subscribersUrl: String? = null,
    @SerializedName("subscription_url")
    @Expose
    val subscriptionUrl: String? = null,
    @SerializedName("commits_url")
    @Expose
    val commitsUrl: String? = null,
    @SerializedName("git_commits_url")
    @Expose
    val gitCommitsUrl: String? = null,
//    @SerializedName("comments_url")
//    @Expose
//    val commentsUrl: String? = null
//    @SerializedName("issue_comment_url")
//    @Expose
//    val issueCommentUrl: String? = null
//    @SerializedName("contents_url")
//    @Expose
//    val contentsUrl: String? = null
//    @SerializedName("compare_url")
//    @Expose
//    val compareUrl: String? = null
//    @SerializedName("merges_url")
//    @Expose
//    val mergesUrl: String? = null
//    @SerializedName("archive_url")
//    @Expose
//    val archiveUrl: String? = null
//    @SerializedName("downloads_url")
//    @Expose
//    val downloadsUrl: String? = null
//    @SerializedName("issues_url")
//    @Expose
//    val issuesUrl: String? = null
//    @SerializedName("pulls_url")
//    @Expose
//    val pullsUrl: String? = null
//    @SerializedName("milestones_url")
//    @Expose
//    val milestonesUrl: String? = null
//    @SerializedName("notifications_url")
//    @Expose
//    val notificationsUrl: String? = null
//    @SerializedName("labels_url")
//    @Expose
//    val labelsUrl: String? = null
//    @SerializedName("releases_url")
//    @Expose
//    val releasesUrl: String? = null
//    @SerializedName("deployments_url")
//    @Expose
//    val deploymentsUrl: String? = null
    @SerializedName("created_at")
    @Expose
    val createdAt: String? = null
//    @SerializedName("updated_at")
//    @Expose
//    val updatedAt: String? = null
//    @SerializedName("pushed_at")
//    @Expose
//    val pushedAt: String? = null
//    @SerializedName("git_url")
//    @Expose
//    val gitUrl: String? = null
//    @SerializedName("ssh_url")
//    @Expose
//    val sshUrl: String? = null
//    @SerializedName("clone_url")
//    @Expose
//    val cloneUrl: String? = null
//    @SerializedName("svn_url")
//    @Expose
//    val svnUrl: String? = null
//    @SerializedName("homepage")
//    @Expose
//    val homepage: String? = null
//    @SerializedName("size")
//    @Expose
//    val size: Int = 0
//    @SerializedName("stargazers_count")
//    @Expose
//    val stargazersCount: Int = 0
//    @SerializedName("watchers_count")
//    @Expose
//    val watchersCount: Int = 0
//    @SerializedName("language")
//    @Expose
//    val language: String? = null
//    @SerializedName("has_issues")
//    @Expose
//    val isHasIssues: Boolean = false
//    @SerializedName("has_projects")
//    @Expose
//    val isHasProjects: Boolean = false
//    @SerializedName("has_downloads")
//    @Expose
//    val isHasDownloads: Boolean = false
//    @SerializedName("has_wiki")
//    @Expose
//    val isHasWiki: Boolean = false
//    @SerializedName("has_pages")
//    @Expose
//    val isHasPages: Boolean = false
//    @SerializedName("forks_count")
//    @Expose
//    val forksCount: Int = 0
//    @SerializedName("mirror_url")
//    @Expose
//    val mirrorUrl: Any? = null
//    @SerializedName("archived")
//    @Expose
//    val isArchived: Boolean = false
//    @SerializedName("open_issues_count")
//    @Expose
//    val openIssuesCount: Int = 0
//    @SerializedName("forks")
//    @Expose
//    val forks: Int = 0
//    @SerializedName("open_issues")
//    @Expose
//    val openIssues: Int = 0
//    @SerializedName("watchers")
//    @Expose
//    val watchers: Int = 0
//    @SerializedName("default_branch")
//    @Expose
//    val defaultBranch: String? = null
//    @SerializedName("score")
//    @Expose
//    val score: Float = 0.toFloat()
)