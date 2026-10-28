package com.example.data.repository

import com.example.data.models.YouTubeChannelStats
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class YouTubeApiRepository {
    private val client = OkHttpClient.Builder()
        .connectTimeout(12, TimeUnit.SECONDS)
        .readTimeout(12, TimeUnit.SECONDS)
        .build()

    suspend fun validateApiKey(apiKey: String, channelId: String = ""): Result<YouTubeChannelStats> =
        withContext(Dispatchers.IO) {
            try {
                if (apiKey.isBlank()) {
                    return@withContext Result.failure(IllegalArgumentException("Chave da API do YouTube não pode estar vazia."))
                }

                val url = if (channelId.isNotBlank()) {
                    "https://www.googleapis.com/youtube/v3/channels?part=snippet,statistics&id=${channelId.trim()}&key=${apiKey.trim()}"
                } else {
                    "https://www.googleapis.com/youtube/v3/videos?part=snippet&chart=mostPopular&maxResults=1&key=${apiKey.trim()}"
                }

                val request = Request.Builder()
                    .url(url)
                    .header("User-Agent", "CanalDarkAutomator/1.0")
                    .build()

                val response = client.newCall(request).execute()
                val body = response.body?.string().orEmpty()

                if (!response.isSuccessful) {
                    val errorMsg = try {
                        val json = JSONObject(body)
                        val errorObj = json.optJSONObject("error")
                        val message = errorObj?.optString("message")
                        val code = errorObj?.optInt("code", response.code)
                        "Erro API ($code): $message"
                    } catch (e: Exception) {
                        "Erro de resposta HTTP ${response.code}: $body"
                    }
                    return@withContext Result.failure(Exception(errorMsg))
                }

                val json = JSONObject(body)
                val items = json.optJSONArray("items")

                if (channelId.isNotBlank() && items != null && items.length() > 0) {
                    val item = items.getJSONObject(0)
                    val snippet = item.optJSONObject("snippet")
                    val statistics = item.optJSONObject("statistics")

                    val title = snippet?.optString("title", "Canal Dark Conectado") ?: "Canal Dark"
                    val subs = statistics?.optString("subscriberCount", "N/A") ?: "N/A"
                    val views = statistics?.optString("viewCount", "N/A") ?: "N/A"
                    val videos = statistics?.optString("videoCount", "N/A") ?: "N/A"
                    val thumb = snippet?.optJSONObject("thumbnails")?.optJSONObject("default")?.optString("url", "") ?: ""

                    return@withContext Result.success(
                        YouTubeChannelStats(
                            channelTitle = title,
                            subscriberCount = formatNumber(subs),
                            viewCount = formatNumber(views),
                            videoCount = videos,
                            thumbnailUrl = thumb,
                            isConnected = true,
                            quotaUsedToday = 1,
                            quotaLimit = 10000
                        )
                    )
                } else {
                    return@withContext Result.success(
                        YouTubeChannelStats(
                            channelTitle = "API Conectada com Sucesso",
                            subscriberCount = "Ativo",
                            viewCount = "10.000 req/dia",
                            videoCount = "Pronto para Upload",
                            thumbnailUrl = "",
                            isConnected = true,
                            quotaUsedToday = 1,
                            quotaLimit = 10000
                        )
                    )
                }
            } catch (e: Exception) {
                Result.failure(Exception("Falha na conexão: ${e.localizedMessage ?: "Verifique sua internet ou a chave API"}"))
            }
        }

    private fun formatNumber(raw: String): String {
        val num = raw.toLongOrNull() ?: return raw
        return when {
            num >= 1_000_000 -> String.format("%.1fM", num / 1_000_000.0)
            num >= 1_000 -> String.format("%.1fK", num / 1_000.0)
            else -> raw
        }
    }
}
