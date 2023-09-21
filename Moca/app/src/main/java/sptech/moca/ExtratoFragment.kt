package sptech.moca

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class ExtratoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar o layout do fragmento de Extrato aqui
        return inflater.inflate(R.layout.activity_extrato, container, false)
    }

    // Outros métodos e lógica relacionada ao fragmento de Extrato
}
