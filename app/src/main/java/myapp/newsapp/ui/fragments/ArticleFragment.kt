package myapp.newsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_articles.*
import myapp.newsapp.R
import myapp.newsapp.db.ArticleDatabase
import myapp.newsapp.repository.MainRepository
import myapp.newsapp.viewmodels.MainViewModel
import myapp.newsapp.viewmodels.MainViewModelProviderFactory

class ArticleFragment : Fragment(R.layout.fragment_articles) {

    lateinit var viewModel: MainViewModel
    val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsRepository = MainRepository(ArticleDatabase(requireContext()))
        val viewModelProviderFactory = MainViewModelProviderFactory(requireActivity().application, newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(MainViewModel::class.java)

        val article = args.article
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url.toString())
        }

        fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
        }
    }
}