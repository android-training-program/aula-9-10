package pt.atp.bobi.presentation.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth
import pt.atp.bobi.ACTIVITY_REQUEST
import pt.atp.bobi.EXTRA_USERNAME
import pt.atp.bobi.R

private const val TAG = "LoginActivity2"
private const val REQUEST_SIGN_IN = 12345

class LoginActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        setup()
    }

    private fun setup() {
        findViewById<SignInButton>(R.id.sign_in).setOnClickListener {
            openPostActivityCustom.launch(REQUEST_SIGN_IN)
        }

        FirebaseAuth.getInstance().addAuthStateListener {
            Log.d(TAG, "AuthStateListener triggered. User: ${it.currentUser}")
            if (it.currentUser != null) {
                val username = it.currentUser?.displayName
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(EXTRA_USERNAME, username)
                startActivity(intent)
                finish()
            }
        }
    }

    private val openPostActivityCustom =
        registerForActivityResult(ActivityContract()) { result ->
            if (result != null) {
                Log.d(TAG, "User signed in")
            }
        }

    class ActivityContract : ActivityResultContract<Int, Boolean?>() {

        override fun createIntent(context: Context, input: Int): Intent {
            val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
            return AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build().apply {
                    putExtra(ACTIVITY_REQUEST, REQUEST_SIGN_IN)
            }
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
            val data = intent?.getStringExtra(ACTIVITY_REQUEST)
            return resultCode == Activity.RESULT_OK && data != null
        }
    }
}