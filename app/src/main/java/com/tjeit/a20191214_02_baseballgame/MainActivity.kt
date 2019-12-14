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
            chatDataList.add(ChatData("user: "+userInputNumEdt.text.toString(), "user"))
            chatAdapter?.notifyDataSetChanged()

            calculateStrikeAndBalls()
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

    fun calculateStrikeAndBalls() {
        val userInputValue = userInputNumEdt.text.toString().toInt()
//        100의 자리 / 10의 자리 / 1의 자리를 따다 배열에 저장
        val userInputNumArr = ArrayList<Int>()
//        100의 자리 : 567 / 100 = 5
//        10의 자리 : 567 / 10 = 56 % 10 => 567/10 % 10
//        1의 자리 : 567 % 10
        userInputNumArr.add(userInputValue/100)
        userInputNumArr.add(userInputValue/10 % 10)
        userInputNumArr.add(userInputValue%10)
        var strikeCount = 0
        var ballCount = 0

        for (i in 0..2) {
            for (j in 0..2) {
//                입력한 값과 컴퓨터가 낸 문제의 자리수 비교.
                if (computerNumArray.get(j) == userInputNumArr.get(i)) {
                    if (i == j) {
                        strikeCount++
                    } else {
                        ballCount++
                    }
                }
            }
        }
        chatDataList.add(ChatData("computer: ${strikeCount}S ${ballCount}B입니다.","computer"))
        chatAdapter?.notifyDataSetChanged()
    }

}
