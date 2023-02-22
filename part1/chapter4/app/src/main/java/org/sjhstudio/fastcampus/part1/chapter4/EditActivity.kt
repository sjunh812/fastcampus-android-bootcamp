package org.sjhstudio.fastcampus.part1.chapter4

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import org.sjhstudio.fastcampus.part1.chapter4.databinding.ActivityEditBinding
import java.util.*

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.spinnerBloodType.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.blood_types,
            android.R.layout.simple_spinner_item
        )

        binding.layerBirth.setOnClickListener {
            DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    binding.tvBirth.text = "$year-${month.inc()}-$dayOfMonth"
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.cbCaution.setOnCheckedChangeListener { _, isChecked ->
            binding.etCaution.isVisible = isChecked
        }

        binding.etCaution.isVisible = binding.cbCaution.isChecked

        binding.btnSave.setOnClickListener {
            saveData()
            finish()
        }
    }

    private fun saveData() {
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE).edit()) {
            putString(NAME, binding.etName.text.toString())
            putString(BIRTH, binding.tvBirth.text.toString())
            putString(BLOOD_TYPE, getBloodType())
            putString(EMERGENCY_CONTACT, binding.etContact.text.toString())
            putString(CAUTION, getCaution())
            apply()
        }

        Toast.makeText(this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun getBloodType(): String {
        val bloodSign = if (binding.radRhPlus.isChecked) "+" else "-"
        val blood = binding.spinnerBloodType.selectedItem.toString()
        return "$bloodSign$blood"
    }

    private fun getCaution(): String {
        return if (binding.cbCaution.isChecked) binding.etCaution.text.toString() else ""
    }

    companion object {

        val calendar: Calendar = Calendar.getInstance().apply { time = Date() }
    }
}