package fr.isen.cerrone.adriendroidburger

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var selectedBurger = ""
        var burgerList = arrayListOf<String>("Burger du chef", "Cheese Burger", "Italiano Burger", "Fish Burger")
        val burgerSpinner: Spinner = findViewById(R.id.burgerSpinner)
        burgerSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, burgerList)
        burgerSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedBurger = burgerList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val mPickTimeBtn = findViewById<Button>(R.id.pickTimeBtn)
        val timeDel = findViewById<TextView>(R.id.timeTv)

        mPickTimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                timeDel.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        // handle button click
        findViewById<Button>(R.id.mySendButton).setOnClickListener {
            //val name = findViewById<TextView>(R.id.nameTextBox).text.toString().trim()
            //val name = findViewById<TextView>(R.id.nameTextBox);
            //name.setErrorEnabled(true)
            val name = findViewById<TextView>(R.id.nameTextBox)
            val strName = findViewById<TextView>(R.id.nameTextBox).editableText.toString().trim()
            val address = findViewById<TextView>(R.id.addressTextBox)
            val strAddress = findViewById<TextView>(R.id.addressTextBox).text.toString().trim()
            val phone = findViewById<TextView>(R.id.phoneTextBox)
            val strPhone = findViewById<TextView>(R.id.phoneTextBox).text.toString().trim()
            val timeDelivery = findViewById<TextView>(R.id.timeTv).text.toString()
            var hasError = false

            if (strName.isEmpty()) {
                hasError = true
                name.background.setTint(getResources().getColor(R.color.red))
                Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show()
            }
            if (strAddress.isEmpty()) {
                hasError = true
                name.background.setTint(getResources().getColor(R.color.red))
                Toast.makeText(this, "Address cannot be empty", Toast.LENGTH_SHORT).show()
            }
            if (strPhone.isEmpty() || !TextUtils.isDigitsOnly(strPhone)) {
                hasError = true
                name.background.setTint(getResources().getColor(R.color.red))
                Toast.makeText(this, "Phone must be digits and not empty", Toast.LENGTH_SHORT).show()
            }

           // if (name.getText().toString().trim().isEmpty()) {
               // name.error("Name cannot be empty")
           // }
            if (strPhone.isEmpty()) {
                //phone.setError("Phone cannot be empty")
            }

            if (!hasError){
                var intent = Intent(this, SecondActivity::class.java).apply {
                    putExtra("referName", strName)
                    putExtra("referAddress", strAddress)
                    putExtra("referPhone", strPhone)
                    putExtra("referSelectedBurger", selectedBurger)
                    putExtra("referTimeDelivery", timeDelivery)
                }

                val sharedPref = this?.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                //val sharedPref = getPreferences(Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("saved_name", strName)
                    putString("saved_address", strAddress)
                    putString("saved_phone", strPhone)
                    apply()
                }
                startActivity(intent)
            }
        }
    }
}

