package com.idpwf.medicationtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.idpwf.medicationtracker.logic.MedicationTrackerViewModel
import com.idpwf.medicationtracker.ui.theme.MedicationTrackerTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        val viewModel: MedicationTrackerViewModel by viewModels()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) { }
        }

        setContent {
            MedicationTrackerTheme {
                Scaffold { innerPadding ->
                    val paddedModifier = Modifier
                        .captionBarPadding()
                        .statusBarsPadding()
                        .systemBarsPadding()
                        .navigationBarsPadding()
                        .padding(innerPadding)

                    Column(paddedModifier) {
                        MedicationTrackerTopBar(Modifier)

                        MedicationTrackerMedsList(
                            viewModel.takenToday().map { TakenMed(it.key, it.value) },
                            Modifier
                        )
                    }
                    FloatingActionButton({
                        println("Clicked the floating button")
                    }, Modifier) {
                        Icons.Rounded.Add
                    }
                }
            }
        }
    }
}

@Composable
fun MedicationTrackerTopBar(modifier: Modifier) {
    Column(Modifier) {
        AppHeaderRow(Modifier)
    }
}

@Composable
fun TakenMedTodayCounter(modifier: Modifier, count: Int) {
    Text(
        text = count.toString(),
        modifier = modifier,
        fontSize = 30.sp,
        textAlign = TextAlign.Center
    )
}

@Composable
fun TakenMedRow(modifier: Modifier, medicationName: String, takenToday: Int) {
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        var takenToday by rememberSaveable { mutableIntStateOf(takenToday) }

        Button(
            onClick = {
                takenToday++
                println("clicked on $medicationName ; new value of counter is $takenToday")
            },
            modifier = Modifier.weight(4f)
        ) {
            Text(text = medicationName)
        }

        TakenMedTodayCounter(
            modifier = Modifier.weight(1f),
            count = takenToday
        )

        Button(
            { },
            modifier = Modifier.weight(1f)
        ) {
            Text("❌")
        }
    }
}

@Composable
fun AppHeaderRow(modifier: Modifier) {
    Row {
        Spacer(Modifier.weight(1f))
        Text(text = "Medication Tracker", textAlign = TextAlign.Center)
        Spacer(Modifier.weight(1f))
    }
}

@Composable
fun TakenMedLabelRow(modifier: Modifier) {
    Row(modifier.fillMaxWidth()) {
        Text(
            text = "Medication Name and Dose",
            modifier = Modifier.weight(4f),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Today",
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Delete",
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun MedicationTrackerMedsList(meds: List<TakenMed>, modifier: Modifier) {
    Column(Modifier) {
        TakenMedLabelRow(Modifier)
        meds.forEach { takenMed ->
            TakenMedRow(Modifier, medicationName = takenMed.name, takenMed.amount)
        }
    }
}


//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    MedicationTrackerTheme {
//        MedicationTrackerTheme {
//            Scaffold(modifier = Modifier.padding(top = 32.dp)) { innerPadding ->
//                MedicationTrackerTopBar(Modifier.padding(innerPadding))
//            }
//        }
//    }
//}

class TakenMed(val name: String, val amount: Int)
