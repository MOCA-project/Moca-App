package sptech.moca.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sptech.moca.R

class ConfiguracoesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar o layout do fragmento de Configurações aqui
        return inflater.inflate(R.layout.activity_configuracoes, container, false)
    }

    // Outros métodos e lógica relacionada ao fragmento de Configurações
}