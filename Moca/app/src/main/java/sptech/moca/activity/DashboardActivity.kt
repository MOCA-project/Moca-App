package sptech.moca.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import sptech.moca.R
import sptech.moca.api.EndpointClient
import sptech.moca.api.EndpointHome
import sptech.moca.databinding.ActivityDashboardBinding
import sptech.moca.util.NetworkUtils
import java.util.Calendar

class DashboardActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityDashboardBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Esconder a barra de menu do topo
        supportActionBar?.hide()
        dashboardRequest()
    }

    private fun dashboardRequest() {

        val retrofitClient = NetworkUtils.getRetrofitInstance("http://26.239.63.16:8080/api/")
        val endpoint = retrofitClient.create(EndpointHome::class.java)

        val sharedPreferences = getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)
        val idUsuario = sharedPreferences.getLong("idUsuario", 0L)
        // Pegar o ano atual
        val calendar = Calendar.getInstance()
        val ano = calendar.get(Calendar.YEAR)

        val posicaoSpinner = binding.spinnerMeses.selectedItemPosition + 1

        val callback = endpoint.getInformations(idUsuario, posicaoSpinner, ano)

    }
}