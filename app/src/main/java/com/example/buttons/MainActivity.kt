package com.example.buttons

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    val size = 15

    private lateinit var edtcodigo: EditText
    private lateinit var edtmarca: EditText
    private lateinit var edtmodelo: EditText
    private lateinit var edtcosto: EditText

    private lateinit var gpoRadTallas: RadioGroup
    private lateinit var radTallaChica: RadioButton
    private lateinit var radTallaMediana: RadioButton
    private lateinit var radTallaGrande: RadioButton

    private lateinit var chColorNegro: CheckBox
    private lateinit var chColorBlanco: CheckBox
    private lateinit var chColorAzul: CheckBox
    private lateinit var chColorRojo: CheckBox
    private lateinit var chColorNaranja: CheckBox

    private lateinit var boton: ImageButton

    private var ropaArray = Array(size){RopaDeportiva("", "", "", "", "", 0.0)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        edtcodigo = findViewById(R.id.edtCodigo)
        edtmarca = findViewById(R.id.edtMarca)
        edtmodelo = findViewById(R.id.edtModelo)
        edtcosto = findViewById(R.id.edtCosto)

        gpoRadTallas = findViewById(R.id.gpoRadTallas)
        radTallaChica = findViewById(R.id.radTallaChica)
        radTallaMediana = findViewById(R.id.radTallaMediana)
        radTallaGrande = findViewById(R.id.radTallaGrande)

        chColorNegro = findViewById(R.id.chColorNegro)
        chColorBlanco = findViewById(R.id.chColorBlanco)
        chColorAzul = findViewById(R.id.chColorAzul)
        chColorRojo = findViewById(R.id.chColorRojo)
        chColorNaranja = findViewById(R.id.chColorNaranja)

        boton = findViewById(R.id.ibtnAdd)

        boton.setOnClickListener{ btnAdd() }
    }

    var contador = 0
    var talla: String = ""
    var color: String = ""

    fun selectedTalla(){
        talla = when {
            radTallaChica.isChecked -> "Chica"
            radTallaMediana.isChecked -> "Mediana"
            radTallaGrande.isChecked -> "Grande"
            else -> ""
        }
    }

    fun selectedColor(){
        var coloresElegidos = mutableListOf<String>()

        if(chColorNegro.isChecked) coloresElegidos.add("Negro")
        if(chColorBlanco.isChecked) coloresElegidos.add("Blanco")
        if(chColorAzul.isChecked) coloresElegidos.add("Azul")
        if(chColorRojo.isChecked) coloresElegidos.add("Rojo")
        if(chColorNaranja.isChecked) coloresElegidos.add("Naranja")

        color = coloresElegidos.joinToString(separator = ",")
    }

    fun onClick(v: View) {
        when (v?.id) {
            R.id.ibtnAdd -> { btnAdd() }
            R.id.ibtnSearch -> { btnSearch() }
            R.id.ibtnDelete -> { btnDelete() }
            R.id.ibtnClean -> { btnClean() }
        }
    }

    private fun btnAdd(){
        if (edtcodigo.text.isNotBlank() && edtcodigo.text.isNotEmpty() &&
            edtmarca.text.isNotEmpty() && edtmarca.text.isNotBlank() &&
            edtmodelo.text.isNotEmpty() && edtmodelo.text.isNotBlank() &&
            edtcosto.text.isNotEmpty() && edtcosto.text.isNotEmpty()) {
            if(contador < ropaArray.size) {
                selectedTalla()
                selectedColor()

                val aux = RopaDeportiva(edtcodigo.text.toString(),
                        edtmarca.text.toString(),
                        edtmodelo.text.toString(),
                        talla,
                        color,
                        edtcosto.text.toString().toDouble())

                ropaArray[contador] = aux
                contador++

                Toast.makeText(this, "Código ${ropaArray[contador]!!.codigo} registrado", Toast.LENGTH_LONG).show()
                btnClean()
            } else {
                Toast.makeText(this, "Arreglo lleno :c", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Registra el código de la prenda", Toast.LENGTH_LONG).show()
        }
    }

    private fun btnSearch(){
        val codigo: String = edtcodigo.text.toString()
        var encontrado = false

        if(edtcodigo.text.isNotEmpty() && edtcodigo.text.isNotBlank()){
            for (i in ropaArray) {
                if(codigo == i.codigo) {
                    edtcodigo.setText(i.codigo)
                    edtmarca.setText(i.marca)
                    edtmodelo.setText(i.modelo)
                    edtcosto.setText(i.costo.toString())

                    when(i.talla) {
                        "Chica" -> radTallaChica.isChecked = true
                        "Mediana" -> radTallaMediana.isChecked = true
                        "Grande" -> radTallaGrande.isChecked = true
                    }

                    val coloresElegidos = i.colores.split(",").toSet()
                    chColorNegro.isChecked = "Negro" in coloresElegidos
                    chColorBlanco.isChecked = "Blanco" in coloresElegidos
                    chColorAzul.isChecked = "Azul" in coloresElegidos
                    chColorRojo.isChecked = "Rojo" in coloresElegidos
                    chColorNaranja.isChecked = "Naranja" in coloresElegidos

                    encontrado = true
                    Toast.makeText(this, "Prenda encontrada", Toast.LENGTH_SHORT).show()
                    break
                }
            }
            if(!encontrado) {
                Toast.makeText(this, "No se encontró la prenda :c", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Ingresa codigo para buscar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun btnDelete(){
        val codigo: String = edtcodigo.text.toString()
        var encontrado = false
        var moveArray = -1

        for(i in ropaArray.indices) {
            if(ropaArray[i].codigo == codigo) {
                ropaArray[i] = RopaDeportiva("", "", "", "", "", 0.0)
                encontrado = true
                moveArray = i
                Toast.makeText(this, "Prenda eliminada", Toast.LENGTH_SHORT).show()
                break
            }
        }
        if(!encontrado) {
            Toast.makeText(this, "No se encontró la prenda :c", Toast.LENGTH_SHORT).show()
        }
//        if(moveArray != -1) {
//            while (moveArray <= contador) {
//                ropaArray[moveArray] = ropaArray[moveArray + 1]
//                moveArray++
//            }
//            contador--
//        }
        btnClean()
    }

    private fun btnClean(){
        edtcodigo.text.clear()
        edtcosto.text.clear()
        edtmodelo.text.clear()
        edtmarca.text.clear()

        chColorNegro.isChecked = false
        chColorBlanco.isChecked = false
        chColorAzul.isChecked = false
        chColorRojo.isChecked = false
        chColorNaranja.isChecked = false

        gpoRadTallas.clearCheck()

        edtcodigo.requestFocus()
    }
}