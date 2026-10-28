package com.example.data.models

data class DarkNiche(
    val id: String,
    val name: String,
    val description: String,
    val iconName: String,
    val avgCpm: String,
    val viralPotential: String,
    val defaultKeywords: List<String>
)

data class DarkVideoDraft(
    val title: String,
    val hook: String,
    val script: String,
    val visualPrompts: List<String>,
    val tags: List<String>,
    val description: String,
    val durationSeconds: Int,
    val niche: String
)

data class YouTubeApiConfig(
    val apiKey: String = "",
    val channelId: String = "",
    val autoPublish: Boolean = false,
    val defaultPrivacy: String = "private", // private, unlisted, public
    val scheduleTime: String = "18:00",
    val webhookUrl: String = ""
)

data class YouTubeChannelStats(
    val channelTitle: String = "",
    val subscriberCount: String = "",
    val viewCount: String = "",
    val videoCount: String = "",
    val thumbnailUrl: String = "",
    val isConnected: Boolean = false,
    val quotaUsedToday: Int = 0,
    val quotaLimit: Int = 10000
)

data class WorkflowStep(
    val stepNumber: Int,
    val title: String,
    val tool: String,
    val description: String,
    val samplePayload: String
)

data class TutorialChapter(
    val timeLabel: String,
    val title: String,
    val description: String
)
