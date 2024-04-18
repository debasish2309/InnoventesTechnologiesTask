package com.example.innoventestechnologiestask

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.innoventestechnologiestask.databinding.ActivityMainBinding
import com.example.innoventestechnologiestask.util.afterTextChanged
import com.example.innoventestechnologiestask.util.drawable
import com.example.innoventestechnologiestask.util.isValidPan
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val validationViewmodel: ValidationViewModel by viewModels()
    private var panValid = false
    private var birthValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnNext.isEnabled = false

        // checkButton()
        panValidation()
        birthDateValidation()
        onClickListeners()

        validationViewmodel.validPan.observe(this) {
            if (it) {
                binding.etPanNumber.drawable(R.drawable.rounded_corner_blue)
                panValid = it
            } else {
                binding.etPanNumber.drawable(R.drawable.rounded_corner_red)
                panValid = it
            }
            checkValidation(panValid, birthValid)
        }
        validationViewmodel.validBirthDate.observe(this) {
            if (it) {
                binding.etYear.drawable(R.drawable.rounded_corner_purple)
                birthValid = it
            } else {
                binding.etYear.drawable(R.drawable.rounded_corner_red)
                birthValid = it
            }
            checkValidation(panValid, birthValid)
        }
    }

    private fun checkValidation(panValid: Boolean, birthValid: Boolean) {
        if(panValid && birthValid) {
            binding.btnNext.isEnabled = true
        } else {
            binding.btnNext.isEnabled = false
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun panValidation() {
        binding.apply {
            etPanNumber.addTextChangedListener(etPanNumber.afterTextChanged {
                if (it.length.toString() == "10") {
                    validationViewmodel._panNumber.postValue(it)
                    validationViewmodel.isValidPin()
                } else {
                    etPanNumber.drawable(R.drawable.rounded_corner_grey)
                }
            })
        }
    }

    private fun birthDateValidation() {
        binding.apply {
            etYear.addTextChangedListener(etYear.afterTextChanged {
                if (it.length.toString() == "4") {
                    validationViewmodel.isValidBirthday("${etYear.text.toString()}-${etMonth.text.toString()}-${etDate.text.toString()}")
                }
            })
        }
    }

    private fun onClickListeners() {
        binding.btnNext.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                updateDetails()
            }
        }

        binding.tvDontHavePin.setOnClickListener {
            finish()
        }
    }

    private suspend fun updateDetails() {
        withContext(Dispatchers.Main) {
            Toast.makeText(applicationContext, "Details submitted successfully", Toast.LENGTH_SHORT)
                .show()
            delay(500)
            finish()
        }
    }
}