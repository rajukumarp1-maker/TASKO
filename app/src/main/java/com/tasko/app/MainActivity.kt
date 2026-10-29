package com.tasko.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg = Color(0xFF090B10)
private val CardBg = Color(0xFF151923)
private val Cyan = Color(0xFF42D9E8)
private val White = Color(0xFFF5F7FA)
private val Muted = Color(0xFF929BAB)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskoApp()
        }
    }
}

@Composable
fun TaskoApp() {
    var page by remember { mutableStateOf("Home") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Bg
    ) {
        Column(
                modifier = Modifier
                        .fillMaxSize()
                                .statusBarsPadding()
        ) {
            Box(modifier = Modifier.weight(1f)) {
                when (page) {
                    "Home" -> HomeScreen(
                        onVoice = { page = "Assistant" },
                        onTasks = { page = "Tasks" },
                        onTimer = { page = "Timer" }
                    )
                    "Assistant" -> SimpleScreen(
                        "AI ASSISTANT",
                        "Voice assistant setup comes next.",
                        "Home",
                        { page = "Home" }
                    )
                    "Tasks" -> SimpleScreen(
                        "MY TASKS",
                        "Your task list will be built here.",
                        "Home",
                        { page = "Home" }
                    )
                    "Timer" -> SimpleScreen(
                        "SMART TIMER",
                        "Timer controls will be built here.",
                        "Home",
                        { page = "Home" }
                    )
                    "Settings" -> SimpleScreen(
                        "SETTINGS",
                        "Voice, AI and app settings will be built here.",
                        "Home",
                        { page = "Home" }
                    )
                }
            }

            NavigationBar(
                containerColor = Color(0xFF10131A),
                contentColor = White
            ) {
                listOf("Home", "Tasks", "Timer", "Settings").forEach { item ->
                    NavigationBarItem(
                        selected = page == item,
                        onClick = { page = item },
                        icon = {
                            Text(
                                when (item) {
                                    "Home" -> "🏠"
                                    "Tasks" -> "📝"
                                    "Timer" -> "⏱️"
                                    else -> "⚙️"
                                },
                                fontSize = 21.sp
                            )
                        },
                        label = { Text(item, fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Cyan,
                            selectedTextColor = Cyan,
                            indicatorColor = Color(0xFF20343C),
                            unselectedIconColor = Muted,
                            unselectedTextColor = Muted
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    onVoice: () -> Unit,
    onTasks: () -> Unit,
    onTimer: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 22.dp, vertical = 20.dp)
    ) {
        Text(
            "TASKO",
            color = White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Your Tasks, Automated",
            color = Muted,
            fontSize = 14.sp
        )

        Spacer(Modifier.height(34.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                onClick = onVoice,
                modifier = Modifier.size(174.dp),
                shape = CircleShape,
                color = Color(0xFF122A34),
                border = androidx.compose.foundation.BorderStroke(
                    2.dp, Cyan
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("🎙", fontSize = 43.sp)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Tap to Start",
                        color = White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                    Text(
                        "Hey ZENY",
                        color = Cyan,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(Modifier.height(34.dp))

        Text(
            "QUICK ACCESS",
            color = Muted,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Spacer(Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FeatureCard(
                "◷",
                "Timer",
                "Smart timing",
                Modifier.weight(1f),
                onTimer
            )
            FeatureCard(
                "☷",
                "Tasks",
                "Stay organized",
                Modifier.weight(1f),
                onTasks
            )
        }

        Spacer(Modifier.height(14.dp))

        FeatureCard(
            "✦",
            "Custom Command",
            "Your commands and shortcuts",
            Modifier.fillMaxWidth(),
            onVoice
        )

        Spacer(Modifier.height(22.dp))
    }
}

@Composable
fun FeatureCard(
    symbol: String,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        color = CardBg,
        border = androidx.compose.foundation.BorderStroke(
            1.dp, Color(0xFF292F3B)
        )
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(symbol, color = Cyan, fontSize = 27.sp)
            Spacer(Modifier.height(12.dp))
            Text(
                title,
                color = White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(4.dp))
            Text(subtitle, color = Muted, fontSize = 12.sp)
        }
    }
}

@Composable
fun SimpleScreen(
    title: String,
    message: String,
    buttonText: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            title,
            color = White,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(12.dp))
        Text(
            message,
            color = Muted,
            fontSize = 14.sp
        )
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(
                containerColor = Cyan,
                contentColor = Bg
            )
        ) {
            Text(buttonText)
        }
    }
}
