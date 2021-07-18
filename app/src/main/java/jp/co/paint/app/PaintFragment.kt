package jp.co.paint.app

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.himanshurawat.imageworker.Extension
import com.himanshurawat.imageworker.ImageWorker
import dagger.hilt.android.AndroidEntryPoint
import jp.co.paint.app.databinding.FragmentPaintBinding


/**
 * Created by vegeta
 */
@AndroidEntryPoint
class PaintFragment : Fragment(R.layout.fragment_paint) {

    private val paintViewModel: PaintViewModel by viewModels()
    private var checkIndex = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentPaintBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = paintViewModel
            initView(this)
        }
    }

    private fun initView(binding: FragmentPaintBinding) {
        binding.undo.setOnClickListener {
            binding.drawingView.setErase(isErase = true)
        }
        binding.save.setOnClickListener {
            getBitmapFromView(binding.drawingView)?.let { bitmap ->
                saveFile(bitmap = bitmap)
            }
        }
        binding.fab.setOnClickListener {
            showAlertDialog(binding)
        }
        binding.importImage.setOnClickListener {
            val bitmap = ImageWorker.from(requireContext())
                .directory("paint")
                .setFileName("test")
                .withExtension(Extension.PNG)
                .load()
            bitmap?.let {
                binding.drawingView.loadFile(it)
            }
        }
    }

    private fun saveFile(bitmap: Bitmap) {
        ImageWorker.to(requireContext())
            .directory("paint")
            .setFileName("test")
            .withExtension(Extension.PNG)
            .save(bitmap, 85)
    }

    private fun getBitmapFromView(view: View): Bitmap? {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun showAlertDialog(binding: FragmentPaintBinding) {
        val items = arrayOf("黒", "青", "赤", "黄")
        val alertDialog = AlertDialog.Builder(requireContext()).apply {
            setTitle("色を選びなさい")
            setSingleChoiceItems(items, checkIndex) { dialogInterface, index ->
                when (index) {
                    0 -> {
                        Toast.makeText(requireContext(), "Tap black", Toast.LENGTH_SHORT).show()
                        binding.drawingView.setColor(R.color.black)
                        checkIndex = 0
                    }
                    1 -> {
                        Toast.makeText(requireContext(), "Tap blue", Toast.LENGTH_SHORT).show()
                        binding.drawingView.setColor(R.color.bg_blue)
                        checkIndex = 1
                    }
                    2 -> {
                        Toast.makeText(requireContext(), "Tap red", Toast.LENGTH_SHORT).show()
                        binding.drawingView.setColor(R.color.bg_red)
                        checkIndex = 2
                    }
                    3 -> {
                        Toast.makeText(requireContext(), "Tap yellow", Toast.LENGTH_SHORT).show()
                        binding.drawingView.setColor(R.color.bg_yellow)
                        checkIndex = 3
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