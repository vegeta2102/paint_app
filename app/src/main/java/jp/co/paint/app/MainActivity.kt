package jp.co.paint.app

import android.app.Activity
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import jp.co.paint.DisplayInfoRepository
import jp.co.paint.app.databinding.ActivityMainBinding
import jp.co.paint.core.extentions.toPx
import jp.co.paint.model.ScreenSize
import jp.co.paint.startup.StartupViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

val Activity.displayMetrics: DisplayMetrics
    get() {
        // display metrics is a structure describing general information
        // about a display, such as its size, density, and font scaling
        val displayMetrics = DisplayMetrics()

        if (Build.VERSION.SDK_INT >= 30) {
            display?.apply {
                getRealMetrics(displayMetrics)
            }
        } else {
            // getMetrics() method was deprecated in api level 30
            windowManager.defaultDisplay.getMetrics(displayMetrics)
        }

        return displayMetrics
    }


// extension property to get screen width and height in dp
val Activity.screenSizeInDp: Point
    get() {
        val point = Point()
        displayMetrics.apply {
            // screen width in dp
            point.x = (widthPixels / density).roundToInt()

            // screen height in dp
            point.y = (heightPixels / density).roundToInt()
        }

        return point
    }

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    companion object {
        val TAG = MainActivity::class.simpleName
    }

    private val viewModel: StartupViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var viewDataBinding: ActivityMainBinding

    @Inject
    lateinit var displayInfoRepository: DisplayInfoRepository

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

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        lifecycleScope.launch {
            displayInfoRepository.emit(ScreenSize(w = main_layout.width, h = main_layout.height))
        }
    }
}