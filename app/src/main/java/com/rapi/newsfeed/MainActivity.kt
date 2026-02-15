package com.rapi.newsfeed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rapi.newsfeed.uii.NewsScreen
import com.rapi.newsfeed.uii.NewsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val vm: NewsViewModel = viewModel()
            NewsScreen(vm)
        }
    }
}
