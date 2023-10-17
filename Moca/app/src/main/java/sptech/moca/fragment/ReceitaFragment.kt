package sptech.moca.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Response
import sptech.moca.R
import sptech.moca.api.EndpointHome
import sptech.moca.api.EndpointReceita
import sptech.moca.databinding.ActivityDashboardBinding
import sptech.moca.databinding.ActivityReceitaBinding
import sptech.moca.model.HomeInformationsModel
import sptech.moca.model.ReceitaModel
import sptech.moca.util.NetworkUtils
import java.util.Calendar
import kotlin.math.roundToInt

class ReceitaFragment : Fragment() {

    private var _binding: ActivityReceitaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityReceitaBinding.inflate(inflater, container, false)
        val view = binding.root

        // Inflar o layout do fragmento aqui
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Configurar a lógica do fragmento aqui
        // Por exemplo, esconder a barra de menu do topo
//        requireActivity().supportActionBar?.hide()

        dashboardRequest()
    }


    private fun receitaRequest() {
        val retrofitClient = NetworkUtils.getRetrofitInstance("http://26.239.63.16:8080/api/")
        val endpoint = retrofitClient.create(EndpointReceita::class.java)
        val sharedPreferences =
            requireActivity().getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)
        val idUsuario = sharedPreferences.getLong("idUsuario", 0L)
        val calendar = Calendar.getInstance()
        val ano = calendar.get(Calendar.YEAR)
        val posicaoSpinner = binding.spinnerMeses.selectedItemPosition + 1
        val callback = endpoint.getInformacoesReceita(idUsuario, posicaoSpinner, ano)

        callback.enqueue(object : retrofit2.Callback<ReceitaModel> {
            override fun onResponse(call: Call<ReceitaModel>, response: Response<ReceitaModel>) {
                if (response.isSuccessful) {

                }
            }

            override fun onFailure(call: Call<ReceitaModel>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun dashboardRequest() {
        val retrofitClient = NetworkUtils.getRetrofitInstance("http://26.239.63.16:8080/api/")
        val endpoint = retrofitClient.create(EndpointHome::class.java)
        val sharedPreferences =
            requireActivity().getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)
        val idUsuario = sharedPreferences.getLong("idUsuario", 0L)
        val calendar = Calendar.getInstance()
        val ano = calendar.get(Calendar.YEAR)
        val posicaoSpinner = binding.spinnerMeses.selectedItemPosition + 1
        val callback = endpoint.getInformations(idUsuario, posicaoSpinner, ano)



        callback.enqueue(object : retrofit2.Callback<HomeInformationsModel> {
            override fun onResponse(
                call: Call<HomeInformationsModel>,
                response: Response<HomeInformationsModel>
            ) {
                if (response.isSuccessful) {
                    // Valores
                    "R$ ${response.body()!!.receita}".also {
                        binding.receitaUsuarioRecetas.text = it
                    }
                }
            }

            override fun onFailure(call: Call<HomeInformationsModel>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}