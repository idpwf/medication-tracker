package com.idpwf.medicationtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.idpwf.medicationtracker.ui.theme.MedicationTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedicationTrackerTheme {
                Scaffold { innerPadding ->
                    //header row
                    //  'medication tracker'
                    //  menu button
//                    val borderGrid = Modifier.border(
//                        width = 4.dp,
//                        brush = Brush.linearGradient(listOf(Color.Red)),
//                        shape = RoundedCornerShape(50)
//                        )

                    val borderStroke = BorderStroke(1.dp, Color.Red)

                    MedicationTrackerTopBar(Modifier
                        .padding(innerPadding)
                        .border(borderStroke))


                    val defaultMeds = listOf(TakenMed("Ibuprofen", "200 mg"))
                    // meds list
                    //  header row
                    //    name
                    //    taken today
                    //    delete
                    //  meds row
                    //    swipe bar
                    //      name
                    //    today counter
                    //    delete button
                    MedicationTrackerMedsList(
                        defaultMeds,
                        modifier = Modifier
                            .padding(innerPadding)
//                            .border(borderStroke)
                    )

                    //floating `add` button
                }
            }
        }
    }
}

@Composable
fun MedicationTrackerTopBar(modifier: Modifier = Modifier) {
    println("Composing MedicationTrackerTopBar")
    Row (modifier = modifier) {
        Text(
            text = "Medication Tracker",
            modifier
                .align(Alignment.CenterVertically)
                .padding(start = 8.dp)
        )
        Spacer(modifier.weight(1f))
        Button({ }, modifier = modifier.padding(end = 8.dp)) { }
    }
}

/**
 * @param amount is `String` because I am not an SME and don't know what units need to be used or if this even can be
 *                  generalized well (volume, mass, duration, etc)
 */
class TakenMed(val name: String, val amount: String) {
    override fun toString(): String {
        return "$name $amount"
    }
}


@Composable
fun TakenMedRow(modifier: Modifier, label: String) {
    Row {
        Text(text = label, modifier = modifier.padding(all = 8.dp))
        Text(text = "1", modifier, style = TextStyle(fontSize = TextUnit(32f, type = TextUnitType.Sp)))
        Button ({ } , modifier) { }
    }
}


@Composable
fun TakenMedLabelRow(modifier: Modifier) {
    Row {
        Text(text = "Medication name", modifier = modifier)
        Text(text = "Today", modifier = modifier)
        Text(text = "❌", modifier = modifier)
    }
}


@Composable
fun MedicationTrackerMedsList(meds: List<TakenMed>, modifier: Modifier = Modifier) {
    println("Composing MedicationTrackerMedsList")
    Column (modifier.padding(top = 16.dp)) {
        meds.forEach { takenMed ->
            TakenMedLabelRow(modifier)
            TakenMedRow(modifier = modifier, label = takenMed.toString())
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