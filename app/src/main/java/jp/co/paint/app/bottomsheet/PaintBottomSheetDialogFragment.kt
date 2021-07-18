package jp.co.paint.app.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jp.co.paint.app.R
import jp.co.paint.app.databinding.PaintBottomSheetLayoutBinding
import kotlinx.android.synthetic.main.paint_bottom_sheet_layout.*

/**
 * Created by vegeta
 */
class PaintBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private val viewModel: PaintBottomSheetViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return DataBindingUtil.inflate<PaintBottomSheetLayoutBinding>(
            inflater, R.layout.paint_bottom_sheet_layout, container, false
        ).apply {
            this.lifecycleOwner = viewLifecycleOwner
            this.viewModel = this@PaintBottomSheetDialogFragment.viewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.doOnPreDraw {
            BottomSheetBehavior.from(bottomSheet).addBottomSheetCallback(bottomSheetCallback)
        }
    }

    private val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            Log.d("BottomSheet", slideOffset.toString())
        }

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            Log.d("BottomSheet", newState.toString())
        }
    }
}