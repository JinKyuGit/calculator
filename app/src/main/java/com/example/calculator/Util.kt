package com.example.calculator

import android.app.AlertDialog
import android.content.Context

class Util {


    //타이틀과 메세지를 보내면 alert를 띄워준다.
     fun alert(view : Context, title: String, message: String) {

        val builder = AlertDialog.Builder(view);

        builder.setTitle(title).setMessage(message)

        val alertDialog = builder.create()

        alertDialog.show()


    }
}