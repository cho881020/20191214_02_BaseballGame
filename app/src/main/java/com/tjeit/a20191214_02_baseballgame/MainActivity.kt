package com.tjeit.a20191214_02_baseballgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tjeit.a20191214_02_baseballgame.adapters.ChatingAdapters
import com.tjeit.a20191214_02_baseballgame.datas.ChatData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

//    문제로 나온 숫자를 담기 위한 배열
    val computerNumArray = ArrayList<Int>()
    val chatDatatList = ArrayList<ChatData>()
//    김미현의 전용 브런치입니다.
    val chatAdapter:ChatingAdapters? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        inputBtn.setOnClickListener {
            chatDatatList.add(ChatData(userInputNumEdt.text.toString(),"user"))

            chatAdapter.notifyDataSetChanged()
        }
    }

    override fun setValues() {
        makeComputerNumbers()

        chatAdapter = ChatingAdapters(mContext,R.layout.chat_list_item,chatDatatList)
            chatListView.adapter = chatAdapter
    }
    
    fun makeComputerNumbers(){
        for(i in 0..2){
            Log.d("for문","${i}")
            while(true){
                val randomNum = (Math.random() *9 +1).toInt()// 0<= 랜덤값<1 *9 +1

                var isDuplicateOk = true

                for(num in computerNumArray){
                    if ( num == randomNum){
                        isDuplicateOk == false
                        break
                    }
                }
                if (isDuplicateOk){
                    computerNumArray.add(randomNum)
                    break
                }
            }
        }
        for (num in computerNumArray){
            Log.d("출제된 숫자", "${num}")
        }
    }

}
