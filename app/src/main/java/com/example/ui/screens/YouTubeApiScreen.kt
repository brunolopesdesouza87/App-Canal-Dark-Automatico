package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.MainViewModel
import com.example.ui.YouTubeApiUiState
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YouTubeApiScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val config by viewModel.ytConfig.collectAsState()
    val uiState by viewModel.apiUiState.collectAsState()

    fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .testTag("youtube_api_screen_scroll"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "INTEGRAÇÃO OFICIAL",
                    color = TechCyan,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Conexão YouTube API v3",
                    color = TextPrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "Configure suas credenciais do Google Cloud para habilitar o upload 100% automático dos vídeos renderizados.",
                    color = TextSecondary,
                    fontSize = 14.sp
                )
            }
        }

        // Credentials Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BorderStroke, RoundedCornerShape(16.dp))
                    .testTag("credentials_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Credenciais do Google Cloud",
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    OutlinedTextField(
                        value = config.apiKey,
                        onValueChange = { viewModel.updateApiKey(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("api_key_input"),
                        label = { Text("Chave da API (YouTube Data v3)") },
                        placeholder = { Text("AIzaSy...") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TechCyan,
                            unfocusedBorderColor = BorderHighlight,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedLabelColor = TechCyan,
                            unfocusedLabelColor = TextSecondary
                        ),
                        trailingIcon = {
                            IconButton(onClick = {
                                openUrl("https://console.cloud.google.com/apis/credentials")
                            }) {
                                Icon(
                                    Icons.Filled.HelpOutline,
                                    contentDescription = "Ajuda Google Cloud",
                                    tint = TextSecondary
                                )
                            }
                        }
                    )

                    OutlinedTextField(
                        value = config.channelId,
                        onValueChange = { viewModel.updateChannelId(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("channel_id_input"),
                        label = { Text("Channel ID (Opcional)") },
                        placeholder = { Text("UC1234567890abcdef...") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TechCyan,
                            unfocusedBorderColor = BorderHighlight,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedLabelColor = TechCyan,
                            unfocusedLabelColor = TextSecondary
                        )
                    )

                    OutlinedTextField(
                        value = config.webhookUrl,
                        onValueChange = { viewModel.updateWebhookUrl(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("webhook_input"),
                        label = { Text("Webhook do n8n / Make") },
                        placeholder = { Text("https://seu-servidor/webhook/...") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TechCyan,
                            unfocusedBorderColor = BorderHighlight,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedLabelColor = TechCyan,
                            unfocusedLabelColor = TextSecondary
                        )
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { openUrl("https://console.cloud.google.com/apis/library/youtube.googleapis.com") },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.OpenInNew,
                            contentDescription = null,
                            tint = TechCyan,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "Ativar YouTube Data API v3 no Google Cloud Console",
                            color = TechCyan,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Button(
                        onClick = { viewModel.testYouTubeConnection() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("test_api_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = TechCyan),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (uiState is YouTubeApiUiState.Loading) {
                            CircularProgressIndicator(
                                color = DarkBackground,
                                modifier = Modifier.size(22.dp),
                                strokeWidth = 2.5.dp
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Testando Conexão...", color = DarkBackground, fontWeight = FontWeight.Bold)
                        } else {
                            Icon(
                                Icons.Filled.CloudDone,
                                contentDescription = null,
                                tint = DarkBackground,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "TESTAR CONEXÃO DA API",
                                color = DarkBackground,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }

        // Connection Test Result Feedback
        item {
            AnimatedVisibility(visible = uiState !is YouTubeApiUiState.Idle) {
                when (val state = uiState) {
                    is YouTubeApiUiState.Success -> {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, MetricGreen, RoundedCornerShape(16.dp))
                                .testTag("api_success_card"),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Filled.CheckCircle,
                                        contentDescription = null,
                                        tint = MetricGreen,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        "API Conectada com Sucesso!",
                                        color = MetricGreen,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                }

                                Divider(color = BorderStroke)

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text("Status do Canal", color = TextSecondary, fontSize = 12.sp)
                                        Text(state.stats.channelTitle, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text("Cota Diária Gratuita", color = TextSecondary, fontSize = 12.sp)
                                        Text("${state.stats.quotaLimit} unidades", color = CyberGold, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    }
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text("Inscritos", color = TextSecondary, fontSize = 12.sp)
                                        Text(state.stats.subscriberCount, color = TechCyan, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text("Visualizações", color = TextSecondary, fontSize = 12.sp)
                                        Text(state.stats.viewCount, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    }
                                }
                            }
                        }
                    }
                    is YouTubeApiUiState.Error -> {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, NeonRed, RoundedCornerShape(16.dp))
                                .testTag("api_error_card"),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    Icons.Filled.ErrorOutline,
                                    contentDescription = null,
                                    tint = NeonRed,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        "Falha na Verificação da API",
                                        color = NeonRed,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        state.message,
                                        color = TextSecondary,
                                        fontSize = 13.sp,
                                        lineHeight = 18.sp
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        "Dica: Certifique-se de que a 'YouTube Data API v3' foi ativada na biblioteca de APIs do Google Cloud.",
                                        color = TechCyan,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }
                    else -> {}
                }
            }
        }

        // Automation Settings
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BorderStroke, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Configuração do Upload Automático",
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Publicação Automática Imediata", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("Publica direto sem salvar como rascunho", color = TextSecondary, fontSize = 12.sp)
                        }
                        Switch(
                            checked = config.autoPublish,
                            onCheckedChange = { viewModel.updateAutoPublish(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = TechCyan
                            )
                        )
                    }

                    Divider(color = BorderStroke)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Horário de Pico Automático", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("Melhor horário de retenção no YouTube Brasil", color = TextSecondary, fontSize = 12.sp)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(DarkSurfaceVariant)
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(config.scheduleTime, color = TechCyan, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }
            }
        }

        // Quota & Limits Info Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Filled.Speed,
                            contentDescription = null,
                            tint = CyberGold,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Consumo da Cota da YouTube API",
                            color = CyberGold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                    Text(
                        "• O Google concede 10.000 unidades diárias gratuitas por projeto.\n• Cada upload de vídeo consome 50 unidades.\n• Isso permite subir até 200 vídeos por dia sem custos com a API do YouTube.",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}
