package com.example.a207385_wangling_cikguizwan_project2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.a207385_wangling_cikguizwan_project2.ui.theme.A207385_Wangling_CikguIzwan_Project2Theme
import java.util.Locale
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            A207385_Wangling_CikguIzwan_Project2Theme {
                val navController = rememberNavController()
                val context = androidx.compose.ui.platform.LocalContext.current

                val database = GroceryDatabase.getDatabase(context)
                val firestoreDb = FirebaseFirestore.getInstance()
                val repository = GroceryRepository(database.groceryDao(), firestoreDb)

                val viewModel: GroceryViewModel = viewModel(
                    factory = GroceryViewModelFactory(repository)
                )

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val allNavRoutes = listOf("home", "form", "summary", "scanner", "community", "detail", "calc")

                Scaffold(
                    bottomBar = {
                        if (currentRoute in allNavRoutes) {
                            Column {
                                Surface(
                                    color = Color(0xFFF9FBE7),
                                    tonalElevation = 2.dp
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(48.dp),
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        SecondaryNavItem(
                                            icon = {
                                                Icon(
                                                    Icons.Default.Info,
                                                    contentDescription = "Detail",
                                                    modifier = Modifier.size(18.dp)
                                                )
                                            },
                                            label = "Item Detail",
                                            selected = currentRoute == "detail",
                                            onClick = {
                                                if (viewModel.selectedItem != null) {
                                                    navController.navigate("detail") {
                                                        popUpTo("home")
                                                        launchSingleTop = true
                                                    }
                                                }
                                            },
                                            enabled = viewModel.selectedItem != null,
                                            modifier = Modifier.weight(1f)
                                        )

                                        Box(
                                            modifier = Modifier
                                                .width(1.dp)
                                                .height(24.dp)
                                                .background(Color(0xFFDCE775))
                                        )

                                        SecondaryNavItem(
                                            icon = {
                                                Icon(
                                                    Icons.Default.Star,
                                                    contentDescription = "Calc",
                                                    modifier = Modifier.size(18.dp)
                                                )
                                            },
                                            label = "CO₂ Result",
                                            selected = currentRoute == "calc",
                                            onClick = {
                                                navController.navigate("calc") {
                                                    popUpTo("home")
                                                    launchSingleTop = true
                                                }
                                            },
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                }

                                NavigationBar(
                                    containerColor = Color.White,
                                    tonalElevation = 8.dp
                                ) {
                                    NavigationBarItem(
                                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                                        label = { Text("Home") },
                                        selected = currentRoute == "home",
                                        onClick = {
                                            navController.navigate("home") {
                                                popUpTo("home") { inclusive = true }
                                                launchSingleTop = true
                                            }
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = Color(0xFF2E7D32),
                                            indicatorColor = Color(0xFFC8E6C9)
                                        )
                                    )
                                    NavigationBarItem(
                                        icon = { Icon(Icons.Default.Add, contentDescription = "Add") },
                                        label = { Text("Catalog") },
                                        selected = currentRoute == "form",
                                        onClick = {
                                            navController.navigate("form") {
                                                popUpTo("home")
                                                launchSingleTop = true
                                            }
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = Color(0xFF2E7D32),
                                            indicatorColor = Color(0xFFC8E6C9)
                                        )
                                    )
                                    NavigationBarItem(
                                        icon = { Icon(Icons.Default.Search, contentDescription = "Scan") },
                                        label = { Text("Scan") },
                                        selected = currentRoute == "scanner",
                                        onClick = {
                                            navController.navigate("scanner") {
                                                popUpTo("home")
                                                launchSingleTop = true
                                            }
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = Color(0xFF2E7D32),
                                            indicatorColor = Color(0xFFC8E6C9)
                                        )
                                    )
                                    NavigationBarItem(
                                        icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "List") },
                                        label = { Text("My Fridge") },
                                        selected = currentRoute == "summary",
                                        onClick = {
                                            navController.navigate("summary") {
                                                popUpTo("home")
                                                launchSingleTop = true
                                            }
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = Color(0xFF2E7D32),
                                            indicatorColor = Color(0xFFC8E6C9)
                                        )
                                    )
                                    NavigationBarItem(
                                        icon = { Icon(Icons.Default.Share, contentDescription = "Community") },
                                        label = { Text("Donate") },
                                        selected = currentRoute == "community",
                                        onClick = {
                                            navController.navigate("community") {
                                                popUpTo("home")
                                                launchSingleTop = true
                                            }
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = Color(0xFF2E7D32),
                                            indicatorColor = Color(0xFFC8E6C9)
                                        )
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") { HomeScreen(navController) }
                        composable("form") {
                            FormScreen(viewModel) {
                                viewModel.selectItem(it)
                                navController.navigate("detail")
                            }
                        }
                        composable("detail") {
                            DetailScreen(viewModel) {
                                viewModel.selectedItem?.let { viewModel.addIngredient(it) }
                                navController.navigate("summary")
                            }
                        }
                        composable("summary") {
                            SummaryScreen(viewModel, onCalc = { navController.navigate("calc") })
                        }
                        composable("calc") {
                            CalculationScreen(viewModel) {
                                viewModel.resetProjectData()
                                navController.navigate("home") {
                                    popUpTo("home") { inclusive = true }
                                }
                            }
                        }
                        composable("scanner") { ScannerScreen(navController, viewModel) }
                        composable("community") { CommunityScreen(viewModel) }
                    }
                }
            }
        }
    }
}

