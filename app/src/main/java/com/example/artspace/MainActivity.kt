package com.example.artspace

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ArtSpaceScreen()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceScreen(){
    var scaffoldState = rememberScaffoldState(snackbarHostState = SnackbarHostState())
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
           ArtSpaceImages(scaffoldState = scaffoldState)
        }
    }
}

@Composable
fun ArtSpaceImages(
    scaffoldState: ScaffoldState
){
    var isPageTurned by remember { mutableStateOf(false)}
    var isPageTurnedPrev by remember { mutableStateOf(false)}
    var isPageTurnedNext by remember { mutableStateOf(false)}

    var scope = rememberCoroutineScope()
    var pageCount by remember { mutableStateOf(0) }
    var count by remember { mutableStateOf(0) }

    var artSpaceImages = remember {
        mutableStateListOf(
            R.drawable.africanart, R.drawable.medivallunch,
            R.drawable.naturepainting, R.drawable.stgeorge
        )
    }

    var artSpaceTitle = remember {
        mutableStateListOf(
            "Food Marketplace", "The Social Gathering",
            "Birds of Love", "St.George (Ethiopia)"
        )
    }

    var artSpaceNames = remember {
        mutableStateListOf(
            "Madingo Tombore", "William Spanks",
            "Roberto Celcius", "Tomas Demeke"
        )
    }

    Column(
        modifier = Modifier
            .padding(25.dp),
        verticalArrangement = Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column(
            modifier = Modifier
                .shadow(
                    2.dp,
                    RectangleShape,
                    true
                )
                .border(5.dp, Color.Gray)
                .padding(30.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = artSpaceImages[pageCount]),
                contentDescription = "Painting",
                contentScale = ContentScale.Crop,
            )
        }

        Column(
            modifier = Modifier
                .shadow(
                    2.dp,
                    RectangleShape,
                    true
                )
                .padding(20.dp)
        ) {
            Text(
                text = artSpaceTitle[pageCount],
                fontSize = 20.sp
            )
            Text(
                text = artSpaceNames[pageCount],
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                isPageTurned = true
                isPageTurnedPrev = true
            }) {
                Text(text = "Previous")
            }

            Button(onClick = {
                isPageTurned = true
                isPageTurnedNext = true
            }) {
                Text(text = "Next")
            }
        }
        if(isPageTurned){
            LaunchedEffect(key1 = isPageTurned == true) {
                Log.d("log","triggered")

                if (isPageTurnedNext && pageCount < 3) {
                    isPageTurnedNext = false
                    pageCount++
                } else if (isPageTurnedPrev && pageCount > 0) {
                    isPageTurnedPrev = false
                    pageCount--
                }
                isPageTurned = false
                Log.d("log","$pageCount")
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ArtSpaceTheme {
        ArtSpaceScreen()
    }
}