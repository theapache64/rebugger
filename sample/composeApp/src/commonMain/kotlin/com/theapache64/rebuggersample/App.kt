package com.theapache64.rebuggersample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
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
data class Rocket(val name: String)

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
                car = Car("Audi - ${getRandomInt()}")
            }
        ) {
            Text("Create new Audi")
        }

        Button(
            onClick = {
                bike = Bike("Yamaha - ${getRandomInt()}")
            }
        ) {
            Text("Create new Yamaha")
        }


        Button(
            onClick = {
                car = Car("Audi - ${getRandomInt()}")
                bike = Bike("Yamaha - ${getRandomInt()}")
            }
        ) {
            Text("Create Audi & Yamaha")
        }



        Text(text = "(see Logcat and search for 'Rebugger')", fontSize = 23.sp)
    }
}

fun getRandomInt(): Long {
    return (0..1000000000L).random()
}


@Composable
fun VehicleUi(
    car: Car,
    bike: Bike,
    rocket: Rocket = Rocket(name = "Falcon 9"), // by default, everytime VehicleUi is recomposed, a new instance of Rocket will be created
) {
    println("Vehicle recomposed")

    Rebugger(
        trackMap = mapOf(
            "car" to car,
            "bike" to bike,
            "rocket" to rocket
        ),
    )
    Column {
        Text("Car is $car")
        Text("Bike is $bike")
    }
}