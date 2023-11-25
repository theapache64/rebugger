# 🐞 Rebugger

> Rebugger : A recomposition debugger

Being a “compose dev” our enemy number one is unnecessary recompositions 🤕. Often times we use tools like [recompositionHighligher](https://github.com/theapache64/boil/blob/master/files/RecompositionHighlighter.kt), [LogComposition](https://github.com/theapache64/boil/blob/master/files/LogComposition.kt), and layout inspector to count the recomposition, but there’s no direct way to understand “why” the recomposition has happened.

Rebugger is a simple compose utility function that can track the change in the given arguments. It’ll print the reason for recomposition in your Logcat window.

## ⌨️ Demo

![](https://i.imgur.com/jztXzP9.png)

## Usage

### 1. Add dependencies

[![latestVersion](https://maven-badges.herokuapp.com/maven-central/io.github.theapache64/rebugger/badge.svg)](https://mvnrepository.com/artifact/io.github.theapache64/rebugger)

**Kotlin Script**

```kotlin
repositories {
    ...
    mavenCentral()
}

dependencies {
    implementation("io.github.theapache64:rebugger:1.0.0-rc01")
}
```

**Groovy**

```groovy
repositories {
    ...
    mavenCentral() 
}

dependencies {
    implementation 'io.github.theapache64:rebugger:1.0.0-rc01'
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


## 🖥 Sample Outputs

- When Rebugger hooked into your composable, it’ll print something like this

![image](https://user-images.githubusercontent.com/9678279/228623885-b0516fbd-518b-4135-9016-1928c57bc0c1.png)

- When VehicleUi recomposes due to car instance change

![image](https://user-images.githubusercontent.com/9678279/228624041-e6143b07-ca19-4c03-b49f-9b5bee7f936d.png)

- When VehicleUi recomposes due to both  car and bike instance change

![image](https://user-images.githubusercontent.com/9678279/228624288-498558a1-697a-46e7-99a6-e2c53ff1f975.png)

- When VehicleUi recomposes due to human instance change (State within the composable)

![image](https://user-images.githubusercontent.com/9678279/228624484-c1de1112-c13c-4b9b-8788-e2a4b917368e.png)

## 🎨 Customization

You can use the `RebuggerConfig.init` function to override default properties of Rebugger. 

```kotlin
class App : Application() {
    // ...
    
    override fun onCreate() {
        super.onCreate()
        
        // ...
        
        RebuggerConfig.init(
            tag = "MyAppRebugger", // changing default tag
            logger = { tag, message -> Timber.i(tag, message) } // use Timber for logging
        )
    }
}
```

## 🔌 Plugin

You can use the [Rebugger IntelliJ plugin](https://plugins.jetbrains.com/plugin/21633-rebugger) to generate the `Rebugger` function call.

https://user-images.githubusercontent.com/9678279/235495156-445e7ced-30fa-41dd-9b37-84b80502187f.mov


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

![image](https://user-images.githubusercontent.com/9678279/229018052-97d66da3-c716-4553-a930-92ca740facc2.png)


### The Fix

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
