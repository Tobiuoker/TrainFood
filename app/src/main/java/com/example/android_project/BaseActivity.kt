//package com.example.android_project
//
//import android.R
//import android.content.Intent
//import android.os.Bundle
//import android.view.View
//import androidx.appcompat.app.AppCompatActivity
//import com.google.android.material.bottomnavigation.BottomNavigationView
//
//
//abstract class BaseActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
//    protected var navigationView: BottomNavigationView? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(this.getWindow().getDecorView().findViewById(android.R.id.content))
//        navigationView = findViewById<View>(R.id.navigation) as BottomNavigationView
//        navigationView!!.setOnNavigationItemSelectedListener(this)
//    }
//
//    override fun onStart() {
//        super.onStart()
//        updateNavigationBarState()
//    }
//
//    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
//    public override fun onPause() {
//        super.onPause()
//        overridePendingTransition(0, 0)
//    }
//
//    fun onNavigationItemSelected(item: MenuItem): Boolean {
//        navigationView!!.postDelayed({
//            val itemId: Int = item.getItemId()
//            if (itemId == R.id.action_home) {
//                startActivity(Intent(this, HomeActivity::class.java))
//            } else if (itemId == R.id.action_profile) {
//                startActivity(Intent(this, ProfielActivity::class.java))
//            } else if (itemId == R.id.action_notification) {
//                startActivity(Intent(this, NotificationsActivity::class.java))
//            } else if (itemId == R.id.action_more) {
//                startActivity(Intent(this, MoreActivity::class.java))
//            }
//            finish()
//        }, 300)
//        return true
//    }
//
//    private fun updateNavigationBarState() {
//        val actionId: Int = getNavigationMenuItemId()
//        selectBottomNavigationBarItem(actionId)
//    }
//
//    fun selectBottomNavigationBarItem(itemId: Int) {
//        val item: MenuItem = navigationView!!.menu.findItem(itemId)
//        item.setChecked(true)
//    }
//
//    // this is to return which layout(activity) needs to display when clicked on tabs.
//    abstract val layoutId: Int
//
//    //Which menu item selected and change the state of that menu item
//    abstract val bottomNavigationMenuItemId: Int
//}