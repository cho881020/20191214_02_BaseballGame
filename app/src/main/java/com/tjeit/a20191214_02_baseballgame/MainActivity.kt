package com.tjeit.a20191214_02_baseballgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
            chatDataList.add(ChatData(userInputNumEdt.text.toString(), "user"))

            chatAdapter?.notifyDataSetChanged()

            calculateStrikeAndBalls()

        }

    }

    fun calculateStrikeAndBalls() {

        chatDataList.add(ChatData("?S ?B 입니다.", "computer"))
        chatAdapter?.notifyDataSetChanged()

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
