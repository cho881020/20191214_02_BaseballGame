package com.tjeit.a20191214_02_baseballgame

import android.os.Bundle
import android.util.Log
import com.tjeit.a20191214_02_baseballgame.adapters.ChatingAdapter
import com.tjeit.a20191214_02_baseballgame.datas.ChatData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

//    문제로 나온 숫자를 담기 위한 배열
    val computerNumArray = ArrayList<Int>()
    val chatDataList = ArrayList<ChatData>()
    var chatAdapter:ChatingAdapter? = null

//    차순혁의 전용 브런치!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        inputBtn.setOnClickListener {
            chatDataList.add(ChatData(userInputNumEdt.text.toString(), "user"))
            chatAdapter?.notifyDataSetChanged()
        }
    }

    override fun setValues() {
        makeComputerNumberes()

        chatAdapter = ChatingAdapter(mContext, R.layout.activity_main, chatDataList)
        chatListView.adapter = chatAdapter
    }

    fun makeComputerNumberes() {
        for (i in 0..2) {
            Log.d("log]for문","${i}")
            while (true) {
                val randomNum = (Math.random() * 9 + 1).toInt()   // 0 <= 랜덤값 * 9 + 1 < 9

                var isDuplicateOk = true

                for (num in computerNumArray) {
                    if (num == randomNum) {
                        isDuplicateOk = false
                        break
                    }
                }

                if (isDuplicateOk) {
                    computerNumArray.add(randomNum)
                    break
                }
            }
        }
        for (num in computerNumArray) {
            Log.d("log]출제된 숫자","${num}")
        }
    }

}
