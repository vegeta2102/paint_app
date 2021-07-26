package jp.co.paint.app

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.co.paint.app.databinding.ProgressFragmentBinding

/**
 * Created by vegeta on 2021/07/26.
 */
@AndroidEntryPoint
class ProgressFragment : Fragment(R.layout.progress_fragment) {

    private val progressViewModel: ProgressViewModel by viewModels()
    private val navController: NavController by lazy {
        findNavController()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ProgressFragmentBinding.bind(view).apply {
            this.viewModel = progressViewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        progressViewModel.init()
        observeViewModel()
    }

    private fun observeViewModel() {
        with(progressViewModel) {
            state.observe(viewLifecycleOwner) {
                when (it) {
                    ProgressViewModel.State.LOADING -> {
                        // Do nothing
                    }
                    ProgressViewModel.State.DONE -> {
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}