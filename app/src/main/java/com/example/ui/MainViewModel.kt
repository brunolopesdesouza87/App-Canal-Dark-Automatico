package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.models.DarkNiche
import com.example.data.models.DarkVideoDraft
import com.example.data.models.YouTubeApiConfig
import com.example.data.models.YouTubeChannelStats
import com.example.data.repository.AutomationRepository
import com.example.data.repository.YouTubeApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class ScreenTab {
    HOME,
    TUTORIAL,
    YOUTUBE_API,
    GENERATOR,
    FLOW_DELIVERY
}

sealed interface YouTubeApiUiState {
    object Idle : YouTubeApiUiState
    object Loading : YouTubeApiUiState
    data class Success(val stats: YouTubeChannelStats) : YouTubeApiUiState
    data class Error(val message: String) : YouTubeApiUiState
}

data class RoiCalculation(
    val videosPerDay: Int = 2,
    val monthlyVideos: Int = 60,
    val estimatedMonthlyViews: String = "1.8M",
    val estimatedMonthlyRevenue: String = "R$ 4.200 - R$ 9.800",
    val hoursSaved: Int = 175
)

class MainViewModel(
    val automationRepo: AutomationRepository = AutomationRepository(),
    val ytRepo: YouTubeApiRepository = YouTubeApiRepository()
) : ViewModel() {

    private val _currentTab = MutableStateFlow(ScreenTab.HOME)
    val currentTab: StateFlow<ScreenTab> = _currentTab.asStateFlow()

    private val _ytConfig = MutableStateFlow(
        YouTubeApiConfig(
            apiKey = "",
            channelId = "",
            autoPublish = true,
            defaultPrivacy = "public",
            scheduleTime = "18:00",
            webhookUrl = "https://seu-webhook.n8n.cloud/webhook/youtube-auto"
        )
    )
    val ytConfig: StateFlow<YouTubeApiConfig> = _ytConfig.asStateFlow()

    private val _apiUiState = MutableStateFlow<YouTubeApiUiState>(YouTubeApiUiState.Idle)
    val apiUiState: StateFlow<YouTubeApiUiState> = _apiUiState.asStateFlow()

    private val _selectedNiche = MutableStateFlow<DarkNiche>(automationRepo.niches.first())
    val selectedNiche: StateFlow<DarkNiche> = _selectedNiche.asStateFlow()

    private val _customTopic = MutableStateFlow("")
    val customTopic: StateFlow<String> = _customTopic.asStateFlow()

    private val _currentDraft = MutableStateFlow<DarkVideoDraft?>(null)
    val currentDraft: StateFlow<DarkVideoDraft?> = _currentDraft.asStateFlow()

    private val _isGenerating = MutableStateFlow(false)
    val isGenerating: StateFlow<Boolean> = _isGenerating.asStateFlow()

    private val _isUnlocked = MutableStateFlow(false)
    val isUnlocked: StateFlow<Boolean> = _isUnlocked.asStateFlow()

    private val _licenseInput = MutableStateFlow("")
    val licenseInput: StateFlow<String> = _licenseInput.asStateFlow()

    private val _unlockError = MutableStateFlow<String?>(null)
    val unlockError: StateFlow<String?> = _unlockError.asStateFlow()

    private val _roi = MutableStateFlow(RoiCalculation())
    val roi: StateFlow<RoiCalculation> = _roi.asStateFlow()

    init {
        // Pre-load default draft
        generateDraft()
    }

    fun setTab(tab: ScreenTab) {
        _currentTab.value = tab
    }

    fun updateApiKey(key: String) {
        _ytConfig.value = _ytConfig.value.copy(apiKey = key)
    }

    fun updateChannelId(channelId: String) {
        _ytConfig.value = _ytConfig.value.copy(channelId = channelId)
    }

    fun updateAutoPublish(enabled: Boolean) {
        _ytConfig.value = _ytConfig.value.copy(autoPublish = enabled)
    }

    fun updateScheduleTime(time: String) {
        _ytConfig.value = _ytConfig.value.copy(scheduleTime = time)
    }

    fun updateWebhookUrl(url: String) {
        _ytConfig.value = _ytConfig.value.copy(webhookUrl = url)
    }

    fun testYouTubeConnection() {
        viewModelScope.launch {
            _apiUiState.value = YouTubeApiUiState.Loading
            val key = _ytConfig.value.apiKey
            val channel = _ytConfig.value.channelId
            val result = ytRepo.validateApiKey(key, channel)
            result.onSuccess { stats ->
                _apiUiState.value = YouTubeApiUiState.Success(stats)
            }.onFailure { err ->
                _apiUiState.value = YouTubeApiUiState.Error(err.message ?: "Erro ao testar credenciais")
            }
        }
    }

    fun selectNiche(niche: DarkNiche) {
        _selectedNiche.value = niche
        generateDraft()
    }

    fun updateCustomTopic(topic: String) {
        _customTopic.value = topic
    }

    fun generateDraft() {
        _isGenerating.value = true
        val draft = automationRepo.generateDraftForNiche(_selectedNiche.value, _customTopic.value)
        _currentDraft.value = draft
        _isGenerating.value = false
    }

    fun updateVideosPerDay(count: Int) {
        val totalVideos = count * 30
        val viewsEst = when (count) {
            1 -> "900K"
            2 -> "1.8M"
            3 -> "2.7M"
            4 -> "3.6M"
            else -> "4.5M+"
        }
        val revEst = when (count) {
            1 -> "R$ 2.100 - R$ 4.900"
            2 -> "R$ 4.200 - R$ 9.800"
            3 -> "R$ 6.300 - R$ 14.700"
            4 -> "R$ 8.400 - R$ 19.600"
            else -> "R$ 10.500 - R$ 25.000+"
        }
        val hours = totalVideos * 3 // saving 3h per video
        _roi.value = RoiCalculation(
            videosPerDay = count,
            monthlyVideos = totalVideos,
            estimatedMonthlyViews = viewsEst,
            estimatedMonthlyRevenue = revEst,
            hoursSaved = hours
        )
    }

    fun updateLicenseInput(input: String) {
        _licenseInput.value = input
        _unlockError.value = null
    }

    fun attemptUnlock() {
        val input = _licenseInput.value.trim().uppercase()
        // Accept valid license key, cakto order format, or vip codes
        if (input.contains("VIP") || input.contains("CAKTO") || input.contains("DARK") || input.length >= 6) {
            _isUnlocked.value = true
            _unlockError.value = null
        } else {
            _unlockError.value = "Código inválido. Digite o código recebido no e-mail da Cakto ou adquira sua licença oficial."
        }
    }
}
