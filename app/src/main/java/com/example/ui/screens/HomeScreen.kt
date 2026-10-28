package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.MainViewModel
import com.example.ui.ScreenTab
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    onNavigateTab: (ScreenTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val roi by viewModel.roi.collectAsState()
    val isUnlocked by viewModel.isUnlocked.collectAsState()

    fun openBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .testTag("home_screen_scroll"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Hero Image with Glow & Overlay
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BorderHighlight, RoundedCornerShape(20.dp))
                    .testTag("hero_banner_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(230.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.canal_dark_banner),
                            contentDescription = "Canal Dark Automático Banner",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            DarkBackground.copy(alpha = 0.85f),
                                            DarkBackground
                                        )
                                    )
                                )
                        )
                        // Floating Badge
                        Row(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(12.dp)
                                .clip(RoundedCornerShape(30.dp))
                                .background(NeonRed.copy(alpha = 0.9f))
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Filled.Bolt,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                "100% AUTOMATIZADO",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "CANAL DARK AUTOMÁTICO",
                            color = TextPrimary,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "Crie, publique e monetize vídeos no YouTube no piloto automático sem precisar aparecer ou gravar sua voz.",
                            color = TextSecondary,
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        // Primary Checkout CTA
                        Button(
                            onClick = { openBrowser(viewModel.automationRepo.checkoutUrl) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .testTag("checkout_cta_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = NeonRed
                            ),
                            shape = RoundedCornerShape(14.dp),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                        ) {
                            Icon(
                                Icons.Filled.ShoppingCart,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "ADQUIRIR FLUXO COMPLETO",
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 15.sp
                            )
                        }

                        // Guarantee & Security Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Filled.Verified,
                                    contentDescription = null,
                                    tint = MetricGreen,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    "Garantia 7 Dias",
                                    color = TextSecondary,
                                    fontSize = 12.sp
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Filled.Lock,
                                    contentDescription = null,
                                    tint = CyberGold,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    "Acesso Imediato Cakto",
                                    color = TextSecondary,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Demo Video Showcase Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { openBrowser(viewModel.automationRepo.demoVideoUrl) }
                    .border(1.dp, TechCyan.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                    .testTag("demo_video_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(NeonRed.copy(alpha = 0.2f))
                            .border(1.dp, NeonRed, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Filled.PlayArrow,
                            contentDescription = "Assistir Vídeo Tutorial",
                            tint = NeonRed,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "VÍDEO EXPLICATIVO",
                                color = TechCyan,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                "08:45 min",
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }
                        Text(
                            "Veja a Máquina de Vídeos em Ação",
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                        Text(
                            "Clique para assistir no YouTube como o fluxo gera e publica vídeos sozinho.",
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                    }
                    Icon(
                        Icons.Filled.ChevronRight,
                        contentDescription = null,
                        tint = TextSecondary
                    )
                }
            }
        }

        // Features Grid (Why it dominates)
        item {
            Text(
                text = "O Que Você Recebe No Fluxo:",
                color = TextPrimary,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                FeatureRow(
                    icon = Icons.Filled.Hub,
                    iconColor = TechCyan,
                    title = "Fluxo Pronto para n8n & Make",
                    subtitle = "Basta importar o arquivo JSON pré-configurado com todos os nós e gatilhos conectados."
                )
                FeatureRow(
                    icon = Icons.Filled.UploadFile,
                    iconColor = NeonRed,
                    title = "Integração Direta YouTube API v3",
                    subtitle = "Automação oficial do Google Cloud: faz upload automático com título, tags e agendamento."
                )
                FeatureRow(
                    icon = Icons.Filled.RecordVoiceOver,
                    iconColor = CyberGold,
                    title = "Voz Neural & Roteiro Magnético",
                    subtitle = "Prompts testados para ultrapassar 80% de retenção no Shorts e vídeos longos de documentário."
                )
                FeatureRow(
                    icon = Icons.Filled.Speed,
                    iconColor = MetricGreen,
                    title = "De 6 Horas para 3 Minutos",
                    subtitle = "Elimine todo o trabalho manual de pesquisa, edição, legenda e subida manual de arquivos."
                )
            }
        }

        // Interactive ROI Calculator
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CyberGold.copy(alpha = 0.4f), RoundedCornerShape(18.dp))
                    .testTag("roi_calculator_card"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Simulador de Escala & Receita",
                            color = CyberGold,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "YouTube AdSense",
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }

                    Text(
                        "Quantos vídeos automáticos por dia?",
                        color = TextPrimary,
                        fontSize = 13.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(1, 2, 3, 4, 5).forEach { count ->
                            val isSelected = roi.videosPerDay == count
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(42.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) CyberGold else DarkSurfaceVariant)
                                    .clickable { viewModel.updateVideosPerDay(count) }
                                    .testTag("roi_option_$count"),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "$count/dia",
                                    color = if (isSelected) DarkBackground else TextPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }

                    Divider(color = BorderStroke)

                    // Results
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Vídeos / Mês", color = TextSecondary, fontSize = 12.sp)
                            Text("${roi.monthlyVideos} vídeos", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                        Column {
                            Text("Alcance Estimado", color = TextSecondary, fontSize = 12.sp)
                            Text(roi.estimatedMonthlyViews, color = TechCyan, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Tempo Poupado", color = TextSecondary, fontSize = 12.sp)
                            Text("${roi.hoursSaved}h / mês", color = MetricGreen, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(MetricGreenDim)
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Faturamento Estimado:", color = TextPrimary, fontSize = 13.sp)
                            Text(roi.estimatedMonthlyRevenue, color = MetricGreen, fontWeight = FontWeight.Black, fontSize = 15.sp)
                        }
                    }
                }
            }
        }

        // Quick Navigation Tiles to inside tools
        item {
            Text(
                text = "Ferramentas & Acesso:",
                color = TextPrimary,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickNavCard(
                    title = "API YouTube v3",
                    subtitle = "Testar conexão",
                    icon = Icons.Filled.Api,
                    accentColor = TechCyan,
                    modifier = Modifier.weight(1f)
                ) {
                    onNavigateTab(ScreenTab.YOUTUBE_API)
                }
                QuickNavCard(
                    title = "Roteirizador",
                    subtitle = "Gerar Roteiros",
                    icon = Icons.Filled.AutoAwesome,
                    accentColor = CyberGold,
                    modifier = Modifier.weight(1f)
                ) {
                    onNavigateTab(ScreenTab.GENERATOR)
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickNavCard(
                    title = "Vídeo Tutorial",
                    subtitle = "Como funciona",
                    icon = Icons.Filled.PlayCircle,
                    accentColor = NeonRed,
                    modifier = Modifier.weight(1f)
                ) {
                    onNavigateTab(ScreenTab.TUTORIAL)
                }
                QuickNavCard(
                    title = "Baixar Fluxo",
                    subtitle = if (isUnlocked) "Desbloqueado" else "Acessar JSON",
                    icon = Icons.Filled.FileDownload,
                    accentColor = MetricGreen,
                    modifier = Modifier.weight(1f)
                ) {
                    onNavigateTab(ScreenTab.FLOW_DELIVERY)
                }
            }
        }

        // Bottom CTA Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { openBrowser(viewModel.automationRepo.checkoutUrl) }
                    .border(1.dp, NeonRed.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                    .testTag("bottom_checkout_banner"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.LocalFireDepartment,
                        contentDescription = null,
                        tint = NeonRed,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Oferta Especial de Lançamento",
                            color = NeonRed,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Garanta seu Fluxo Completo na Cakto",
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            "Receba imediatamente o JSON e a aula no seu e-mail.",
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                    }
                    Icon(
                        Icons.Filled.OpenInNew,
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun FeatureRow(
    icon: ImageVector,
    iconColor: Color,
    title: String,
    subtitle: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(iconColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    color = TextSecondary,
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
            }
        }
    }
}

@Composable
private fun QuickNavCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(100.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(accentColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        icon,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Icon(
                    Icons.Filled.ArrowForward,
                    contentDescription = null,
                    tint = TextMuted,
                    modifier = Modifier.size(14.dp)
                )
            }
            Column {
                Text(
                    text = title,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
                Text(
                    text = subtitle,
                    color = TextSecondary,
                    fontSize = 11.sp
                )
            }
        }
    }
}
