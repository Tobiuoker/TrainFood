package com.example.android_project

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.android_project.deliverer.deliverer_allOrders
import com.example.android_project.passenger.restaurants_main
import com.example.android_project.restaurant.restaurant_menu
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*


data class Test(
    var name: String = "",
    var price: String = ""
)

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var databse = FirebaseDatabase.getInstance()
        var ref = databse.getReference("Restaurants")
        var ref2 = databse.getReference("Restaurants/Paradise")
        var ref3 = databse.getReference("Restaurants/Paradise/kebab")

        val availableSalads: List<Test> = mutableListOf(
            Test("Gherkin", "12"),
            Test("Lettuce", "13"),
            Test("Tomato", "14"),
            Test("Zucchini", "15")
        )

        val rtl = AnimationUtils.loadAnimation(this, R.anim.rtl)
        val ltr = AnimationUtils.loadAnimation(this, R.anim.ltr)
        val btu = AnimationUtils.loadAnimation(this, R.anim.btu)
        val scale = AnimationUtils.loadAnimation(this, R.anim.scale)

        title1.startAnimation(ltr)
        title2.startAnimation(ltr)
        loginLabel.startAnimation(rtl)
        loginText.startAnimation(rtl)
        passwordLabel.startAnimation(ltr)
        passwordText.startAnimation(ltr)
        login.startAnimation(btu)
        signUp.startAnimation(btu)
        logo.startAnimation(scale)


        signUp.setOnClickListener {
            startActivity(Intent(this, second_window::class.java))
        }

        login.setOnClickListener {


            var email = loginText.text.toString()
            var password = passwordText.text.toString()
            if (email.isEmpty()) {
                loginText.setError("Email can not be empty")
            } else if (password.isEmpty()) {
                passwordText.setError("Password can not be empty")
            } else {
                var auth: FirebaseAuth
                auth = FirebaseAuth.getInstance()
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, OnCompleteListener { task ->
                        if (task.isSuccessful) {
                            var userId: String = auth.uid.toString()
                            var ref = databse.getReference("Users")
                            var newRef = ref.child(userId)
                            newRef.addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.value == "passanger") {
                                        goToPassengerPage()
                                    } else if (snapshot.value == "deliverer") {
                                        goToDelivererPage()
                                    } else if (snapshot.value == "restaurant") {
                                        goToRestaurantPage()
                                    }
                                    var basketRef = databse.getReference("Basket/")
                                    basketRef.removeValue()
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }

                            })


                        } else {
                            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
                        }
                    })
            }

        }



    }

    fun goToPassengerPage() {
        Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show()
        startActivity(Intent(this, restaurants_main::class.java))
    }
    fun goToDelivererPage() {
        Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show()
        startActivity(Intent(this, deliverer_allOrders::class.java))
    }
    fun goToRestaurantPage() {
        Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show()
        startActivity(Intent(this, restaurant_menu::class.java))
    }

}
