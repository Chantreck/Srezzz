package com.chantreck.srez

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chantreck.srez.databinding.ActivitySignInBinding
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity(R.layout.activity_sign_in) {
    private val binding by lazy { ActivitySignInBinding.inflate(layoutInflater) }
    private val api = Network.retrofit.create(RequestInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val login = binding.loginET.text.toString()
            val password = binding.passET.text.toString()

            if (login.isNullOrEmpty() || password.isNullOrEmpty()) {
                showDialog("Login and password can't be empty")
                return@setOnClickListener
            }

            val body = LoginBody(login, password)
            api.login(body).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            SharedPrefs.saveAuthToken(it.data.token)
                            startActivity(Intent(this@SignInActivity, TasksActivity::class.java))
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        errorBody?.let {
                            val message = JSONObject(it).getString("message")
                            showDialog(message)
                        }
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    showDialog("Something went wrong")
                }
            })
        }
    }

    private fun showDialog(text: String) {
        AlertDialog.Builder(this).apply {
            setTitle(text)
            setPositiveButton("OK", null)
        }.show()
    }
}