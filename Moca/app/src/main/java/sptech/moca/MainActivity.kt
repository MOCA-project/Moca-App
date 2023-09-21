package sptech.moca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {


    //==================================================================================================
//        Função para animação fab
//==================================================================================================
    private fun toggleMenu() {
        val semiTransparentBackground = findViewById<View>(R.id.semiTransparentBackground)
        if (isOpen) {
            fabAdd.startAnimation(rotateBackward)
            fabCartoes.startAnimation(fabClose)
            fabReceitas.startAnimation(fabClose)
            fabDespesas.startAnimation(fabClose)
            fabPorquinho.startAnimation(fabClose)
            semiTransparentBackground.visibility = View.GONE
            fabCartoes.isClickable = false
            fabReceitas.isClickable = false
            fabDespesas.isClickable = false
            fabPorquinho.isClickable = false
        } else {
            fabAdd.startAnimation(rotateForward)
            fabCartoes.startAnimation(fabOpen)
            fabReceitas.startAnimation(fabOpen)
            fabDespesas.startAnimation(fabOpen)
            fabPorquinho.startAnimation(fabOpen)
            semiTransparentBackground.visibility = View.VISIBLE
            fabCartoes.isClickable = true
            fabReceitas.isClickable = true
            fabDespesas.isClickable = true
            fabPorquinho.isClickable = true
        }
        isOpen = !isOpen // Inverter o estado do isOpen
    }







    //==================================================================================================
//        Definindo botões fab e animações para os botões
//==================================================================================================
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var fabReceitas: FloatingActionButton
    private lateinit var fabDespesas: FloatingActionButton
    private lateinit var fabPorquinho: FloatingActionButton
    private lateinit var fabCartoes: FloatingActionButton
    private lateinit var fabOpen: Animation
    private lateinit var fabClose: Animation
    private lateinit var rotateForward: Animation
    private lateinit var rotateBackward: Animation
    private var isOpen: Boolean = false   //Por padrão é falso











    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        // Removendo a sombra do menu bottom
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.background = null
        // Desativando o botao vazio que arruma o menu bottom
        bottomNavigationView.menu.getItem(2).isEnabled = false

        // Esconder a barra de menu do topo
        supportActionBar?.hide()










//==================================================================================================
//        Botões fab
//==================================================================================================

//        Botões
        fabAdd = findViewById(R.id.floatingActionButton)
        fabCartoes = findViewById(R.id.floatingActionButtonCartoes)
        fabReceitas = findViewById(R.id.floatingActionButtonReceitas)
        fabDespesas = findViewById(R.id.floatingActionButtonDespesas)
        fabPorquinho = findViewById(R.id.floatingActionButtonPorquinho)


//        Animações
        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close)
        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward)
        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backwawrd)

        fabAdd.setOnClickListener {
            toggleMenu()
        }

        fabCartoes.setOnClickListener {
            toggleMenu()
            Toast.makeText(this@MainActivity, "cartoes clicado", Toast.LENGTH_SHORT).show()
        }

        fabReceitas.setOnClickListener {
            toggleMenu()
            Toast.makeText(this@MainActivity, "receitas clicado", Toast.LENGTH_SHORT).show()
        }

        fabDespesas.setOnClickListener {
            toggleMenu()
            Toast.makeText(this@MainActivity, "despesas clicado", Toast.LENGTH_SHORT).show()
        }

        fabPorquinho.setOnClickListener {
            toggleMenu()
            Toast.makeText(this@MainActivity, "porquinho clicado", Toast.LENGTH_SHORT).show()
        }


        // Encontre os ícones do FAB e converta-os em Drawable
        val fabCorIconCartao = findViewById<FloatingActionButton>(R.id.floatingActionButtonCartoes)
        val fabCorIconReceita =
            findViewById<FloatingActionButton>(R.id.floatingActionButtonReceitas)
        val fabCorIconDespesa =
            findViewById<FloatingActionButton>(R.id.floatingActionButtonDespesas)
        val fabCorIconPorquinho =
            findViewById<FloatingActionButton>(R.id.floatingActionButtonPorquinho)

// Defina cores diferentes para cada ícone
        val corIconCartao = ContextCompat.getColor(this, R.color.azul_botao)
        val corIconReceita = ContextCompat.getColor(this, R.color.verde)
        val corIconDespesa = ContextCompat.getColor(this, R.color.vermelho)
        val corIconPorquinho = ContextCompat.getColor(this, R.color.rosa)

// Aplicar cores aos ícones
        applyColorToFabIcon(fabCorIconCartao, corIconCartao)
        applyColorToFabIcon(fabCorIconReceita, corIconReceita)
        applyColorToFabIcon(fabCorIconDespesa, corIconDespesa)
        applyColorToFabIcon(fabCorIconPorquinho, corIconPorquinho)


    }








    //==================================================================================================
//        Função para aplicar a cor ao ícone
//==================================================================================================
    private fun applyColorToFabIcon(fab: FloatingActionButton, color: Int) {
        val fabIcon = fab.drawable

        // Verifique se o ícone não é nulo
        if (fabIcon != null) {
            // Aplique a cor ao ícone
            DrawableCompat.setTint(fabIcon, color)

            // Defina o ícone modificado de volta no FAB
            fab.setImageDrawable(fabIcon)
        }
    }
}