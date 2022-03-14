package myapp.newsapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import myapp.newsapp.R
import myapp.newsapp.db.ArticleDatabase
import myapp.newsapp.repository.MainRepository
import myapp.newsapp.viewmodels.MainViewModel
import myapp.newsapp.viewmodels.MainViewModelProviderFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private val TAG = "LoginActivity.kt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val newsRepository = MainRepository(ArticleDatabase(this))
        val viewModelProviderFactory = MainViewModelProviderFactory(application, newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(MainViewModel::class.java)
        btnLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        progress_circular_login.visibility = View.VISIBLE
        btnLogin.visibility = View.GONE
        viewModel.login(this, inputEmail.text.toString())
            .observe(this, {
                Log.d(TAG, "authResult:: name: " + it.user?.displayName)
                Intent(this, MainActivity::class.java).apply {
                    startActivity(this)
                }
            })
    }

    override fun onStart() {
        super.onStart()
        FirebaseApp.initializeApp(this)
        if (FirebaseAuth.getInstance().currentUser != null) {
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}