package jp.co.paint.startup

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import jp.co.paint.startup.databinding.FragmentStartupBinding
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
@AndroidEntryPoint
class StartupFragment : Fragment(R.layout.fragment_startup) {

    private val viewModel: StartupViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentStartupBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        initViewModel()
    }

    override fun onStart() {
        super.onStart()
        requestPermissionWithPermissionCheck()
    }

    @NeedsPermission(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    fun requestPermission() {
        // パーミッションが許可されていれば起動処理に遷移
        // initViewModel()
    }

    @OnPermissionDenied(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    fun onPermissionDenied() {
        // 拒否された場合は再度パーミッション要求する
        // DeviceOwnerがかかっていると権限ダイアログが表示されないため実質開発向け
        requestPermissionWithPermissionCheck()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    private fun initViewModel() {
        viewModel.initialize()
    }
}