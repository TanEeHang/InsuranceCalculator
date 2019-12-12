package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: TotalModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(TotalModel::class.java)

        if(viewModel.total != 0){
            textViewPremium.text = viewModel.total.toString()
        }

        val btnCal = findViewById(R.id.buttonCalculate) as Button

        btnCal.setOnClickListener(){
            calculation(it)
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }

        val btnReset = findViewById(R.id.buttonReset) as Button

        btnReset.setOnClickListener(){
            reset(it)
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }
    }

    private fun reset(view: View){
      //  val age = findViewById<Spinner>(R.id.s)
       // val smoker = findViewById<CheckBox>(R.id.checkBoxSmoker)
        //val radio = findViewById<RadioGroup>(R.id.radioGroupGender)

        spinnerAge.setSelection(0)
        radioGroupGender.clearCheck()
        checkBoxSmoker.setChecked(false)
        textViewPremium.text="Insurance Premium"

        Toast.makeText(this, "Reset Complete",
            Toast.LENGTH_SHORT).show()

    }

    private fun calculation(view: View){

        val radio: RadioButton = findViewById(radioGroupGender.checkedRadioButtonId)
        val smoker = findViewById<CheckBox>(R.id.checkBoxSmoker)
        val resultView = findViewById<TextView>(R.id.textViewPremium)

        val ageSelected = spinnerAge.selectedItem.toString()
        var premium = 0
        var smoke = 0
        var male = 0
        var total = 0


        //premium
        if(ageSelected != "Less than 17"){
            when(ageSelected){
                "17 to 25" -> premium = 70
                "26 to 30" -> premium = 90
                "31 to 40" -> premium = 120
                else -> premium = 150
            }
        }else{
            premium = 60
        }

        //male
        if(radio.text == "Male"){
            when(ageSelected){
                "17 to 25" -> male = 50
                "26 to 30" -> male = 100
                "31 to 40" -> male = 150
                "41 to 45" -> smoke = 200
                "More than 55" -> smoke = 200
            }
        }

        //smoke
        if(smoker.isChecked){
            when(ageSelected){
                "17 to 25" -> smoke = 100
                "26 to 30" -> smoke = 150
                "31 to 40" -> smoke = 200
                "41 to 45" -> smoke = 250
                "More than 55" -> smoke = 300
            }
        }

        total = premium + smoke + male

        viewModel.total = total

        textViewPremium.text =  total.toString()

    }

    class TotalModel : ViewModel() {
        var total:Int = 0

    }


}
