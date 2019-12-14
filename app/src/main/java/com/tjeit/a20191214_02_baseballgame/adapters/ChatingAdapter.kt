package com.tjeit.a20191214_02_baseballgame.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.tjeit.a20191214_02_baseballgame.R
import com.tjeit.a20191214_02_baseballgame.datas.ChatData

class ChatingAdapter(context:Context, resId:Int, list:ArrayList<ChatData>)
    : ArrayAdapter<ChatData>(context, resId, list) {

    val mContext = context
    val mList = list
    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView

        tempRow?.let {

        }.let {
            tempRow = inf.inflate(R.layout.chat_list_item, null)
        }

        val row = tempRow!!


        return row

    }


}
