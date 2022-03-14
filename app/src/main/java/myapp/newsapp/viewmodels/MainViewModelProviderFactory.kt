package myapp.newsapp.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import myapp.newsapp.repository.MainRepository

class MainViewModelProviderFactory(
    private val app: Application,
    private val repository: MainRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(app, repository) as T
    }
}