package com.example.android_project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import kotlinx.android.synthetic.main.activity_second_window.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class second_window : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_window)
        var auth: FirebaseAuth
        auth = Firebase.auth
        println("tuta")
        println(auth.currentUser.toString())
        val rtl = AnimationUtils.loadAnimation(this, R.anim.rtl)
        val ltr = AnimationUtils.loadAnimation(this, R.anim.ltr)
        val btu = AnimationUtils.loadAnimation(this, R.anim.btu)

        signUpLabel.startAnimation(rtl)
        secondLoginLabel.startAnimation(ltr)
        secondLoginText.startAnimation(ltr)
        secondPasswordLabel.startAnimation(rtl)
        secondPasswordText.startAnimation(rtl)
        emailLabel.startAnimation(ltr)
        emailText.startAnimation(ltr)
        phoneLabel.startAnimation(rtl)
        phoneText.startAnimation(rtl)
        signUpButton.startAnimation(btu)

        arrowBack.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        signUpButton.setOnClickListener {
            if (validateLogin() && validatePassword() && validateEmail() && validatePhone()) {
                var database = FirebaseDatabase.getInstance()
                val email = emailText.text.toString()
                val password = secondPasswordText.text.toString()
                var auth: FirebaseAuth
                auth = Firebase.auth
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            var radioButton = ""
                            if (passangerRadioButton.isChecked()) {
                                radioButton = "passanger"
                            } else if (delivererRadioButton.isChecked()) {
                                radioButton = "deliverer"
                            } else if (restaurantRadioButton.isChecked()) {
                                radioButton = "restaurant"
                            }


                            var ref = database.getReference("Users")
                            var userId: String = auth.uid.toString()
                            val newRef = ref.child(userId)
                            newRef.setValue(radioButton)

                            if (radioButton == "restaurant") {
                                var ref = database.getReference("RestUsers")
                                var userId: String = auth.uid.toString()
                                val newRef = ref.child(userId)
                                newRef.setValue(secondLoginText.text.toString())
                            }

                            

                            FirebaseAuth.getInstance().signOut()
                            startActivity(Intent(this, MainActivity::class.java))
                        } else {
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        passangerRadioButton.setOnClickListener {
            delivererRadioButton.isChecked = false
            restaurantRadioButton.isChecked = false
        }
        delivererRadioButton.setOnClickListener {
            passangerRadioButton.isChecked = false
            restaurantRadioButton.isChecked = false
        }
        restaurantRadioButton.setOnClickListener {
            delivererRadioButton.isChecked = false
            passangerRadioButton.isChecked = false
        }
    }

    private fun validateLogin(): Boolean {
        val login = secondLoginText.text.toString()
        if (login.isEmpty()) {
            secondLoginText.setError("Field cannot be empty");
            return false
        } else if (login.length >= 15) {
            secondLoginText.setError("Username too long");
            return false
        } else if (login.contains(" ")) {
            secondLoginText.setError("White Spaces are not allowed");
            return false
        }
        return true
    }

    private fun validateEmail(): Boolean {
        val email = emailText.text.toString()
        var pattern: Pattern
        var matcher: Matcher
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        pattern = Pattern.compile(emailPattern)
        matcher = pattern.matcher(email)
        if (email.isEmpty()) {
            emailText.setError("Email cannot be empty")
            return false
        } else if (!matcher.matches()) {
            emailText.setError("Invalid Email address")
            return false
        }
        return true
    }

    private fun validatePassword(): Boolean {
        var pattern: Pattern
        var matcher: Matcher
        val password = secondPasswordText.text.toString()
        val regex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^_&*-]).{8,}\$"
        pattern = Pattern.compile(regex)
        matcher = pattern.matcher(password)
        if (password.isEmpty()) {
            secondPasswordText.setError("Password cannot be empty")
            return false
        } else if (!matcher.matches()) {
            secondPasswordText.error =
                                "A digit must occur at least once." + "\n"+
                                "A lower case alphabet must occur at least once."+ "\n"+
                                "An upper case alphabet that must occur at least once."+ "\n"+
                                "A special character that must occur at least once." + "\n"+
                                "White spaces are not allowed."+ "\n"+
                                "At least 6 characters"
            return false
        }
        return true
    }

    private fun validatePhone(): Boolean {
        val phone = phoneText.text.toString()
        val regex = "\\d{10}"
        var pattern: Pattern
        var matcher: Matcher
        pattern = Pattern.compile(regex)
        matcher = pattern.matcher(phone)
        if (phone.isEmpty()) {
            phoneText.setError("Phone cannot be empty")
            return false
        } else if (!matcher.matches()) {
            phoneText.setError("Must be digits")
            return false
        }
        return true
    }
}
