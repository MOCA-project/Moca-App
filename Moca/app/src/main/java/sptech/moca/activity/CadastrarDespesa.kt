package sptech.moca.activity

import android.R.id
import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.EditText
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response
import sptech.moca.R
import sptech.moca.api.EndpointCartao
import sptech.moca.api.EndpointDespesa
import sptech.moca.databinding.ActivityCadastrarDespesaBinding
import sptech.moca.model.CartaoModel
import sptech.moca.model.OpcaoReceitaDespesaModel
import sptech.moca.util.NetworkUtils
import java.util.Calendar

class CadastrarDespesa : AppCompatActivity() {

    private lateinit var editTextDate: EditText
    private lateinit var datePicker: DatePicker
    private var selectedDate: String? = null
    private var idCartao: Int? = null
    private var listaCartoes: List<CartaoModel> = emptyList()

    val opcoesDespesa = listOf(
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
        ActivityCadastrarDespesaBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar_despesa)

        receberCartoesCliente()

        binding.selecionarData.setOnClickListener {
            hideKeyboard()
            showDatePicker()
        }

        binding.selecionarData.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                hideKeyboard()
                showDatePicker()
            }
        }


        val adapterCategoria = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcoesDespesa)
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categoriaAdicionarDespesa.adapter = adapterCategoria

        val adapterTipoDespesa = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipoDespesa)
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val adapterQtdParcelas = ArrayAdapter(this, android.R.layout.simple_spinner_item, qtdParcelas)
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    private fun processSelectedDate(year: Int, month: Int, day: Int) {
        selectedDate = "$year-${month + 1}-$day"
        binding.selecionarData.setText(selectedDate)
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, day ->
                processSelectedDate(year, month, day)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }

    fun receberCartoesCliente() {
        val retrofitClient = NetworkUtils.getRetrofitInstance()
        val endpoint = retrofitClient.create(EndpointCartao::class.java)
        val sharedPreferences = getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)
        val idUsuario = sharedPreferences.getLong("idUsuario", -1)
        val callback = endpoint.getCartoes(idUsuario, Calendar.MONTH, Calendar.YEAR)

        callback.enqueue(object : retrofit2.Callback<List<CartaoModel>> {
            override fun onResponse(
                call: Call<List<CartaoModel>>,
                response: Response<List<CartaoModel>>
            ) {
                when {
                    response.isSuccessful -> {
                        listaCartoes = response.body() ?: emptyList()
                    }

                    response.code() == 401 -> println("Não autorizado. Faça login novamente.")
                    response.code() == 404 -> println("Recurso não encontrado.")
                    else -> println("Ocorreu um erro. Tente novamente mais tarde.")
                }
            }

            override fun onFailure(call: Call<List<CartaoModel>>, t: Throwable) {

            }

        })
    }

    fun cadastrarDespesaFixa() {
        val retrofitClient = NetworkUtils.getRetrofitInstance()
        val endpoint = retrofitClient.create(EndpointDespesa::class.java)
        val sharedPreferences = getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)
        val idUsuario = sharedPreferences.getLong("idUsuario", -1)
        if (idUsuario == -1L) {
            // O idUsuario é inválido, faça algo aqui, como mostrar uma mensagem de erro
            // ou redirecionar o usuário para a tela de login
            println("O idUsuario é inválido")
            return
        }
        val opcaoSelecionada =
            binding.categoriaAdicionarDespesa.selectedItem as OpcaoReceitaDespesaModel
        val idTipoDespesa = opcaoSelecionada.id
        // Crie sua carga útil "raw" como uma String
        var jsonPayload = ""
        val valorDespesaStr = binding.valorDespesaUsuario.text.toString()
// Verifica se o valorDespesaStr é um número válido
        val valorDespesa = if (valorDespesaStr.isNotBlank()) {
            valorDespesaStr.toDouble()
        } else {
            // Se não for um número válido, atribua um valor padrão ou trate conforme necessário
            0.0
        }
        if (listaCartoes.any { cartao ->
                (cartao.limite - cartao.utilizado) >= (valorDespesa * 12)
            }) {
            // Existe pelo menos um cartão que atende à condição
            // Faça o que precisa ser feito aqui
            jsonPayload = "{\"descricao\":\"${binding.descricaoDespesaUsuario.text}\"," +
                    "\"valor\":\"${binding.valorDespesaUsuario.text}\"," +
                    "\"data\":\"${selectedDate}\"," +
                    "\"idCliente\":\"${idUsuario}\"," +
                    "\"idTipoReceita\":\"${idTipoDespesa}\"," +
                    "\"isCartao\":\"${true}\"" +
                    "\"idCartao\":\"$idCartao\"" +
                    "\"paid\":\"${false}\"" +
                    "\"parcela\":\"${true}\"}"
        } else {
            println("Valor ultrapassa o limite do cartão")
        }

//        if (idCartao == 0 && binding.valorDespesaUsuario.text.toString()
//                .toInt() > 0 && binding.categoriaAdicionarDespesa.equals() != "0" && binding.descricaoDespesaUsuario.text.isEmpty() && selectedDate != ""
//        ) {
//
//        }

        // Converta a String em um RequestBody
        val requestBody = jsonPayload.toRequestBody("application/json".toMediaTypeOrNull())

        // Faça a chamada à API passando o RequestBody
        val callback = endpoint.postCadastrarDespesaFixa(requestBody)
    }
}