@Composable
fun SecondaryNavItem(
    icon: @Composable () -> Unit,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val contentColor = when {
        selected -> Color(0xFF2E7D32)
        !enabled -> Color(0xFFBDBDBD)
        else -> Color(0xFF757575)
    }
    Surface(
        onClick = onClick,
        enabled = enabled,
        color = if (selected) Color(0xFFE8F5E9) else Color.Transparent,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CompositionLocalProvider(LocalContentColor provides contentColor) {
                icon()
            }
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                color = contentColor
            )
            if (!enabled) {
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "(select item first)", fontSize = 10.sp, color = Color(0xFFBDBDBD))
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Eco Fridge Tracker",
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF1B5E20),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "SDG 12: Responsible Consumption",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF4CAF50),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(40.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9)),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "🌍", fontSize = 56.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Food waste is a major global issue that directly increases our carbon footprint.",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 24.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Track your fridge items and calculate the CO₂ you save by consuming responsibly.",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF757575),
                    lineHeight = 20.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
        Button(
            onClick = {
                navController.navigate("form") {
                    popUpTo("home")
                    launchSingleTop = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF6C00)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Start Tracking Now", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun FormScreen(viewModel: GroceryViewModel, onClick: (GroceryItem) -> Unit) {
    val groupedItems = viewModel.allIngredients.groupBy { it.category }
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        groupedItems.forEach { (category, items) ->
            item {
                Text(
                    text = category,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            items(items) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClick(item) }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(item.imageRes),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(item.name, fontWeight = FontWeight.Bold)
                        Text(item.category, fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Composable
fun DetailScreen(viewModel: GroceryViewModel, onAdd: () -> Unit) {
    val item = viewModel.selectedItem
    if (item == null) {
        Text("No item selected", color = Color.Red)
        return
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painterResource(item.imageRes), null, modifier = Modifier.size(200.dp))
        Spacer(Modifier.height(20.dp))
        Text(item.name, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("Carbon Footprint Saved: ${item.impactValue} kg CO₂", fontSize = 16.sp, color = Color.Gray)
        Spacer(Modifier.height(30.dp))
        Button(
            onClick = onAdd,
            modifier = Modifier.size(width = 160.dp, height = 50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
        ) {
            Text("Add to Fridge", fontSize = 18.sp)
        }
    }
}

@Composable
fun SummaryScreen(viewModel: GroceryViewModel, onCalc: () -> Unit) {
    val summaryItems = viewModel.summaryItems.collectAsState().value

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("My Fridge 🥦", fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))

        if (summaryItems.isEmpty()) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text(
                    text = "Your fridge is empty! 🌬️\nLet's add some food to start saving the planet.",
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(summaryItems) { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(item.imageRes),
                            contentDescription = null,
                            modifier = Modifier.size(50.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.name, fontWeight = FontWeight.Bold)
                            Text("${item.impactValue} kg CO₂", fontSize = 12.sp, color = Color.Gray)
                        }
                        IconButton(onClick = { viewModel.removeIngredient(item) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(10.dp))
        Button(
            onClick = onCalc,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            enabled = summaryItems.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF6C00))
        ) {
            Text("Calculate CO₂ Saved", fontSize = 18.sp)
        }
    }
}

@Composable
fun CalculationScreen(viewModel: GroceryViewModel, onFinish: () -> Unit) {
    val summaryItems = viewModel.summaryItems.collectAsState().value
    val totalImpact = summaryItems.sumOf { it.impactValue }
    val count = summaryItems.size

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Your Impact", fontSize = 40.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
        Spacer(Modifier.height(24.dp))
        Text("Items Saved from Waste: $count", fontSize = 20.sp)

        Text(
            "CO₂ Reduction: ${String.format(Locale.US, "%.2f", totalImpact)} kg",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(24.dp))
        Text("Reduce waste • Protect environment 🌍", color = Color.Gray, fontSize = 18.sp)
        Spacer(Modifier.height(40.dp))
        Button(
            onClick = onFinish,
            modifier = Modifier.size(width = 160.dp, height = 50.dp)
        ) {
            Text("Finish")
        }
    }
}

@Composable
fun CommunityScreen(
    viewModel: GroceryViewModel
) {
    val summaryItems = viewModel.summaryItems.collectAsState().value
    val communityItems = viewModel.communityItems.collectAsState().value
    val uploadStatus = viewModel.uploadStatus.collectAsState().value
    val context = androidx.compose.ui.platform.LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.startListeningCommunity()
    }

    LaunchedEffect(uploadStatus) {
        uploadStatus?.let {
            android.widget.Toast.makeText(context, it, android.widget.Toast.LENGTH_SHORT).show()
            viewModel.clearUploadStatus()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = "🤝 Community Food Bank",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFEF6C00)
        )
        Text(
            text = "Share surplus food to reduce waste",
            fontSize = 13.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Button(
            onClick = {
                if (summaryItems.isNotEmpty()) {
                    android.widget.Toast.makeText(
                        context,
                        "Uploading ${summaryItems.size} items to Cloud! 🚀",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                    summaryItems.forEach { item -> viewModel.donateToCloud(item) }
                } else {
                    android.widget.Toast.makeText(
                        context,
                        "Your fridge is empty! Add items first. 🥦",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF6C00)),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Donate My Fridge Items", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Community Donations",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            if (communityItems.isNotEmpty()) {
                Surface(
                    color = Color(0xFF2E7D32),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "${communityItems.size} items",
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (communityItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No donations yet.\nBe the first to donate! 🌱",
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    fontSize = 15.sp
                )
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(communityItems, key = { it.id }) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9)),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(viewModel.getImageResForCategory(item.category, item.name)),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = item.category,
                                    fontSize = 12.sp,
                                    color = Color(0xFF4CAF50)
                                )
                                Text(
                                    text = "Saves ${item.impactValue} kg CO₂",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                            Text(
                                text = SimpleDateFormat("HH:mm", Locale.getDefault())
                                    .format(Date(item.timestamp)),
                                fontSize = 11.sp,
                                color = Color.LightGray
                            )
                        }
                    }
                }
            }
        }
    }
}