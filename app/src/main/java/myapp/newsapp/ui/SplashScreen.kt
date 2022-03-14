package myapp.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import myapp.newsapp.R
import android.content.Intent
import android.os.Handler

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed(Runnable { /* Create an Intent that will start the Menu-Activity. */
            val mainIntent = Intent(this@SplashScreen, LoginActivity::class.java)
            this@SplashScreen.startActivity(mainIntent)
            this@SplashScreen.finish()
        }, 2500)
    }
}