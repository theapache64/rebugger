package com.theapache64.rebuggersample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.theapache64.rebugger.Rebugger

data class Car(val name: String)
data class Bike(val name: String)
data class Human(val name: String)

@Composable
fun RebuggerSample() {

    var car by remember { mutableStateOf(Car("Audi")) }
    var bike by remember { mutableStateOf(Bike("Yamaha")) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        VehicleUi(
            car = car,
            bike = bike
        )

        Button(
            onClick = {
                car = Car("Audi - ${System.currentTimeMillis()}")
            }
        ) {
            Text("Create new Audi")
        }

        Button(
            onClick = {
                bike = Bike("Yamaha - ${System.currentTimeMillis()}")
            }
        ) {
            Text("Create new Yamaha")
        }


        Button(
            onClick = {
                car = Car("Audi - ${System.currentTimeMillis()}")
                bike = Bike("Yamaha - ${System.currentTimeMillis()}")
            }
        ) {
            Text("Create Audi & Yamaha")
        }



        Text(text = "(see Logcat and search for 'Rebugger')", fontSize = 23.sp)
    }
}


@Composable
fun VehicleUi(
    car: Car,
    bike: Bike,
) {
    println("Vehicle recomposed")

    Rebugger(
        trackMap = mapOf(
            "car" to car,
            "bike" to bike,
        ),
    )
    Column {
        Text("Car is $car")
        Text("Bike is $bike")
    }
}


