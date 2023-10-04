package sptech.moca.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import sptech.moca.R

class Login : AppCompatActivity() {

    private lateinit var onBoardingScreen: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()


//==================================================================================================
//        Mudar para a tela de cadastro
//==================================================================================================
        val textJaTemCadastro = findViewById<TextView>(R.id.cadastrarTextBtn)
        textJaTemCadastro.setOnClickListener {
            val intent = Intent(this, Cadastro::class.java)
            startActivity(intent)
        }


//==================================================================================================
//        Mudar para a tela de Dashboard quando usuário estiver logado
//==================================================================================================
        val usuarioLogado = findViewById<Button>(R.id.btnLogin)
        usuarioLogado.setOnClickListener {

            onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE)
            val isFirstTime: Boolean = onBoardingScreen.getBoolean("firstTime", true)

            if(isFirstTime){
                var editor: SharedPreferences.Editor = onBoardingScreen.edit()
                editor.putBoolean("firstTime", false)
                editor.commit()
                val intent = Intent(this, Onboarding::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }


//==================================================================================================
//        Mudar para a tela de resetar senha
//==================================================================================================
        val textRedefineSenha = findViewById<TextView>(R.id.esqueceuSenhaTextBn)
        textRedefineSenha.setOnClickListener {
            val intent = Intent(this, ResetPassword::class.java)
            startActivity(intent)
        }


//==================================================================================================
//        Mostrar e esconder senha da tela de Login
//==================================================================================================
        val editarTextSenha = findViewById<EditText>(R.id.inputSenha)
        val imageViewSenhaVisivel = findViewById<ImageView>(R.id.iconeSenhaVer)

        imageViewSenhaVisivel.setOnClickListener(View.OnClickListener {
            if (editarTextSenha.inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                editarTextSenha.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                imageViewSenhaVisivel.setImageResource(R.drawable.mostrar_senha)
            } else {
                editarTextSenha.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                imageViewSenhaVisivel.setImageResource(R.drawable.esconder_senha)
            }

            // Coloque o cursor no final do texto
            editarTextSenha.setSelection(editarTextSenha.text.length)
        })
    }
}