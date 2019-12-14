package com.tjeit.a20191214_02_baseballgame

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.tjeit.a20191214_02_baseballgame.adapters.ChatingAdapter
import com.tjeit.a20191214_02_baseballgame.datas.ChatData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

//    문제로 나온 숫자를 담기 위한 배열
    val computerNumArray = ArrayList<Int>()

//    조경진의 전용 브런치입니다.

    val chatDataList = ArrayList<ChatData>()
    var chatAdapter:ChatingAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        inputBtn.setOnClickListener {

            if(userInputNumEdt.text.length !=3 ){
//                Toast.makeText(mContext,"잘못된 입력입니다. 세자리 숫자 입력해주세요",Toast.LENGTH_SHORT).show()
                val adb = AlertDialog.Builder(mContext)

//                예시
//                adb.setTitle("삭제 확인")
//                adb.setMessage("정말 이 데이터를 삭제하시겠습니까?")
                adb.setTitle("입력오류안내")
                adb.setMessage("잘못된 입력입니다. 세자리 숫자를 입력해주세요")
                adb.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                    Toast.makeText(mContext,"확인버튼누름",Toast.LENGTH_SHORT).show()
                })
                adb.setNegativeButton("취소", null )

//                adb.setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which ->
//                    Toast.makeText(mContext,"취소버튼누름",Toast.LENGTH_SHORT).show()
//                })

                adb.show()
            }

            else{
                chatDataList.add(ChatData(userInputNumEdt.text.toString(), "user"))

                chatAdapter?.notifyDataSetChanged()
                chatListView.smoothScrollToPosition(chatDataList.size - 1)
                Handler().postDelayed({

                    calculateStrikeAndBalls()

                }, 500)

            }



        }

    }

    fun calculateStrikeAndBalls() {

        val userInputValue = userInputNumEdt.text.toString().toInt()

        val userInputNumArr = ArrayList<Int>()
        userInputNumArr.add(userInputValue / 100)
        userInputNumArr.add(userInputValue / 10 % 10)
        userInputNumArr.add(userInputValue % 10)

        var strikeCount = 0
        var ballCount = 0

        for (i in 0..2) {
            for (j in 0..2) {

                if (computerNumArray.get(j) == userInputNumArr.get(i) ) {
                    if(i == j) {
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
        chatListView.smoothScrollToPosition(chatDataList.size - 1)

        if (strikeCount == 3) {
            chatDataList.add(ChatData("축하합니다! 정답입니다.", "computer"))
            chatAdapter?.notifyDataSetChanged()
            chatListView.smoothScrollToPosition(chatDataList.size - 1)
        }

    }

    override fun setValues() {
        makeComputerNumbers()

        chatAdapter = ChatingAdapter(mContext, R.layout.chat_list_item, chatDataList)
        chatListView.adapter = chatAdapter
    }

    fun makeComputerNumbers() {
        for (i in 0..2) {

            while (true) {
                val randomNum = (Math.random() * 9 + 1).toInt()  // 1<= 랜덤값 * 9 + 1 < 10

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
            Log.d("출제된 숫자", "${num}")
        }

    }

}
