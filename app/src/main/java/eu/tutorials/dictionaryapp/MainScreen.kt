package eu.tutorials.dictionaryapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun MainScreen(){
    var word by remember { mutableStateOf("") }
    val mvvmInstance : MainViewModel = viewModel()
    val meaningState by mvvmInstance.meaningState
    val controller = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Dictionary.com", fontSize = 50.sp, color = Color(0xFFE8244A), fontFamily = FontFamily.Cursive, fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.height(18.dp))
        Row ( verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(10.dp)){
            OutlinedTextField(value = word,
                onValueChange = { word = it },
                label = { Text(text = "Enter word here")},
                textStyle = TextStyle( fontFamily = FontFamily.Serif),
                shape = RoundedCornerShape(30.dp)
            )
            IconButton(onClick = {
                mvvmInstance.fetchMeanings(word = word.toLowerCase())
                controller?.hide()
            },modifier = Modifier.fillMaxWidth()) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Button", modifier = Modifier.fillMaxSize())
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        when{
            meaningState.loading ->{
                Spacer(modifier = Modifier.height(18.dp))
                Text(text = "Enter a valid word \n or \nCheck Your Internet Connection", fontSize = 20.sp, fontFamily = FontFamily.Serif)
            }
            meaningState.error != null ->{
                Text(text = "Error Occurred")
            }
            else ->{
                Text(text = word, color = Color(0xFF6DE40A), fontSize = 32.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif)
                Spacer(modifier = Modifier.height(12.dp))
                if(meaningState.result.isNotEmpty()){
                    if (meaningState.result[0].phonetic?.isNotEmpty() == true) {
                        Text(
                            text = meaningState.result[0].phonetic.toString(),
                            color = Color.Gray,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.Serif
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    meaningState.result[0].meanings?.let { MeaningLazyColumn(it.toList()) }
                }
            }
        }
    }
}

@Composable
fun MeaningLazyColumn(meanings : List<Meaning>){
    LazyColumn(){
        items(meanings){
            eachMeaning ->
            MeaningEachItem(eachMeaning)
        }
    }
}

@Composable
fun MeaningEachItem(eachMeaning: Meaning) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .border(
                border = BorderStroke(3.dp, Color(0xFFE8244A)),
                shape = RoundedCornerShape(30.dp)
            )
            .padding(32.dp)
    ) {
        Text(
            text = "Part Of Speech :-  ${eachMeaning.partOfSpeech}",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.Serif
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (eachMeaning.definitions?.get(0)?.definition?.isNotEmpty() == true) {
            Text(
                text = "Definition :-  ${eachMeaning.definitions[0].definition}",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif
            )
        } else {
            Text(
                text = "Definition :-  NA",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        if (eachMeaning.definitions?.get(0)?.example?.isNotEmpty() == true) {
            Text(
                text = "Example :-  ${eachMeaning.definitions[0].example}",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif
            )
        } else {
            Text(
                text = "Example :-  NA",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SPreview(){
    MainScreen()
}