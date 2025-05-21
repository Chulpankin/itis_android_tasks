package com.itis.bookclub.presentation.auth.signin

import android.content.Context
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.itis.bookclub.presentation.auth.signin.combosable.SignInScreen
import com.itis.bookclub.presentation.base.BaseFragment
import com.itis.bookclub.presentation.themes.BookClubAppTheme
import com.itis.bookclub.util.DaggerViewModelFactory
import com.itis.bookclub.util.appComponent
import javax.inject.Inject

class SignInFragment : BaseFragment() {

    @Inject
    lateinit var vmFactory: DaggerViewModelFactory

    private val viewModel: SignInViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        requireContext().appComponent.inject(fragment = this)
        super.onAttach(context)
    }

    override fun provideComposeView(): ComposeView =
        ComposeView(requireContext()).apply {
            setContent {
                BookClubAppTheme {
                    SignInScreen(viewModel = viewModel)
                }
            }
        }
}