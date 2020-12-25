package com.example.android_project.restaurant

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.android_project.R
import com.example.android_project.classes.Dish
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_restaurant_add_menu.*
import kotlinx.android.synthetic.main.activity_restaurant_menu.*

class restaurant_addMenu : AppCompatActivity() {

    var imageUrl: String = ""
    var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_add_menu)

        restaurant_add_menu_return.setOnClickListener {
            finish()
        }

        addDishPicture.setOnClickListener {
            if (nameOfDish.text.toString().isEmpty()) {
                nameOfDish.setError("Fill Name first")
            } else {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(
                    Intent.createChooser(
                        intent,
                        "Select Picture"
                    ), 1
                )
            }

        }

        addDish.setOnClickListener {
            val name = nameOfDish.text.toString()
            val price = priceOfDish.text.toString()
            if (price.isEmpty()) {
                priceOfDish.setError("Price is empty")
            }
            else if (name.isEmpty()) {
                nameOfDish.setError("Name is empty")
            } else {
                val dish = Dish(name, price, imageUrl)

                var database = FirebaseDatabase.getInstance()

                var auth: FirebaseAuth
                auth = Firebase.auth
                var ref = database.getReference("RestUsers")
                var userId: String = auth.uid.toString()
                val newRef = ref.child(userId)
                newRef.addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val restName = snapshot.value.toString()

                        var ref = database.getReference("Restaurants/" + restName)
                        var ref1 = ref.child(name)
                        var ref2 = ref1.child("price")
                        ref2.setValue(price)
                        var ref3 = ref1.child("image")
                        ref3.setValue(imageUrl)
                        var ref4 = ref1.child("name")
                        ref4.setValue(name)
                        applicationContext.toast("Dish added to database")
                        nameOfDish.setText("", TextView.BufferType.EDITABLE)
                        priceOfDish.setText("", TextView.BufferType.EDITABLE)
                        finish()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })

            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                val selectedImageUri: Uri? = data?.data
                var imageUri: Uri = selectedImageUri!!
                val ref = FirebaseStorage.getInstance().getReference("FoodPictures")
                var name = nameOfDish.text.toString() + ".jpg"
                val newRef = ref.child(name)
                newRef.putFile(imageUri).addOnCompleteListener(object: OnCompleteListener<UploadTask.TaskSnapshot> {
                    override fun onComplete(task: Task<UploadTask.TaskSnapshot>) {
                        if (task.isSuccessful) {
                            val downloadURL = newRef.downloadUrl.addOnCompleteListener { link ->
                                if (link.isSuccessful) {
                                    val url = link.result.toString()
                                    Picasso.get().load(url).into(dishImage)
                                    imageUrl = url
                                }
                            }


                        } else {
                            applicationContext.toast("Image not uploaded")
                        }
                    }
                })
            }
        }
    }

    fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
