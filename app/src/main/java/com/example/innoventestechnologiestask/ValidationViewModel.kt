package com.example.innoventestechnologiestask

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.innoventestechnologiestask.util.isValidPan
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ValidationViewModel @Inject constructor() : ViewModel() {

    var _panNumber = MutableLiveData<String>()
    val panNumber : LiveData<String>
        get() = _panNumber

    val _validPan = MutableLiveData<Boolean>()
    val validPan : LiveData<Boolean>
        get() = _validPan

    fun isValidPin() {
        if(panNumber != null) {
            if(panNumber.value.toString().isValidPan()) {
                _validPan.postValue(true)
            } else {
                _validPan.postValue(false)
            }
        }
    }

    var _validBirthDate = MutableLiveData<Boolean>()
    val validBirthDate : LiveData<Boolean>
        get() = _validBirthDate

    private val BIRTHDAY_FORMAT_PARSER: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    private fun parseDateString(date: String?): Calendar {
        val calendar: Calendar = Calendar.getInstance()
        BIRTHDAY_FORMAT_PARSER.isLenient = false
        try {
            calendar.time = BIRTHDAY_FORMAT_PARSER.parse(date)
        } catch (e: ParseException) {
        }
        return calendar
    }

    fun isValidBirthday(birthDate: String) {
        if(birthDate != null) {
            val calendar: Calendar = parseDateString(birthDate)
            val year: Int = calendar.get(Calendar.YEAR)
            val thisYear: Int = Calendar.getInstance().get(Calendar.YEAR)
            if (year >= 1900 && year < thisYear) {
                _validBirthDate.postValue(true)
            } else {
                _validBirthDate.postValue(false)
            }
        }

    }
}