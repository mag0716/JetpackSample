package com.github.mag0716.viewbindingsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mag0716.viewbindingsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // レイアウトファイルの ViewGroup に tools:viewBindingIgnore="true" を指定すると生成されなくなる
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        binding.bindableText.text = "${binding.bindableText.text}(Bind)"
    }
}
