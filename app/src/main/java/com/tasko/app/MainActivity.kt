package com.tasko.app
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.DisposableEffect
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.speech.RecognizerIntent
import android.content.Intent

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
                    "Assistant" -> AssistantVoiceScreen()
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
                "⏱️",
                "Timer",
                "Smart timing",
                Modifier.weight(1f),
                onTimer
            )
            FeatureCard(
                "📝",
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
@Composable
fun AssistantVoiceScreen() {
    val recognizedText = androidx.compose.runtime.remember {
        androidx.compose.runtime.mutableStateOf("")
    }
    val statusText = androidx.compose.runtime.remember {
        androidx.compose.runtime.mutableStateOf("Tap Speak and say something.")
    }

    androidx.compose.foundation.layout.Column(
        modifier = androidx.compose.ui.Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        androidx.compose.material3.Text(
            text = "AI ASSISTANT",
            color = Cyan,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        androidx.compose.foundation.layout.Spacer(
            modifier = androidx.compose.ui.Modifier.height(16.dp)
        )

        androidx.compose.material3.Text(
            text = statusText.value,
            color = Muted,
            fontSize = 14.sp
        )

        androidx.compose.foundation.layout.Spacer(
            modifier = androidx.compose.ui.Modifier.height(20.dp)
        )

        TaskoVoiceRecognition(
            onResult = { recognizedText.value = it },
            onStatus = { statusText.value = it }
        )

        androidx.compose.foundation.layout.Spacer(
            modifier = androidx.compose.ui.Modifier.height(20.dp)
        )

        androidx.compose.material3.Text(
            text = "YOU SAID",
            color = Cyan,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )

        androidx.compose.material3.Text(
            text = recognizedText.value.ifBlank { "Your recognized speech will appear here." },
            color = White,
            fontSize = 16.sp
        )
    }
}

@Composable
fun TaskoVoiceRecognition(
    onResult: (String) -> Unit,
    onStatus: (String) -> Unit
) {
    val context = LocalContext.current
    var listening by remember { mutableStateOf(false) }
    var permissionGranted by remember {
        mutableStateOf(
            androidx.core.content.ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.RECORD_AUDIO
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        )
    }

    val recognizer = remember(context) {
        if (SpeechRecognizer.isRecognitionAvailable(context)) {
            SpeechRecognizer.createSpeechRecognizer(context)
        } else null
    }

    DisposableEffect(recognizer) {
        if (recognizer != null) {
            recognizer.setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {
                    onStatus("Listening... Speak now.")
                }

                override fun onBeginningOfSpeech() {
                    onStatus("Hearing your voice...")
                }

                override fun onRmsChanged(rmsdB: Float) {}
                override fun onBufferReceived(buffer: ByteArray?) {}
                override fun onEndOfSpeech() {
                    listening = false
                    onStatus("Processing speech...")
                }

                override fun onError(error: Int) {
                    listening = false
                    onStatus(
                        when (error) {
                            SpeechRecognizer.ERROR_NO_MATCH -> "No speech recognized. Try again."
                            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech detected. Try again."
                            SpeechRecognizer.ERROR_AUDIO -> "Microphone audio error."
                            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Microphone permission required."
                            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "Recognizer busy. Try again shortly."
                            else -> "Speech recognition failed (error $error)."
                        }
                    )
                }

                override fun onResults(results: Bundle?) {
                    listening = false
                    val text = results
                        ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                        ?.firstOrNull()
                        .orEmpty()

                    if (text.isNotBlank()) {
                        onResult(text)
                        onStatus("Speech recognized.")
                    } else {
                        onStatus("No speech recognized. Try again.")
                    }
                }

                override fun onPartialResults(partialResults: Bundle?) {}
                override fun onEvent(eventType: Int, params: Bundle?) {}
            })
        }

        onDispose {
            recognizer?.destroy()
        }
    }

    val permissionLauncher =
        androidx.activity.compose.rememberLauncherForActivityResult(
            contract = androidx.activity.result.contract.ActivityResultContracts.RequestPermission()
        ) { granted ->
            permissionGranted = granted
            onStatus(
                if (granted) "Microphone permission granted. Tap Speak to start."
                else "Microphone permission denied."
            )
        }

    androidx.compose.material3.Button(
        onClick = {
            if (!permissionGranted) {
                permissionLauncher.launch(android.Manifest.permission.RECORD_AUDIO)
            } else if (recognizer == null) {
                onStatus("Speech recognition is unavailable on this device.")
            } else if (!listening) {
                try {
                    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                        putExtra(
                            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                        )
                        putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-IN")
                        putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false)
                    }
                    listening = true
                    onStatus("Starting microphone...")
                    recognizer.startListening(intent)
                } catch (e: Exception) {
                    listening = false
                    onStatus("Could not start speech recognition.")
                }
            } else {
                recognizer?.cancel()
                listening = false
                onStatus("Listening stopped.")
            }
        }
    ) {
        androidx.compose.material3.Text(
            if (listening) "Stop Listening" else "Speak"
        )
    }
}

