package com.app.calculator

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.app.calculator.api.Api
import com.app.calculator.data.ArenaInfo
import com.app.calculator.data.ArenaInfoWrapper
import com.app.calculator.data.ArenaInfoWrapper2
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArenaRegister  : AppCompatActivity(), EventListener {

    var dfList = arrayListOf<String>()
    var cartList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.arena_register)

        //파라미터 처리.
        val intent = intent
        val df : ArenaInfo? = intent.getParcelableExtra<ArenaInfo>("df")
        //화면 셋팅
        this.setDfDeck(df!!)

        //화면클릭시 키보드 숨기기.
        this.setupUI(findViewById(R.id.container))

        //방덱 리스트에 추가.
        dfList.add(df!!.df_1_character_id!!)
        dfList.add(df!!.df_2_character_id!!)
        dfList.add(df!!.df_3_character_id!!)
        dfList.add(df!!.df_4_character_id!!)
        dfList.add(df!!.df_5_character_id!!)

        //탭
        val pagerAdapter = FragmentAdapter(supportFragmentManager)

        val pager = findViewById<ViewPager>(R.id.viewPager)
        pager.adapter = pagerAdapter

        val tab = findViewById<TabLayout>(R.id.tab)
        tab.setupWithViewPager(pager)

        //버튼 설정
        //

        val registerBtn = findViewById<Button>(R.id.register)
        val cancelBtn = findViewById<Button>(R.id.cancelBtn)

        registerBtn.setOnClickListener {
            this.register()
        }

        cancelBtn.setOnClickListener {
            //이전 화면으로 이동.
            onBackPressed()
        }
    }

    fun hideSoftKeyboard() {
        currentFocus?.let {
            val inputMethodManager = ContextCompat.getSystemService(
                this,
                InputMethodManager::class.java
            )!!
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    fun setupUI(view: View) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener { v, event ->
                hideSoftKeyboard()
                false
            }
        }

        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView)
            }
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

    override fun click(id: String) {

        if(this.validCart(id)){
            var cartLayout : LinearLayout = findViewById(R.id.regCart)

            val layoutParams = TableRow.LayoutParams(60.topx(), 60.topx())
            layoutParams.setMargins(5, 0, 5, 0)

            var selectImage = ImageView(this)

            val resId = resources.getIdentifier(id, "drawable", packageName)

            selectImage.setImageResource(resId)
            selectImage.tag = resId
            selectImage.layoutParams = layoutParams

            selectImage.setOnClickListener {
                //cart에서 제거.
                var removeTag : String = resources.getResourceName((it.getTag() as Int)!!).toString()
                var idx : Int = removeTag.indexOf("ch")
                this.cartList.remove(removeTag.substring(idx, removeTag.length))
                //view 삭제.
                selectImage.visibility = View.GONE
            }

            cartLayout.addView(selectImage)

            //아이디 추가.
            this.cartList.add(id)
        }
    }
    //카트 validation
    fun validCart(id : String) : Boolean{

        var result = true

        var util = Util()

        if(this.cartList.size == 5){
            util.alert(this, "알림", "이미 5명을 선택하였습니다.");
            return false
        }

        if(this.cartList.indexOf(id) > -1){
            util.alert(this, "알림", "이미 선택한 캐릭터 입니다.");
            return false
        }


        return result
    }

    fun register() {

        var util = Util()

        if(this.cartList.size != 5){
            util.alert(this, "알림", "캐릭터 5명을 선택해주세요.")
            return
        }

        var param = ArenaInfo()

        param.df_1_character_id = dfList.get(0)
        param.df_2_character_id = dfList.get(1)
        param.df_3_character_id = dfList.get(2)
        param.df_4_character_id = dfList.get(3)
        param.df_5_character_id = dfList.get(4)

        param.of_1_character_id = cartList.get(0)
        param.of_2_character_id = cartList.get(1)
        param.of_3_character_id = cartList.get(2)
        param.of_4_character_id = cartList.get(3)
        param.of_5_character_id = cartList.get(4)

        var nickName = findViewById<EditText>(R.id.nick).text.toString()

        if(null != nickName){
            param.regNick = nickName
        }

        val api = Api().getService()
        var request = api.registerArena(param)

        var result = request.enqueue(object : Callback<ArenaInfoWrapper2> {
            override fun onFailure(call: Call<ArenaInfoWrapper2>, t: Throwable) {t.printStackTrace()}
            override fun onResponse(call: Call<ArenaInfoWrapper2>, response: Response<ArenaInfoWrapper2>) {

                var result : ArenaInfo = response.body()!!.result

                regCallBack(result.resultCode!!)

            }
        })


    }

    fun regCallBack(code : String){

        var util = Util()

        if(null == code || "".equals(code)){
            util.alert(this, "알림", "오류가 발생하였습니다. 관리자에게 문의하세요.")
        }

        if("OK".equals(code)){
            successDialog()
        // util.alert(this, "알림", "성공적으로 등록되었습니다.")
        }

        if("DUPLICATE".equals(code)){
            util.alert(this, "알림", "동일한 데이터가 이미 등록되어있습니다.")
        }


    }

    fun successDialog(){

        // 다이얼로그
        val builder = AlertDialog.Builder(
            ContextThemeWrapper(
                this@ArenaRegister,
                R.style.AlertDialog
            )
        )
        builder.setTitle("확인")
        builder.setMessage("성공적으로 등록되었습니다. 추가 등록 하시겠습니까?")

        builder.setPositiveButton(R.string.regAdd) { dialog, id ->

            dialog.dismiss()

        }
        builder.setNegativeButton(R.string.returnSearch) { dialog, id ->

            val intent = Intent(this, ArenaSearch::class.java)
            startActivity(intent);
        }

        val alert = builder.create()
        alert.show()
    }

}