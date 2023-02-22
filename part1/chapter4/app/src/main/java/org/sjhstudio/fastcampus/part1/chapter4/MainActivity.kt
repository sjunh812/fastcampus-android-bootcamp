package org.sjhstudio.fastcampus.part1.chapter4

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import org.sjhstudio.fastcampus.part1.chapter4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGoEditActivity.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }

        binding.btnDelete.setOnClickListener {
            deleteDataAndUiUpdate()
        }

        binding.layerContact.setOnClickListener {
            if (binding.tvContact.text.isNullOrEmpty().not()) {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    val tel = binding.tvContact.text.toString()
                        .replace("-", "")
                    data = Uri.parse("tel:$tel")
                }
                startActivity(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        getDataAndUiUpdate()
    }

    private fun getDataAndUiUpdate() {
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE)) {
            binding.tvName.text = getString(NAME, "")
            binding.tvBirth.text = getString(BIRTH, "")
            binding.tvBloodType.text = getString(BLOOD_TYPE, "")
            binding.tvContact.text = getString(EMERGENCY_CONTACT, "")

            val caution = getString(CAUTION, "")
            binding.tvCautionTitle.isVisible = caution.isNullOrEmpty().not()
            binding.tvCaution.isVisible = caution.isNullOrEmpty().not()
            binding.tvCaution.text = caution
        }
    }

    private fun deleteDataAndUiUpdate() {
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE).edit()) {
            clear()
            apply()
            getDataAndUiUpdate()
        }

        Toast.makeText(this, "초기화를 완료했습니다.", Toast.LENGTH_SHORT).show()
    }
}