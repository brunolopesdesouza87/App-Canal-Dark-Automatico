package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.MainViewModel
import com.example.ui.theme.*

@Composable
fun FlowDeliveryScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isUnlocked by viewModel.isUnlocked.collectAsState()
    val licenseInput by viewModel.licenseInput.collectAsState()
    val unlockError by viewModel.unlockError.collectAsState()
    val steps = viewModel.automationRepo.workflowSteps
    val rawJson = viewModel.automationRepo.readyWorkflowJson

    fun openBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    fun copyToClipboard(text: String, label: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "$label copiado!", Toast.LENGTH_SHORT).show()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .testTag("flow_delivery_screen_scroll"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "ENTREGA DO PRODUTO",
                    color = MetricGreen,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Fluxo Pronto para n8n & Make",
                    color = TextPrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "Importe a automação completa no seu n8n ou Make com todos os nós de IA e YouTube API já interligados.",
                    color = TextSecondary,
                    fontSize = 14.sp
                )
            }
        }

        // License / Unlock Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        if (isUnlocked) MetricGreen else CyberGold,
                        RoundedCornerShape(16.dp)
                    )
                    .testTag("license_unlock_card"),
                shape = RoundedCornerShape(16.dp),
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                if (isUnlocked) Icons.Filled.LockOpen else Icons.Filled.Lock,
                                contentDescription = null,
                                tint = if (isUnlocked) MetricGreen else CyberGold,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                if (isUnlocked) "Acesso VIP Desbloqueado" else "Área de Membros & Compradores",
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isUnlocked) MetricGreenDim else CyberGoldDim)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                if (isUnlocked) "ATIVO" else "BLOQUEADO",
                                color = if (isUnlocked) MetricGreen else CyberGold,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }
                    }

                    if (!isUnlocked) {
                        Text(
                            "Comprou na Cakto? Insira o código da sua compra (ou e-mail) para liberar o JSON completo e os webhooks privados.",
                            color = TextSecondary,
                            fontSize = 13.sp
                        )

                        OutlinedTextField(
                            value = licenseInput,
                            onValueChange = { viewModel.updateLicenseInput(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("license_input"),
                            label = { Text("Código de Ativação ou E-mail Cakto") },
                            placeholder = { Text("Ex: CAKTO-VIP-2025") },
                            singleLine = true,
                            isError = unlockError != null,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = CyberGold,
                                unfocusedBorderColor = BorderHighlight,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary,
                                focusedLabelColor = CyberGold,
                                unfocusedLabelColor = TextSecondary
                            )
                        )

                        unlockError?.let { err ->
                            Text(err, color = NeonRed, fontSize = 12.sp)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = { viewModel.attemptUnlock() },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .testTag("unlock_button"),
                                colors = ButtonDefaults.buttonColors(containerColor = CyberGold),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("VALIDAR ACESSO", color = DarkBackground, fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = { openBrowser(viewModel.automationRepo.checkoutUrl) },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .testTag("buy_now_button"),
                                colors = ButtonDefaults.buttonColors(containerColor = NeonRed),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("COMPRAR AGORA", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    } else {
                        Text(
                            "Parabéns! Sua licença vitalícia está verificada. Copie o JSON abaixo e importe diretamente no n8n ou Make.",
                            color = MetricGreen,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        // Blueprint Architecture Stages
        item {
            Text(
                text = "Etapas do Pipeline de Automação:",
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }

        items(steps) { step ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(TechCyan.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "${step.stepNumber}",
                                    color = TechCyan,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                step.title,
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                        Text(
                            step.tool,
                            color = CyberGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Text(
                        step.description,
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // JSON Blueprint Viewer (Unlocked or Blurred Preview)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BorderHighlight, RoundedCornerShape(16.dp))
                    .testTag("json_blueprint_card"),
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
                            "ARQUIVO JSON DO FLUXO (n8n / Make)",
                            color = TechCyan,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                        IconButton(onClick = {
                            if (isUnlocked) {
                                copyToClipboard(rawJson, "Código JSON do Fluxo")
                            } else {
                                Toast.makeText(context, "Desbloqueie com sua licença para copiar o JSON completo!", Toast.LENGTH_LONG).show()
                            }
                        }) {
                            Icon(
                                Icons.Filled.ContentCopy,
                                contentDescription = "Copiar JSON",
                                tint = if (isUnlocked) TechCyan else TextMuted
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(DarkBackground)
                            .padding(12.dp)
                    ) {
                        Text(
                            text = if (isUnlocked) {
                                rawJson
                            } else {
                                rawJson.take(380) + "\n\n/* ... CONTEÚDO RESTRITO PARA ASSINANTES ... */\n/* Digite seu código Cakto ou adquira sua licença para exibir todas as 500+ linhas */"
                            },
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            color = if (isUnlocked) TextPrimary else TextMuted,
                            lineHeight = 16.sp
                        )
                    }

                    Button(
                        onClick = {
                            if (isUnlocked) {
                                copyToClipboard(rawJson, "Blueprint JSON")
                            } else {
                                openBrowser(viewModel.automationRepo.checkoutUrl)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("action_copy_json_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isUnlocked) TechCyan else NeonRed
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            if (isUnlocked) Icons.Filled.FileCopy else Icons.Filled.ShoppingCart,
                            contentDescription = null,
                            tint = if (isUnlocked) DarkBackground else Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            if (isUnlocked) "COPIAR JSON COMPLETO DO FLUXO" else "DESBLOQUEAR FLUXO NA CAKTO",
                            color = if (isUnlocked) DarkBackground else Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        // How to Import Guide
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
                    Text(
                        "Como Importar no n8n em 3 Passos:",
                        color = CyberGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        "1. Abra seu painel do n8n (cloud ou auto-hospedado).\n2. Clique nos três pontinhos no topo direito -> 'Import from JSON'.\n3. Cole o código copiado e clique em Salvar. Pronto! Todos os nós estarão prontos para rodar.",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}
