package sptech.moca.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sptech.moca.R
import sptech.moca.databinding.ActivityConfiguracoesBinding
import sptech.moca.databinding.ActivityReceitaBinding

class ConfiguracoesFragment : Fragment() {

    private var _binding: ActivityConfiguracoesBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityConfiguracoesBinding.inflate(inflater, container, false)

        binding.btnSair.setOnClickListener{
            deslogar()
        }


        // Inflar o layout do fragmento de Configurações aqui
        return binding.root
    }

    // Outros métodos e lógica relacionada ao fragmento de Configurações
    private fun deslogar() {
        val sharedPreferences = requireContext().getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putBoolean("firstTime", true)
        editor.putLong("idUsuario", 0)
        editor.putString("token", "")
        editor.apply()
    }
}