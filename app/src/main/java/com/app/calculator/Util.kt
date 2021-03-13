package com.app.calculator

import android.R
import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.TextView





class Util {


    //타이틀과 메세지를 보내면 alert를 띄워준다.
     fun alert(view: Context, title: String, message: String) {

       /* val builder = AlertDialog.Builder(view);

        builder.setTitle(title).setMessage(message)

        val alertDialog = builder.create()

        alertDialog.show()
*/
        val dialog = AlertDialog.Builder(view)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("확인", null)
            .show()
        val textView = dialog.findViewById<View>(R.id.message) as TextView
        textView.textSize = 16f

    }
}