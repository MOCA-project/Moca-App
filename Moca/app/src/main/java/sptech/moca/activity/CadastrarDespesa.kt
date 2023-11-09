package sptech.moca.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import sptech.moca.R
import sptech.moca.databinding.ActivityCadastrarReceitasBinding

class CadastrarDespesa : AppCompatActivity() {

    private lateinit var editTextDate: EditText
    private lateinit var datePicker: DatePicker
    private var selectedDate: String? = null

    val opcoesReceita = listOf(
        "Moradia",
        "Alimentação",
        "Transporte",
        "Saúde",
        "Educação",
        "Lazer",
        "Vestuário",
        "Dívidas",
        "Impostos",
        "Outras"
    )

    val tipoDespesa = listOf(
        "Dinheiro",
        "Fixa",
        "Cartão"
    )

    val qtdParcelas = listOf(
        "1x",
        "2x",
        "3x",
        "4x",
        "5x",
        "6x",
        "7x",
        "8x",
        "9x",
        "10x",
        "11x",
        "12x"
    )

    val binding by lazy {
        ActivityCadastrarReceitasBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar_despesa)
    }
}