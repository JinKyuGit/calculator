package com.app.calculator

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.app.calculator.data.ArenaInfo


class ArenaSearchResult : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.arena_search_result)

        //파라미터 처리.
        val intent = intent
        val df : ArenaInfo? = intent.getParcelableExtra<ArenaInfo>("df")
        val ofList : ArrayList<ArenaInfo>? = intent.getParcelableArrayListExtra("ofList")

        //화면 셋팅
        this.setDfDeck(df!!)
        this.setOfDeckList(ofList!!)
        
        //버튼 처리
        
        //뒤로가기
        val backBtn = findViewById<Button>(R.id.backBtn)

        backBtn.setOnClickListener {

            val intent = Intent(this, ArenaSearch::class.java)
            startActivity(intent);
        }


    }


    //dp를 픽셀로 리턴.
    fun Int.topx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

    //방덱 셋팅
    fun setDfDeck(df : ArenaInfo) {

        var dfDeckLayout : LinearLayout = findViewById(R.id.dfDeck)

        val layoutParams = TableRow.LayoutParams(65.topx(), 65.topx())
        layoutParams.setMargins(5, 0, 5, 0)

        var one = ImageView(this)
        val resId1 = resources.getIdentifier(df.df_1_character_id, "drawable", packageName)
        one.setImageResource(resId1)
        one.layoutParams = layoutParams

        var two = ImageView(this)
        val resId2 = resources.getIdentifier(df.df_2_character_id, "drawable", packageName)
        two.setImageResource(resId2)
        two.layoutParams = layoutParams

        var three = ImageView(this)
        val resId3 = resources.getIdentifier(df.df_3_character_id, "drawable", packageName)
        three.setImageResource(resId3)
        three.layoutParams = layoutParams

        var four = ImageView(this)
        val resId4 = resources.getIdentifier(df.df_4_character_id, "drawable", packageName)
        four.setImageResource(resId4)
        four.layoutParams = layoutParams

        var five = ImageView(this)
        val resId5 = resources.getIdentifier(df.df_5_character_id, "drawable", packageName)
        five.setImageResource(resId5)
        five.layoutParams = layoutParams

        dfDeckLayout.addView(one)
        dfDeckLayout.addView(two)
        dfDeckLayout.addView(three)
        dfDeckLayout.addView(four)
        dfDeckLayout.addView(five)
    }
    //공덱 리스트 셋팅
    fun setOfDeckList(ofList : ArrayList<ArenaInfo>){

        var table : TableLayout = findViewById(R.id.resultTable)

        val displaymetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displaymetrics)
        val width = displaymetrics.widthPixels

        for(loop in ofList){

            var row = TableRow(this)

            val layoutParams = TableRow.LayoutParams(65.topx(), 65.topx())
            layoutParams.setMargins(5, 0, 5, 0)

            var one = ImageView(this)
            val resId1 = resources.getIdentifier(loop.of_1_character_id, "drawable", packageName)
            one.setImageResource(resId1)
            one.layoutParams = layoutParams

            var two = ImageView(this)
            val resId2 = resources.getIdentifier(loop.of_2_character_id, "drawable", packageName)
            two.setImageResource(resId2)
            two.layoutParams = layoutParams

            var three = ImageView(this)
            val resId3 = resources.getIdentifier(loop.of_3_character_id, "drawable", packageName)
            three.setImageResource(resId3)
            three.layoutParams = layoutParams

            var four = ImageView(this)
            val resId4 = resources.getIdentifier(loop.of_4_character_id, "drawable", packageName)
            four.setImageResource(resId4)
            four.layoutParams = layoutParams

            var five = ImageView(this)
            val resId5 = resources.getIdentifier(loop.of_5_character_id, "drawable", packageName)
            five.setImageResource(resId5)
            five.layoutParams = layoutParams

            var info = TextView(this)
            info.setText("작성자 : "+loop.regNick+", 작성일 : "+loop.regDate)

            row.addView(one)
            row.addView(two)
            row.addView(three)
            row.addView(four)
            row.addView(five)

            table.addView(info)
            table.addView(row)

        }


    }
}