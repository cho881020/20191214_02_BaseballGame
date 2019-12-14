package com.tjeit.a20191214_02_baseballgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : BaseActivity() {

//    문제로 나온 숫자를 담기 위한 배열
    val computerNumArray = ArrayList<Int>()

//    수정의 전용 브런치입니다

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

    }

}
