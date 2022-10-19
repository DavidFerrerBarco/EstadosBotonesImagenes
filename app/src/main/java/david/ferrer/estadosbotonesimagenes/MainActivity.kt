package david.ferrer.estadosbotonesimagenes

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import david.ferrer.estadosbotonesimagenes.ui.theme.EstadosBotonesImagenesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EstadosBotonesImagenesTheme {
                Column {
                    MyImagenTwitter()
                    Separador()
                    MyPaisaje()
                    Separador()
                    MyDescription()
                }
            }
        }
    }
}

@Composable
fun Separador(){
    Spacer(modifier = Modifier.padding(10.dp))
}

@Composable
fun MyImagenTwitter(){
    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .clip(CircleShape),
            contentPadding = PaddingValues(
                start = 0.dp,
                top = 0.dp,
                end = 0.dp,
                bottom = 0.dp
            )
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.escribir
                ),
                contentDescription = "Escribir",
                modifier = Modifier.size(80.dp)
            )
        }

    }

}

@Composable
fun MyPaisaje(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(10.dp)
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.paisaje
            ),
            contentDescription = "Paisaje"
        )
    }

}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun MyDescription(){
    var nombre by remember { mutableStateOf("Mi nombre") }
    var descripcion by remember { mutableStateOf("Descripcion") }
    val checkedState = remember { mutableStateOf(false) }
    var numeroLikes = 61

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 40.dp)
    ) {

        Image(
            painter = painterResource(
                id = R.drawable.persona
            ),
            contentDescription = "Icono",
            modifier = Modifier
                .clip(CircleShape)
                .size(80.dp)
        )

        Spacer(modifier = Modifier.padding(5.dp))
        
        Column() {
            Text(
                text = nombre,
                fontSize = 15.sp
            )

            Text(
                text = descripcion,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.padding(25.dp))

        IconToggleButton(
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = !checkedState.value
            },
            modifier = Modifier.padding(10.dp)
        ) {
            val transition = updateTransition(checkedState.value)
            val tint by transition.animateColor(label = "iconColor") { isCheked ->
                if(isCheked) Color.Red else Color.Black
            }
            val size by transition.animateDp(
                transitionSpec = {
                    if(false isTransitioningTo true){
                        keyframes {
                            durationMillis = 250
                            30.dp at 0 with LinearOutSlowInEasing
                            35.dp at 15 with FastOutLinearInEasing
                            40.dp at 75
                            35.dp at 150
                        }
                    }else{
                        spring(stiffness = Spring.StiffnessVeryLow)
                    }
                },
                label = "Size"
            ){ 30.dp }

            Icon(
                imageVector = if(checkedState.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = "Icon",
                tint = tint,
                modifier = Modifier.size(size)
            )
        }

        Spacer(modifier = Modifier.padding(2.dp))

        Text(text = if(checkedState.value) (numeroLikes + 1).toString() else (numeroLikes).toString())
    }

    Separador()

    var name by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    val maxChar = 50
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = {
                if(it.length <= maxChar){
                    name = it
                }
            },
            label = { Text(text = "Nombre")},
            placeholder = {Text(text = "Introduce tu nombre")},
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Text(
            text = "${name.length} / $maxChar",
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
        )
        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = desc,
            onValueChange = {
                if(it.length <= maxChar){
                    desc = it
                }
            },
            label = { Text(text = "Descripción")},
            placeholder = {Text(text = "Introduce la descripción")},
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        Text(
            text = "${desc.length} / $maxChar",
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth().padding(end = 40.dp, top = 20.dp),
            horizontalAlignment = Alignment.End
        ) {
            Button(
                onClick = {
                    nombre = name
                    descripcion = desc
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
            ) {
                Text(text = "ACTUALIZAR", color = Color.White)
            }
        }

    }
}
