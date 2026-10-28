package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.example.data.models.DarkVideoDraft
import com.example.ui.MainViewModel
import com.example.ui.theme.*

@Composable
fun GeneratorScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val niches = viewModel.automationRepo.niches
    val selectedNiche by viewModel.selectedNiche.collectAsState()
    val customTopic by viewModel.customTopic.collectAsState()
    val draft by viewModel.currentDraft.collectAsState()
    val isGenerating by viewModel.isGenerating.collectAsState()

    fun copyToClipboard(label: String, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "$label copiado para a área de transferência!", Toast.LENGTH_SHORT).show()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .testTag("generator_screen_scroll"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "ESTÚDIO DE CONTEÚDO",
                    color = CyberGold,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Gerador de Roteiros Dark",
                    color = TextPrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "Estruture vídeos magnéticos com retenção comprovada e prompts de cena para renderização.",
                    color = TextSecondary,
                    fontSize = 14.sp
                )
            }
        }

        // Niche Selector Row
        item {
            Text(
                text = "Escolha o Nicho Lucrativo:",
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(niches) { niche ->
                    val isSelected = niche.id == selectedNiche.id
                    Card(
                        modifier = Modifier
                            .clickable { viewModel.selectNiche(niche) }
                            .testTag("niche_item_${niche.id}"),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) CyberGold.copy(alpha = 0.2f) else DarkSurfaceCard
                        ),
                        border = if (isSelected) ButtonDefaults.outlinedButtonBorder.copy(
                            brush = androidx.compose.ui.graphics.SolidColor(CyberGold)
                        ) else ButtonDefaults.outlinedButtonBorder.copy(
                            brush = androidx.compose.ui.graphics.SolidColor(BorderStroke)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                        ) {
                            Text(
                                niche.name,
                                color = if (isSelected) CyberGold else TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                            Text(
                                "CPM Médio: ${niche.avgCpm}",
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }

        // Topic Input & Generate Button
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = customTopic,
                        onValueChange = { viewModel.updateCustomTopic(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("topic_input"),
                        label = { Text("Tema do Vídeo (Opcional)") },
                        placeholder = { Text("Ex: O enigma da cidade submersa...") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CyberGold,
                            unfocusedBorderColor = BorderHighlight,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedLabelColor = CyberGold,
                            unfocusedLabelColor = TextSecondary
                        )
                    )

                    Button(
                        onClick = { viewModel.generateDraft() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("generate_draft_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = CyberGold),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            Icons.Filled.AutoAwesome,
                            contentDescription = null,
                            tint = DarkBackground,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "GERAR ROTEIRO COMPLETO",
                            color = DarkBackground,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        // Generated Draft Output
        draft?.let { currentDraft ->
            // Title Card
            item {
                ContentCard(
                    tag = "TÍTULO VIRAL (ALTO CTR)",
                    content = currentDraft.title,
                    tagColor = NeonRed,
                    onCopy = { copyToClipboard("Título", currentDraft.title) }
                )
            }

            // Hook Card
            item {
                ContentCard(
                    tag = "GANCHO INICIAL (PRIMEIROS 3 SEGUNDOS)",
                    content = currentDraft.hook,
                    tagColor = TechCyan,
                    onCopy = { copyToClipboard("Gancho", currentDraft.hook) }
                )
            }

            // Script Card
            item {
                ContentCard(
                    tag = "ROTEIRO NARRATIVO COMPLETO",
                    content = currentDraft.script,
                    tagColor = CyberGold,
                    onCopy = { copyToClipboard("Roteiro", currentDraft.script) }
                )
            }

            // Visual B-Roll Prompts Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "PROMPTS DE CENAS (B-ROLL / MIDJOURNEY)",
                                color = MetricGreen,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                            IconButton(onClick = {
                                copyToClipboard("Prompts", currentDraft.visualPrompts.joinToString("\n\n"))
                            }) {
                                Icon(Icons.Filled.ContentCopy, contentDescription = "Copiar", tint = TextSecondary)
                            }
                        }

                        currentDraft.visualPrompts.forEachIndexed { index, prompt ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(DarkSurfaceVariant)
                                    .padding(10.dp)
                            ) {
                                Column {
                                    Text("Cena ${index + 1}:", color = MetricGreen, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(prompt, color = TextPrimary, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }

            // Tags & SEO Card
            item {
                ContentCard(
                    tag = "TAGS & METADADOS RANQUEADOS",
                    content = currentDraft.tags.joinToString(", ") + "\n\n" + currentDraft.description,
                    tagColor = TechCyan,
                    onCopy = { copyToClipboard("Tags e Descrição", currentDraft.tags.joinToString(", ") + "\n\n" + currentDraft.description) }
                )
            }
        }
    }
}

@Composable
private fun ContentCard(
    tag: String,
    content: String,
    tagColor: Color,
    onCopy: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = tag,
                    color = tagColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
                IconButton(onClick = onCopy) {
                    Icon(
                        Icons.Filled.ContentCopy,
                        contentDescription = "Copiar",
                        tint = TextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            Text(
                text = content,
                color = TextPrimary,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
        }
    }
}
