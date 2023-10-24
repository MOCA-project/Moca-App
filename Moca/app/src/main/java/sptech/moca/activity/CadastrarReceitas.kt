package sptech.moca.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import sptech.moca.R
import java.util.Calendar

class CadastrarReceitas : AppCompatActivity() {

    private lateinit var editTextDate: EditText
    private lateinit var datePicker: DatePicker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar_receitas)


        editTextDate = findViewById(R.id.selecionar_data)
        datePicker = findViewById(R.id.datePicker)

        editTextDate.setOnClickListener {
            showDatePicker()
        }
    }

    private fun showDatePicker() {
        datePicker.visibility = View.VISIBLE
        datePicker.init(
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        ) { _, year, month, day ->
            val selectedDate = "$day/${month + 1}/$year"
            editTextDate.setText(selectedDate)
            datePicker.visibility = View.GONE
        }
    }
}