package com.tjeit.a20191214_02_baseballgame

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.tjeit.a20191214_02_baseballgame.adapters.ChatingAdapters
import com.tjeit.a20191214_02_baseballgame.datas.ChatData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

//    문제로 나온 숫자를 담기 위한 배열
    val computerNumArray = ArrayList<Int>()
    val chatDatatList = ArrayList<ChatData>()
//    김미현의 전용 브런치입니다.
    var chatAdapter:ChatingAdapters? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        inputBtn.setOnClickListener {
            chatDatatList.add(ChatData(userInputNumEdt.text.toString(),"user"))



            if(userInputNumEdt.text.length !=3){
                Toast.makeText(mContext,"잘못된 입력입니다. 세자리 숫자를 입력해 주세요",Toast.LENGTH_SHORT).show()
                val adb = AlertDialog.Builder(mContext)

                adb.setIcon(R.drawable.ic_launcher_background)
                adb.setTitle("입력 오류 안내")
                adb.setMessage("잘못된 입력입니다. 세 자리 숫자를 입력해 주세요.")
                adb.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                    Toast.makeText(mContext,"확인버튼 누름",Toast.LENGTH_SHORT).show()
                })
                adb.setNegativeButton("취소",null)
                adb.show()
            }else{
                Handler().postDelayed({
                    calculateStrikeAndBalls()
                    chatAdapter?.notifyDataSetChanged()
                    chatListView.smoothScrollToPosition(chatDatatList.size-1)
                },500)
            }

        }
    }

    fun calculateStrikeAndBalls(){
        val userInputValue = userInputNumEdt.text.toString().toInt()
//        각 자리수 따다 배열에 저장.
        val userInputNumArr = ArrayList<Int>()
        userInputNumArr.add(userInputValue/100)
        userInputNumArr.add(userInputValue/10%10)
        userInputNumArr.add(userInputValue%10)


        var strikeCount=0
        var ballCount=0

        for (i in 0..2){
            for (j in 0..2){
//                입력한 값과 컴퓨터가 낸 문제의 자리수 비교.
                if(computerNumArray.get(j)==userInputNumArr.get(i)){
                    if(i==j){
                        strikeCount++
                    }else{
                        ballCount++
                    }
                }
            }
        }

        chatDatatList.add(ChatData("${strikeCount}S ${ballCount}B 입니다","computer"))
        chatAdapter?.notifyDataSetChanged()
        chatListView.smoothScrollToPosition(chatDatatList.size-1)

        if (strikeCount ==3){
            chatDatatList.add(ChatData("축하합니다 정답입니다.","computer"))
            chatAdapter?.notifyDataSetChanged()
            chatListView.smoothScrollToPosition(chatDatatList.size-1)
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
