package fr.isen.cerrone.adriendroidburger

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        //var obtainedName = intent.getStringExtra("referName")
        var obtainedAddress = intent.getStringExtra("referAddress")
        var obtainedPhone = intent.getStringExtra("referPhone")
        var obtainedSelectedBurger = intent.getStringExtra("referSelectedBurger")
        var obtainedTimeDelivery = intent.getStringExtra("referTimeDelivery")

        // retrieve from shared pref
        //val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val sharedPref = this?.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val obtainedName = sharedPref.getString("saved_name", "default_name")

        val welcomeTextView = findViewById<TextView>(R.id.welcomeTextView)
        welcomeTextView.text = "Welcome " + obtainedName + "\nPhone: " + obtainedPhone +
                "\nAddress: " + obtainedAddress + "\nA big: " + obtainedSelectedBurger + "\nAt " + obtainedTimeDelivery


        val emailIntent = Intent(Intent.ACTION_SEND)

        findViewById<Button>(R.id.mySendMail).setOnClickListener {
            emailIntent.data = Uri.parse("mailto:");
            emailIntent.type = "text/plain";
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("marc.mollinari@gmail.com"));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Confirmation commande");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Votre commande a bien été enregistrée");
            try {
                startActivity(Intent.createChooser(emailIntent, "Choose Email Client..."))
            } catch (e: Exception) {
            }
        }
    }
}