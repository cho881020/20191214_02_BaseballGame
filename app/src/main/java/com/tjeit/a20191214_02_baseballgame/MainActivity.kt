package com.tjeit.a20191214_02_baseballgame

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
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
            if (userInputNumEdt.text.length != 3) {
                Toast.makeText(mContext,"잘못된 입력입니다. 세자리 숫자를 입력해주세요.",Toast.LENGTH_SHORT).show()
            } else {
                chatDataList.add(ChatData("user: "+userInputNumEdt.text.toString(), "user"))
                chatAdapter?.notifyDataSetChanged()
                Handler().postDelayed({
                    calculateStrikeAndBalls()
                }, 500)
            }
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
        chatListView.smoothScrollToPosition(chatDataList.size-1)   // 10칸 : 마지막 칸 9번

        if (strikeCount == 3) {
            chatDataList.add(ChatData("computer: 축하합니다! 정답입니다.", "computer"))
            chatListView.smoothScrollToPosition(chatDataList.size-1)   // 10칸 : 마지막 칸 9번
        }
    }

}
