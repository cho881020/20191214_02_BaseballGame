package com.tjeit.a20191214_02_baseballgame

import android.os.Bundle
import android.util.Log
import com.tjeit.a20191214_02_baseballgame.datas.ChatData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

//    문제로 나온 숫자를 담기 위한 배열
    val computerNumArray = ArrayList<Int>()

//    김영호의 전용 브런치
    val chatDataList = ArrayList<ChatData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        inputBtn.setOnClickListener {
            chatDataList.add(ChatData(userInputNumEdt.text.toString(), "user"))
        }
    }

    override fun setValues() {
        makeComputerNurmbers()
    }

    fun makeComputerNurmbers() {
        for ( i in 0..2) {
            //Log.d("for문","${i}")

            while(true) {
//                val randomNum = Math.random() // 1 <= 랜덤값 < 1
                val randomNum = (Math.random() * 9 + 1).toInt() // 1 <= 랜덤값 * 9 + 1 < 10

                var isDuplicateOk = true

                for(num in computerNumArray) {
                    if(num == randomNum) {
                        isDuplicateOk = false
                        break
                    }
                }

                if(isDuplicateOk) {
                    computerNumArray.add(randomNum)
                    break
                }

            }
        }

        for(num in computerNumArray) {
            Log.d("출제된 숫자", "${num}")
        }
    }

}
