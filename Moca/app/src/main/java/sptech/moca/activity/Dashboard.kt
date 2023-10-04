package sptech.moca.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import sptech.moca.R

class Dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Esconder a barra de menu do topo
        supportActionBar?.hide()
    }
}