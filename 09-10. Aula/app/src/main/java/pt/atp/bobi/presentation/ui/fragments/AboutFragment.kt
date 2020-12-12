package pt.atp.bobi.presentation.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import pt.atp.bobi.domain.AboutOptions
import pt.atp.bobi.presentation.ui.compose.AboutList

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        AboutList(abouts = AboutOptions.aboutOptions, onUserClick = { setting ->
                            if (setting.website == null) {
                                return@AboutList
                            }

                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(setting.website))
                            startActivity(browserIntent)
                        })
                    }

                }
            }
        }
    }
}