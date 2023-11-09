package sptech.moca.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.EditText
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response
import sptech.moca.R
import sptech.moca.api.EndpointClient
import sptech.moca.api.EndpointReceita
import sptech.moca.databinding.ActivityCadastrarReceitasBinding
import sptech.moca.databinding.ActivityCadastroBinding
import sptech.moca.fragment.ReceitaFragment
import sptech.moca.model.OpcaoReceitaModel
import sptech.moca.model.ReceitaModel
import sptech.moca.util.NetworkUtils
import java.util.Calendar

class CadastrarReceitas : AppCompatActivity() {

    private lateinit var editTextDate: EditText
    private lateinit var datePicker: DatePicker
    private var selectedDate: String? = null

    val opcoesReceita = listOf(
        "Salário",
        "Rendimentos",
        "Vendas de Bens",
        "Freelance",
        "Aluguel",
        "Ajuda Financeira",
        "Reembolsos",
        "Prêmios",
        "Outras fontes de receita"
    )

    val binding by lazy {
        ActivityCadastrarReceitasBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        editTextDate = binding.selecionarData
        datePicker = binding.datePicker

        editTextDate.setOnClickListener {
            showDatePicker()
        }

        binding.cadastrarReceita.setOnClickListener {
            when {
                binding.valorAdicionarReceita.text.isBlank() -> {
                    binding.valorAdicionarReceita.error = "Preencha os campos!"
                    return@setOnClickListener // "mata" o método
                }

                binding.descricaoAdicionarReceita.text.isBlank() -> {
                    binding.descricaoAdicionarReceita.error = "Preencha os campos!"
                    return@setOnClickListener // "mata" o método
                }

                else -> {
                    cadastrarReceita()
                }
            }
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcoesReceita)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categoriaAdicionarReceita.adapter = adapter
    }

    private fun showDatePicker() {
        datePicker.visibility = View.VISIBLE
        datePicker.init(
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        ) { _, year, month, day ->
            selectedDate = "$day/${month + 1}/$year"
            editTextDate.setText(selectedDate)
            datePicker.visibility = View.GONE
        }
    }

    private fun cadastrarReceita() {

        val retrofitClient = NetworkUtils.getRetrofitInstance()
        val endpoint = retrofitClient.create(EndpointReceita::class.java)

        val sharedPreferences = getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)
        val idUsuario = sharedPreferences.getLong("idUsuario", -1)

        if (idUsuario == -1L) {
            // O idUsuario é inválido, faça algo aqui, como mostrar uma mensagem de erro
            // ou redirecionar o usuário para a tela de login
            println("O idUsuario é inválido")
            return
        }

        val opcaoSelecionada = binding.categoriaAdicionarReceita.selectedItem as OpcaoReceitaModel
        val idTipoReceita = opcaoSelecionada.id


        // Crie sua carga útil "raw" como uma String
        val jsonPayload =
            "{\"descricao\":\"${binding.descricaoAdicionarReceita.text}\",\"valor\":\"${binding.valorAdicionarReceita.text}\",\"data\":\"${selectedDate}\",\"idCliente\":\"${idUsuario}\",\"idTipoReceita\":\"${idTipoReceita}\"}"

        // Converta a String em um RequestBody
        val requestBody = jsonPayload.toRequestBody("application/json".toMediaTypeOrNull())

        // Faça a chamada à API passando o RequestBody
        val callback = endpoint.postCadastrarReceita(requestBody)

        callback.enqueue(object : retrofit2.Callback<ReceitaModel> {
            override fun onResponse(call: Call<ReceitaModel>, response: Response<ReceitaModel>) {
                when {
                    response.isSuccessful -> {
                        val intent = Intent(this@CadastrarReceitas, ReceitaFragment::class.java)
                        startActivity(intent)
                    }
                }
            }

            override fun onFailure(call: Call<ReceitaModel>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }
}
