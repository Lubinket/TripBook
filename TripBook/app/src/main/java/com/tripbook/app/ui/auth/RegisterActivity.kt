package com.tripbook.app.ui.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.AnimatorListenerAdapter
import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputEditText
import com.tripbook.app.MainActivity
import com.tripbook.app.R

class RegisterActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001

    private lateinit var etName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnRegister: Button
    private lateinit var btnGoogleRegister: Button
    private lateinit var tvGoToLogin: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var bgImage1: ImageView
    private lateinit var bgImage2: ImageView

    private val backgrounds = listOf(
        R.drawable.auth_bg1,
        R.drawable.auth_bg2,
        R.drawable.auth_bg3,
        R.drawable.auth_bg4
    )
    private var currentBgIndex = 0
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnRegister = findViewById(R.id.btnRegister)
        btnGoogleRegister = findViewById(R.id.btnGoogleRegister)
        tvGoToLogin = findViewById(R.id.tvGoToLogin)
        progressBar = findViewById(R.id.progressBar)
        bgImage1 = findViewById(R.id.bgImage1)
        bgImage2 = findViewById(R.id.bgImage2)

        setupGoogleSignIn()
        setupObservers()
        startBackgroundSlideshow()

        btnRegister.setOnClickListener {
            viewModel.register(
                etName.text.toString(),
                etEmail.text.toString(),
                etPassword.text.toString()
            )
        }

        btnGoogleRegister.setOnClickListener {
            startActivityForResult(googleSignInClient.signInIntent, RC_SIGN_IN)
        }

        tvGoToLogin.setOnClickListener { finish() }
    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun setupObservers() {
        viewModel.authState.observe(this) { state ->
            when (state) {
                is LoginViewModel.AuthState.Loading ->
                    progressBar.visibility = View.VISIBLE
                is LoginViewModel.AuthState.Success -> {
                    progressBar.visibility = View.GONE
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                is LoginViewModel.AuthState.Error -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun startBackgroundSlideshow() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                val nextIndex = (currentBgIndex + 1) % backgrounds.size
                bgImage2.setImageResource(backgrounds[nextIndex])

                val fadeIn = ObjectAnimator.ofFloat(bgImage2, "alpha", 0f, 1f)
                fadeIn.duration = 1500

                val fadeOut = ObjectAnimator.ofFloat(bgImage1, "alpha", 1f, 0f)
                fadeOut.duration = 1500

                val set = AnimatorSet()
                set.playTogether(fadeIn, fadeOut)
                set.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        bgImage1.setImageResource(backgrounds[nextIndex])
                        bgImage1.alpha = 1f
                        bgImage2.alpha = 0f
                        currentBgIndex = nextIndex
                    }
                })
                set.start()

                handler.postDelayed(this, 4000)
            }
        }, 4000)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                viewModel.signInWithGoogle(account)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google Sign-In failed: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}