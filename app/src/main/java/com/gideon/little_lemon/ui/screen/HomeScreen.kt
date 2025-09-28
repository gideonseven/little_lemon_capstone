package com.gideon.little_lemon.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.gideon.little_lemon.AppDatabase
import com.gideon.little_lemon.MenuItemRoom
import com.gideon.little_lemon.Profile
import com.gideon.little_lemon.R
import com.gideon.little_lemon.ui.karlaFamily
import com.gideon.little_lemon.ui.markaziFamily
import com.gideon.little_lemon.ui.theme.brandChipColors
import com.gideon.little_lemon.ui.theme.brandGreen
import com.gideon.little_lemon.ui.theme.brandYellow

@Composable
fun HomeScreen(
    navController: NavController,
    database: AppDatabase
) {
    val databaseMenuItems by database.menuItemDao().getAll().observeAsState(emptyList())
    var orderMenuItems by remember {
        mutableStateOf(false)
    }

    val menuItems = if (orderMenuItems) {
        databaseMenuItems.sortedBy { it.title }
    } else {
        databaseMenuItems
    }

    Scaffold(
        topBar = {
            HomepageTopBar(navController)
        },
        bottomBar = {}
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.base_green))
                    .padding(16.dp)
            ) {
                // Title on top, full width
                Text(
                    text = stringResource(id = R.string.title),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = markaziFamily,
                    color = colorResource(id = R.color.base_yellow),
                )

                // Row: Left (Chicago + description), Right (Image)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f) // take remaining space
                            .padding(end = 12.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.location),
                            fontWeight = FontWeight.Normal,
                            fontFamily = markaziFamily,
                            fontSize = 36.sp,
                            color = colorResource(id = R.color.base_white),
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = stringResource(id = R.string.description),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = karlaFamily,
                            color = colorResource(id = R.color.base_white)
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.upperpanelimage),
                        contentDescription = "Upper Panel Image",
                        modifier = Modifier
                            .width(120.dp)   // adjust width
                            .height(IntrinsicSize.Min) // matches the height of the text column
                            .clip(RoundedCornerShape(20.dp))
                    )
                }
            }
            MenuListScreen(menuItems)
        }
    }


}

@Composable
fun HomepageTopBar(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .align(Alignment.CenterStart)
                .alpha(0f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_hamburger_menu),
                contentDescription = "Menu Icon",
            )
        }

        Image(
            painter = painterResource(id = R.drawable.img_logo),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .fillMaxWidth(.32f)
                .align(Alignment.Center)
        )

        IconButton(
            onClick = {
                navController.navigate(Profile.route)
            },
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .align(Alignment.CenterEnd)
        ) {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "Profile"
                )
            }
        }
    }
}

@Composable
fun MenuListScreen(items: List<MenuItemRoom>) {
    UpperPanel(items)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UpperPanel(items: List<MenuItemRoom>) {

    var searchPhrase by remember {
        mutableStateOf("")
    }

    var menuItems = items

    Column(
        modifier = Modifier
            .background(Color(0xFF495E57))
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp)
    ) {
//        OutlinedTextField(
//            value = searchPhrase,
//            onValueChange = {
//                searchPhrase = it
//            },
//            label = { Text("Search") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 50.dp, end = 50.dp)
//        )
//        if (searchPhrase.isNotEmpty()) {
//            menuItems = menuItems.filter { it.title.contains(searchPhrase, ignoreCase = true) }
//        }


        SearchBar(
            searchText = searchPhrase,
            onSearchTextChange = { searchPhrase = it },
            placeholder = "Enter search phrase"
        )
        if (searchPhrase.isNotEmpty()) {
            menuItems = menuItems.filter { it.title.contains(searchPhrase, ignoreCase = true) }
        }
    }
    LowerPanel(menuItems)
}

@Composable
private fun LowerPanel(items: List<MenuItemRoom>) {

    Column(modifier = Modifier.background(Color.White)) {
        Spacer(Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.order_take_away).uppercase(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            fontFamily = karlaFamily,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        LazyRow(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            items(items.distinctBy { it.category }) { item ->
                MenuCategory(item.category.replaceFirstChar { it.titlecase() })
            }
        }
        HorizontalDivider(
            modifier = Modifier.padding(8.dp),
            color = Color.Gray,
            thickness = 1.dp
        )
        LazyColumn {
            items(items) { dish ->
                MenuDish(dish)
            }
        }
    }
}

@Composable
fun MenuCategory(category: String) {
    Button(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(Color.LightGray),
        shape = RoundedCornerShape(40),
        modifier = Modifier
            .padding(5.dp)
    ) {
        Text(
            text = category,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            fontFamily = karlaFamily,
            color = brandGreen
        )
    }
}

@Composable
fun MenuDish(dish: MenuItemRoom) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { /*TODO*/ }
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column {
                Text(
                    text = dish.title, fontSize = 18.sp, fontWeight = FontWeight.Bold
                )
                Text(
                    text = dish.description,
                    maxLines = 2,
                    color = brandGreen,
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 5.dp)
                        .fillMaxWidth(.75f),
                    fontFamily = karlaFamily,
                    fontWeight = FontWeight.Normal
                )
                val price = "$${dish.price}"
                Text(
                    text = price,
                    color = brandGreen,
                    fontWeight = FontWeight.Bold
                )
            }
            AsyncImage(
                model = dish.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = rememberVectorPainter(Icons.Default.Refresh),
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(4.dp)
                    .fillMaxWidth()
            )
        }
    }
    HorizontalDivider(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        color = Color.LightGray,
        thickness = 1.dp
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExampleSearchBar() {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    SearchBar(
        query = query,
        onQueryChange = { query = it },
        onSearch = { /* handle search */ },
        active = active,
        onActiveChange = { active = it },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Search") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        }
    ) {
        // Content shown when active (search suggestions, history, etc.)
        Text("Suggestions go here")
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchText: String = "",
    onSearchTextChange: (String) -> Unit = {},
    placeholder: String = "Enter search phrase"
) {
    var text by remember { mutableStateOf(searchText) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText
                onSearchTextChange(newText)
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            singleLine = true,
            shape = RoundedCornerShape(8.dp)
        )
    }
}