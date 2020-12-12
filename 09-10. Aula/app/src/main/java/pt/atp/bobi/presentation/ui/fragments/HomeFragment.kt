package pt.atp.bobi.presentation.ui.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import pt.atp.bobi.DEFAULT_IMAGE
import pt.atp.bobi.EXTRA_USERNAME
import pt.atp.bobi.R
import pt.atp.bobi.presentation.ui.CameraActivity
import java.io.File

private const val TAG = "HomeFragment"
private const val REQUEST_IMAGE_CAPTURE = 100
private const val REQUEST_READ_STORAGE = 500

class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.tv_hello).text = getString(
            R.string.welcome, requireActivity().intent!!.getStringExtra(EXTRA_USERNAME))

        view.findViewById<Button>(R.id.open_camera).setOnClickListener {
            openCamera()
        }

        view.findViewById<Button>(R.id.show_dialog).setOnClickListener {
            showAppDialog()
        }

        view.findViewById<Button>(R.id.show_snackbar).setOnClickListener {
            showAppSnackbar()
        }

        view.findViewById<Button>(R.id.startTimer).setOnClickListener {
            startTimer()
        }

        Glide.with(this)
            .load(DEFAULT_IMAGE)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .fitCenter()
            .into(view.findViewById(R.id.imageView))

        val tvStartTimer = view.findViewById<TextView>(R.id.tv_counter)

        viewModel.timerLiveDate.observe(viewLifecycleOwner) { count ->
            tvStartTimer.text = count.toString()

            if (count == 0L)
                loadImage()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if(requestCode == REQUEST_READ_STORAGE){
            if(permissions[0] == Manifest.permission.READ_EXTERNAL_STORAGE &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startTimer()
            }
        } else {

            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == AppCompatActivity.RESULT_OK) {
            val imageUri = data?.extras?.get("data") as Uri
            requireView().findViewById<ImageView>(R.id.imageView).setImageURI(imageUri)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun openCamera() {
        startActivityForResult(Intent(context, CameraActivity::class.java), REQUEST_IMAGE_CAPTURE)
    }

    /**
     * Calling this method will show a dialog.
     */
    private fun showAppDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.dialog_title)
        builder.setMessage(R.string.dialog_message)
        builder.apply {
            setPositiveButton(R.string.dialog_action_ok) { _, _ ->
                Toast.makeText(
                    requireContext(),
                    R.string.dialog_action_ok_selected,
                    Toast.LENGTH_SHORT
                ).show()
            }
            setNegativeButton(R.string.dialog_action_cancel) { _, _ ->
                Log.d(TAG, "Dialog cancelled")
            }
        }
        builder.create().show()
    }

    /**
     * Calling this method will show a snackbar.
     */
    private fun showAppSnackbar() {
        Snackbar.make(
            requireView(),
            R.string.snackbar_message,
            Snackbar.LENGTH_LONG
        )
            .setAction(R.string.snackbar_action_thanks) {
                Toast.makeText(
                    requireContext(),
                    R.string.snackbar_action_thanks_selected,
                    Toast.LENGTH_SHORT
                ).show()
            }
            .show()
    }

    private fun startTimer() {

        if(!checkPermissionAndRequest()){
            return
        }

        viewModel.starTimer(5000)
    }

    private fun checkPermissionAndRequest(): Boolean{

        if(ContextCompat
                .checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){

            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_READ_STORAGE)

            return false
        }

        return true
    }

    private fun loadImage(){

        val file = File("/storage/emulated/0/Download/fifi.jpg")

        val uri = Uri.fromFile(file)

        val imageView = requireView().findViewById<ImageView>(R.id.imageView)
        imageView.setImageURI(uri)
    }
}