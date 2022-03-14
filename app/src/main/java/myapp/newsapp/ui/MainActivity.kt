package myapp.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import myapp.newsapp.R
import myapp.newsapp.db.ArticleDatabase
import myapp.newsapp.repository.MainRepository
import myapp.newsapp.viewmodels.MainViewModel
import myapp.newsapp.viewmodels.MainViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newsRepository = MainRepository(ArticleDatabase(this))
        val viewModelProviderFactory = MainViewModelProviderFactory(application, newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(MainViewModel::class.java)
        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())
    }
}