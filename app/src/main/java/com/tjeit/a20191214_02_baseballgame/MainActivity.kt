package com.tjeit.a20191214_02_baseballgame

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.tjeit.a20191214_02_baseballgame.adapters.ChattingAdapter
import com.tjeit.a20191214_02_baseballgame.datas.ChatData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

//    문제로 나온 숫자를 담기 위한 배열
    val computerNumArray = ArrayList<Int>()

//    김영호의 전용 브런치
    val chatDataList = ArrayList<ChatData>()
    var chatAdapter:ChattingAdapter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        inputBtn.setOnClickListener {

            if(userInputNumEdt.text.length != 3) {
//                Toast.makeText(mContext, "잘못된 입력입니다. 세자리 숫자를 입력해 주세요", Toast.LENGTH_SHORT).show()

                val adb = AlertDialog.Builder(mContext)

                adb.setTitle("입력 오류 안내")
//                adb.setMessage("정말 이 데이터를 삭제하시겠습니까?")
                adb.setMessage("잘못된 입력입니다. 세자리 숫자를 입력해 주세요.")
                adb.setPositiveButton("확인", DialogInterface.OnClickListener {dialog, which ->
                    Toast.makeText(mContext, "확인버튼 누름", Toast.LENGTH_SHORT).show()
                })
//                adb.setNegativeButton("취소", DialogInterface.OnClickListener {dialog, which ->
//                    Toast.makeText(mContext, "취소버튼 누름", Toast.LENGTH_SHORT).show()
//                })
                adb.setNegativeButton("취소", null)

                adb.show()


            } else {
                chatDataList.add(ChatData(userInputNumEdt.text.toString(), "user"))
                chatAdapter?.notifyDataSetChanged()


                chatListView.smoothScrollToPosition(chatDataList.size -1) //10칸; : 마지막 9번

                Handler().postDelayed({

                    calculateStrikeAndBalls()

                }, 800)            }
        }
    }

    fun calculateStrikeAndBalls() {

        val userInputValue = userInputNumEdt.text.toString().toInt()
//        100의 자리 /10 자리 / 1의 자리를 따다 배열에 저장. => 567 / 100 = 5
//        10의 자리 => 567 : 6 => 567 / 10 = 56 % 10 = 6 => 567 / 10 % 10
//        1의 자리 => 567 : 7 => 567 % 10
//        입력한 숫자의 세자리를 따서 배열에 저장
        val userInputNumArr = ArrayList<Int>()
        userInputNumArr.add(userInputValue / 100)
        userInputNumArr.add(userInputValue / 10 % 10)
        userInputNumArr.add(userInputValue % 10)

        var strikeCount = 0
        var ballCount = 0

        for ( i in 0..2) {
            for (j in 0..2) {
//                입력한 값과 / 컴퓨터가 낸 문재의 자라수 비교
                if(computerNumArray.get(j) == userInputNumArr.get(i)) {
                    if( i == j) {
                        strikeCount++
                    }
                    else {
                        ballCount++
                    }
                }

            }
        }

        chatDataList.add(ChatData("${strikeCount}S ${ballCount}B 입니다.", "computer"))
        chatAdapter?.notifyDataSetChanged()

        chatListView.smoothScrollToPosition(chatDataList.size -1) // 10칸 : 마지칸 9번


        if(strikeCount == 3) {
            chatDataList.add(ChatData("축하합니다! 정답입니다.","computer"))
            chatAdapter?.notifyDataSetChanged()

            chatListView.smoothScrollToPosition(chatDataList.size -1) // 10칸 : 마지칸 9번
        }
    }

    override fun setValues() {
        makeComputerNurmbers()

        chatAdapter = ChattingAdapter(mContext, R.layout.chat_list_item, chatDataList)
        chatListView.adapter = chatAdapter

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
