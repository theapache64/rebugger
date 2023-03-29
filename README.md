# 🐞 Rebugger

Being a “compose dev” our enemy number one is unnecessary recompositions 🤕. Often times we use tools like [recompositionHighligher](https://github.com/theapache64/boil/blob/master/files/RecompositionHighlighter.kt), [LogComposition](https://github.com/theapache64/boil/blob/master/files/LogComposition.kt), and layout inspector to count the recomposition, but there’s no direct way to understand “why” the recomposition has happened.

Rebugger is a simple compose utility function that can track the change in the given arguments. It’ll print the reason for recomposition in your Logcat window.

## ⌨️ Demo

![](https://i.imgur.com/jztXzP9.png)

## Usage

### 1. Add dependencies

![latestVersion](https://img.shields.io/github/v/release/theapache64/rebugger)

```groovy
repositories {
  maven { url 'https://jitpack.io' } // Add jitpack
}

dependencies {
  implementation 'com.github.theapache64:rebugger:<latest.version>'
}
```

### 2. Add `Rebugger` call

> Call `Rebugger` with the states or args you want to track

```kotlin
@Composable
fun VehicleUi(
    car: Car,
    bike: Bike,
) {
    var human by remember { mutableStateOf(Human("John")) }

    // Call Rebugger and pass the states you want to track. 
    // It could be a function arg or a state
    Rebugger(
        trackMap = mapOf(
            "car" to car,
            "bike" to bike,
            "human" to human
        ),
    )
    
    //...
```

### 3. See `LogCat`

> Search for `Rebugger`

![](https://i.imgur.com/jztXzP9.png)


### 🖥 Sample Outputs


When Rebugger hooked into your composable, it’ll print something like this


When VehicleUi recomposes due to car instance change



When VehicleUi recomposes due to both  car and bike instance change



When VehicleUi recomposes due to human instance change (State within the composable)



## 🟠 Limitation

### Auto Name Picking

When Rebugger is placed deep inside the composable, it may not be able to pick the correct composable name. For example, if I place the Rebugger somewhere inside the Button lambda like this

```kotlin
@Composable
fun VehicleUi(
car: Car,
bike: Bike,
) {
// ...

    Column {
        // ...

        Button(
            onClick = {
                //...
            }
        ) {

            // 🟠 Inside Button's content lambda
            Rebugger(
                trackMap = mapOf(
                    "car" to car,
                    "bike" to bike,
                    "human" to human
                ),
            )
            
            // ...
        }
    }
}
```

It’ll print something like this

### Fix

To fix this, you can pass composableName argument to override the automatic name picking behaviour

```kotlin
Rebugger(
    composableName = "Button's body",
    trackMap = mapOf(
        "car" to car,
        "bike" to bike,
        "human" to human
    ),
)
```

## 🌇 TODO

- IDE plugin : To generate `Rebugger` call (vote here)
