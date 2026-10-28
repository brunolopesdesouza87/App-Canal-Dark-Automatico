package com.example

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.ui.ScreenTab
import com.example.ui.screens.*
import com.example.ui.theme.*

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CanalDarkTheme {
                MainAppScaffold(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScaffold(viewModel: MainViewModel) {
    val context = LocalContext.current
    val currentTab by viewModel.currentTab.collectAsState()

    // Back button handling: return to HOME if on another tab
    BackHandler(enabled = currentTab != ScreenTab.HOME) {
        viewModel.setTab(ScreenTab.HOME)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = DarkBackground,
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(NeonRed),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Filled.PlayArrow,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "CANAL DARK",
                                color = TextPrimary,
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp,
                                letterSpacing = 0.5.sp
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(MetricGreen)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "YouTube API v3 Ready",
                                    color = MetricGreen,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                },
                actions = {
                    // Quick Checkout Top CTA
                    Box(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(NeonRed.copy(alpha = 0.2f))
                            .border(1.dp, NeonRed, RoundedCornerShape(20.dp))
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.automationRepo.checkoutUrl))
                                context.startActivity(intent)
                            }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                            .testTag("top_app_bar_buy_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Filled.ShoppingCart,
                                contentDescription = "Comprar Fluxo",
                                tint = NeonRed,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                "Comprar",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkBackground,
                    titleContentColor = TextPrimary
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = DarkSurface,
                contentColor = TextSecondary,
                windowInsets = WindowInsets.navigationBars,
                modifier = Modifier.testTag("main_bottom_nav")
            ) {
                NavigationBarItem(
                    selected = currentTab == ScreenTab.HOME,
                    onClick = { viewModel.setTab(ScreenTab.HOME) },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Início") },
                    label = { Text("Início", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = NeonRed,
                        selectedTextColor = NeonRed,
                        indicatorColor = NeonRedDim,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    ),
                    modifier = Modifier.testTag("nav_item_home")
                )
                NavigationBarItem(
                    selected = currentTab == ScreenTab.TUTORIAL,
                    onClick = { viewModel.setTab(ScreenTab.TUTORIAL) },
                    icon = { Icon(Icons.Filled.PlayCircle, contentDescription = "Aula") },
                    label = { Text("Aula", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = TechCyan,
                        selectedTextColor = TechCyan,
                        indicatorColor = TechCyanDim,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    ),
                    modifier = Modifier.testTag("nav_item_tutorial")
                )
                NavigationBarItem(
                    selected = currentTab == ScreenTab.YOUTUBE_API,
                    onClick = { viewModel.setTab(ScreenTab.YOUTUBE_API) },
                    icon = { Icon(Icons.Filled.Api, contentDescription = "API") },
                    label = { Text("API YT", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = TechCyan,
                        selectedTextColor = TechCyan,
                        indicatorColor = TechCyanDim,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    ),
                    modifier = Modifier.testTag("nav_item_api")
                )
                NavigationBarItem(
                    selected = currentTab == ScreenTab.GENERATOR,
                    onClick = { viewModel.setTab(ScreenTab.GENERATOR) },
                    icon = { Icon(Icons.Filled.AutoAwesome, contentDescription = "Roteiros") },
                    label = { Text("Roteiros", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CyberGold,
                        selectedTextColor = CyberGold,
                        indicatorColor = CyberGoldDim,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    ),
                    modifier = Modifier.testTag("nav_item_generator")
                )
                NavigationBarItem(
                    selected = currentTab == ScreenTab.FLOW_DELIVERY,
                    onClick = { viewModel.setTab(ScreenTab.FLOW_DELIVERY) },
                    icon = { Icon(Icons.Filled.FileDownload, contentDescription = "Fluxo") },
                    label = { Text("Fluxo", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MetricGreen,
                        selectedTextColor = MetricGreen,
                        indicatorColor = MetricGreenDim,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    ),
                    modifier = Modifier.testTag("nav_item_flow")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                ScreenTab.HOME -> HomeScreen(
                    viewModel = viewModel,
                    onNavigateTab = { viewModel.setTab(it) }
                )
                ScreenTab.TUTORIAL -> TutorialScreen(
                    viewModel = viewModel,
                    onNavigateTab = { viewModel.setTab(it) }
                )
                ScreenTab.YOUTUBE_API -> YouTubeApiScreen(
                    viewModel = viewModel
                )
                ScreenTab.GENERATOR -> GeneratorScreen(
                    viewModel = viewModel
                )
                ScreenTab.FLOW_DELIVERY -> FlowDeliveryScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}
