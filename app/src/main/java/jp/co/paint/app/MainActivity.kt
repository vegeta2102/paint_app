package jp.co.paint.app

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import jp.co.paint.app.databinding.ActivityMainBinding
import jp.co.paint.startup.StartupViewModel
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    companion object {
        val TAG = MainActivity::class.simpleName
    }

    private val viewModel: StartupViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var viewDataBinding: ActivityMainBinding

    private val navController: NavController by lazy {
        // https://stackoverflow.com/questions/58703451/fragmentcontainerview-as-navhostfragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        ).apply {
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.mainViewModel
        }
        with(viewModel) {
            initFinished.observe(this@MainActivity) {
                Log.d(TAG, "init event")
                navController.navigate(R.id.action_to_main)
            }
        }
        with(mainViewModel) {
            requestDialogMessage.observe(this@MainActivity) {
                AlertDialog.Builder(this@MainActivity)
                    .setMessage(it)
                    .create()
                    .show()
            }
        }
    }
}