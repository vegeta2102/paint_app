package jp.co.paint.app

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.himanshurawat.imageworker.Extension
import com.himanshurawat.imageworker.ImageWorker
import dagger.hilt.android.AndroidEntryPoint
import jp.co.paint.DisplayInfoRepository
import jp.co.paint.app.databinding.FragmentPaintBinding
import jp.co.paint.model.ScreenSize
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Exception


/**
 * Created by vegeta
 */
@AndroidEntryPoint
class PaintFragment : Fragment(R.layout.fragment_paint) {

    companion object {
        const val PATH = "paint"
        const val FILE_NAME = "paint_20210730"
    }

    private val paintViewModel: PaintViewModel by viewModels()

    @Inject
    lateinit var displayInfoRepository: DisplayInfoRepository
    private var colorSelectedHolder = 0
    private var thicknessSelectedHolder = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentPaintBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = paintViewModel
            BottomSheetBehavior.from(bottomSheet).apply {
                this.state = BottomSheetBehavior.STATE_COLLAPSED
                addBottomSheetCallback(bottomSheetCallback)
            }
            paintViewModel.bind(this.drawingView)
            observeViewModel(this)
        }
    }

    private val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            val bottomMargin = (40 + (bottomSheet.height - 40) * slideOffset).toInt()
            Log.d("TouchBottomMargin", "$bottomMargin")
            lifecycleScope.launch {
                displayInfoRepository.latestData?.let {
                    displayInfoRepository.emit(
                        ScreenSize(
                            w = it.w,
                            h = it.h,
                            offset = bottomMargin
                        )
                    )
                }
            }
        }

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            Log.d("BottomSheet", newState.toString())
        }
    }

    private fun observeViewModel(binding: FragmentPaintBinding) {
        with(paintViewModel) {
            requestChangeColor.observe(viewLifecycleOwner) {
                showChangeColorAlertDialog(binding)
            }

            requestChangeThickness.observe(viewLifecycleOwner) {
                showChangeThicknessAlertDialog(binding)
            }

            requestSaveImage.observe(viewLifecycleOwner) {
                getBitmapFromView(binding.drawingView)?.let {
                    saveBitmapToStorage(it)
                }
            }

            requestLoadImage.observe(viewLifecycleOwner) {
                loadBitmapFromStorage()?.let {
                    binding.drawingView.loadFile(it)
                }
            }
        }
    }

    private fun saveBitmapToStorage(bitmap: Bitmap) {
        try {
            ImageWorker.to(requireContext())
                .directory(PATH)
                .setFileName(FILE_NAME)
                .withExtension(Extension.PNG)
                .save(bitmap, 85)
            Toast.makeText(requireContext(), "保存されました", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e("Save Failure", "$e")
        }
    }

    private fun loadBitmapFromStorage(): Bitmap? {
        return ImageWorker.from(requireContext())
            .directory(PATH)
            .setFileName(FILE_NAME)
            .withExtension(Extension.PNG)
            .load()
    }

    private fun getBitmapFromView(view: View): Bitmap? {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun showChangeColorAlertDialog(binding: FragmentPaintBinding) {
        val items = arrayOf("黒", "青", "赤", "黄")
        val alertDialog = AlertDialog.Builder(requireContext()).apply {
            setTitle("色を選びなさい")
            setSingleChoiceItems(items, colorSelectedHolder) { dialogInterface, index ->
                when (index) {
                    0 -> {
                        binding.drawingView.setColor(R.color.black)
                        colorSelectedHolder = 0
                    }
                    1 -> {
                        binding.drawingView.setColor(R.color.bg_blue)
                        colorSelectedHolder = 1
                    }
                    2 -> {
                        binding.drawingView.setColor(R.color.bg_red)
                        colorSelectedHolder = 2
                    }
                    3 -> {
                        binding.drawingView.setColor(R.color.bg_yellow)
                        colorSelectedHolder = 3
                    }
                }
                dialogInterface.dismiss()
            }
        }
        alertDialog.create().apply {
            setCanceledOnTouchOutside(false)
        }.show()
    }

    private fun showChangeThicknessAlertDialog(binding: FragmentPaintBinding) {
        val items = arrayOf("小", "中", "大")
        val alertDialog = AlertDialog.Builder(requireContext()).apply {
            setTitle("ペンの太さを選びなさい")
            setSingleChoiceItems(items, thicknessSelectedHolder) { dialogInterface, index ->
                when (index) {
                    0 -> {
                        binding.drawingView.setBrushSize(
                            resources.getInteger(R.integer.small_size).toFloat()
                        )
                        thicknessSelectedHolder = 0
                    }
                    1 -> {
                        binding.drawingView.setBrushSize(
                            resources.getInteger(R.integer.medium_size).toFloat()
                        )
                        thicknessSelectedHolder = 1
                    }
                    2 -> {
                        binding.drawingView.setBrushSize(
                            resources.getInteger(R.integer.large_size).toFloat()
                        )
                        thicknessSelectedHolder = 2
                    }
                }
                dialogInterface.dismiss()
            }
        }
        alertDialog.create().apply {
            setCanceledOnTouchOutside(false)
        }.show()
    }
}