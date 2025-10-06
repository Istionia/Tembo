package com.tembo.app

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tembo.app.data.api.MastodonApi
import com.tembo.app.data.models.Status
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        TimelineScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimelineScreen() {
    var statuses by remember { mutableStateOf<List<Status>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    // TODO: Replace with your instance and token
    val api = remember {
        MastodonApi(
            instanceUrl = "https://mastodon.social",
            accessToken = "Tfi5Yr1G1t-B5lplA5uuoczhS3FAv-A6E29GxkUm50o"
        )
    }

    LaunchedEffect(Unit) {
        isLoading = true
        try {
            statuses = api.getHomeTimeline()
            error = null
        } catch (e: Exception) {
            error = e.message
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tembo 🐘") }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.fillMaxSize().wrapContentSize()
                    )
                }
                error != null -> {
                    Text(
                        text = "Error: $error",
                        modifier = Modifier.fillMaxSize().wrapContentSize().padding(16.dp)
                    )
                }
                else -> {
                    LazyColumn {
                        items(statuses) { status ->
                            StatusCard(status)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusCard(status: Status) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = status.account.displayName,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "@${status.account.acct}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = status.content.replace(Regex("<[^>]*>"), ""),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text("💬 ${status.repliesCount}", modifier = Modifier.padding(end = 16.dp))
                Text("🔁 ${status.reblogsCount}", modifier = Modifier.padding(end = 16.dp))
                Text("⭐ ${status.favouritesCount}")
            }
        }
    }
}