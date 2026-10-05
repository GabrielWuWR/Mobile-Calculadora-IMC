package com.example.calculadora

import android.R.attr.onClick
import android.os.Bundle
import android.util.Log.e
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadora.statusIMC
import com.example.calculadora.ui.theme.CalculadoraTheme
import java.math.RoundingMode
import kotlin.jvm.Throws

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    IMCScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

var altura by mutableStateOf("")
var peso by mutableStateOf("")

var imcTela by mutableStateOf("")

var statusIMC by mutableStateOf("");

@Composable
fun IMCScreen(modifier: Modifier = Modifier) {
    var imc = 0.0;
    var corContainer by remember { mutableStateOf(Color(0x00FFFFFF)) };

    fun calcularIMC(peso: String, altura: String): Double {
        val alturaReal = altura.replace(',', '.').toDouble();
        val pesoReal = peso.replace(',', '.').toDouble();

        if(pesoReal == null || pesoReal <= 20 || pesoReal >= 635 || alturaReal == null || alturaReal <= 1 || alturaReal >= 2.72) {
            if(alturaReal >= 100.00) {
                val imcCalculo = pesoReal / (alturaReal / 100 * alturaReal / 100);

                return imcCalculo.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
            } else {
                throw IllegalArgumentException("Peso e altura devem ser valores válidos.")
            }

        } else {
            val imcCalculo = pesoReal / (alturaReal * alturaReal);

            return imcCalculo.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
        }
    }

    fun estadoIMC(imc: Double): String {
        var resposta = "";

        if(imc < 18.5) {
            resposta = "Abaixo do peso."
            corContainer = Color(0xFF3A86FF);
        } else if (imc > 18.5 && imc < 25) {
            resposta = "Peso ideal."
            corContainer = Color(0xFF38B000);
        } else if (imc >= 25 && imc < 30) {
            resposta = "Levemente acima do peso."
            corContainer = Color(0xFFFFB703)
        } else if (imc >= 30 && imc < 35) {
            resposta = "Obesidade grau I"
            corContainer = Color(0xFFFB8500)
        } else if (imc >= 35 && imc < 40) {
            resposta = "Obesidade grau II"
            corContainer = Color(0xFFD62828)
        }else if (imc >= 40) {
            resposta = "Obesidade grau III"
            corContainer = Color(0xFF7A0010)
        }

        return resposta;
    }

    Column(modifier = modifier
        .fillMaxSize()
    ) {

        // -- HEADER -- //
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(color = colorResource(id = R.color.cor_app)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.bmi),
                contentDescription = "Logo APP",
                modifier = Modifier
                    .size(80.dp)
                    .padding(vertical = 16.dp)
            )

            Text(
                text = "Calculadora IMC",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        // -- FORMULARIO -- //
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Card(modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .offset(y = (-30).dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF9F6F6)
                ),
                elevation = CardDefaults.cardElevation(4.dp),
            ) {

                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Spacer(
                        modifier = modifier.height(5.dp)
                    )
                    Text(
                        text = "Seus dados",
                        color = Color(0xFF4CA6D5),
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = altura,
                        onValueChange = { altura = it },
                        singleLine = true,
                        label = {
                            Text(text = "Altura")
                        },
                        placeholder = {
                            Text(text = "Digite sua altura")
                        },

                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xFF4CA6D5),
                            focusedBorderColor = Color(0xFF4CA6D5)
                        ),
                        shape = RoundedCornerShape(15.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = peso,
                        onValueChange = { peso = it },
                        singleLine = true,
                        label = {
                            Text(text = "Peso")
                        },
                        placeholder = {
                            Text(text = "Digite seu peso")
                        },

                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xFF4CA6D5),
                            focusedBorderColor = Color(0xFF4CA6D5)
                        ),
                        shape = RoundedCornerShape(15.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .width(300.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            modifier = Modifier.width(150.dp).height(50.dp),
                            onClick = {
                                try {
                                    val resultCalculoIMC = calcularIMC(peso = peso, altura = altura)
                                    imc = resultCalculoIMC;
                                    imcTela = imc.toString();
                                    statusIMC = estadoIMC(imc = resultCalculoIMC);

                                } catch (error: Throwable) {
                                    imc = 0.0;
                                    imcTela = "Digite números válidos."
                                    statusIMC = ""
                                    corContainer = Color(0xFF7A0010)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4CA6D5),
                                contentColor = Color.White
                            )
                        ) {
                            Text("Calcular")
                        }

                        Button(
                            modifier = Modifier.width(120.dp).height(50.dp),
                            onClick = {
                                altura = "";
                                peso = "";
                                corContainer = Color(0x007A0010)
                                imcTela = ""
                                statusIMC = ""
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFEAEAEA),
                                contentColor = Color.Black
                            )
                        ) {
                            Text("Resetar")
                        }
                    }
                }

            }

            Row(modifier = modifier
                .clip(RoundedCornerShape(15.dp))
                .background(corContainer)
                .width(350.dp)
                .height(90.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "$imcTela $statusIMC", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}