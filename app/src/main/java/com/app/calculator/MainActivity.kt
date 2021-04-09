package com.app.calculator

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.app.calculator.api.Api
import com.app.calculator.data.Admin
import com.app.calculator.data.ScheduleWrapper
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    public var container: LinearLayout? = null
    public var applyConstraintSet = ConstraintSet()
    public val damageArray = arrayOf(
            R.id.damage1,
            R.id.damage2,
            R.id.damage3,
            R.id.damage4,
            R.id.damage5
    )
    public var damageLength = 2
    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //변수 생성 및 초기화.
        container = findViewById(R.id.container);
        //applyConstraintSet.clone(container);

        //화면클릭시 키보드 숨기기.
        this.setupUI(findViewById(R.id.container))

        val addBtn = findViewById<Button>(R.id.addBtn); //칸 추가
        val delBtn = findViewById<Button>(R.id.delBtn); //칸 제거
        val calculBtn =  findViewById<Button>(R.id.calculateBtn); //계산
        val resetBtn = findViewById<Button>(R.id.resetBtn); //초기화
        val helpBtn = findViewById<Button>(R.id.helpBtn); //도움말

        MobileAds.initialize(this) { }

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        /* 화면 기초 기능 설정 */
        addBtn.setOnClickListener{
                this.add()
        }

        delBtn.setOnClickListener {
                this.delete()
        }

        //계산버튼 클릭시 함수 호출.
        calculBtn.setOnClickListener {
                this.runCalculator()
        }

        resetBtn.setOnClickListener {
            this.reset()
        }

        helpBtn.setOnClickListener {
            val util = Util()
            val str =
                "☆ 이월 계산기 ☆\n" +
                        "- \"프린세스 커넥트 : 리다이브!\"의 클랜전 이월시간 계산기 프로그램입니다.\n" +
                        "\n" +
                        "※ 사용 방법 ※\n" +
                        "- 보스의 잔여체력과 입력한 데미지를 비교하여, 두 가지 결과를 확인할 수 있습니다.\n" +
                        "- 총 데미지 < 보스 체력 : 풀이월에 필요한 데미지를 표시합니다.\n" +
                        "- 총 데미지 > 보스 체력 : 조합에 따른 이월시간을 표시합니다.\n" +
                        "\n" +
                        "● 만 단위 ●\n" +
                        "- 보스 체력, 적용 데미지에 값 입력 시, 10,000(1만) 단위로 적용됩니다.\n" +
                        "\n" +
                        "● 보스 체력 ● \n" +
                        "- 보스의 체력을 입력합니다.\n" +
                        "\n" +
                        "● 적용 데미지 ●\n" +
                        "- 칸 추가 : 입장한 인원 수만큼 칸을 추가할 수 있습니다.\n" +
                        "- 칸 제거 : 인원 변동 발생 시, 칸을 제거할 수 있습니다.\n" +
                        "- 계산 : 입력한 값을 토대로 결과를 출력합니다."

            util.alert(this, "안내", str)
        }


        //화면 초기화 함수 호출.
       this.init()
    }
    //메뉴
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    //메뉴 선택
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {

            //아레나 조회 페이지로 이동
            R.id.menu1 -> {
                val intent = Intent(this, ArenaSearch::class.java)
                startActivity(intent);
                true
            }
            //일정
            R.id.menu2 -> {
                val api = Api().getService()
                var request = api.getSchedule()

                var result = request.enqueue(object : Callback<ScheduleWrapper> {
                    override fun onFailure(call: Call<ScheduleWrapper>, t: Throwable) {t.printStackTrace()}
                    override fun onResponse(call: Call<ScheduleWrapper>, response: Response<ScheduleWrapper>) {

                       var today = response?.body()?.result?.today
                       var tomorrow = response?.body()?.result?.tomorrow

                        alertSchedule(today, tomorrow)
                    }
                })


                true
            }
            /*
            //설문조사.
            R.id.menu3 -> {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/urR4gSdw88b2ySdF7"))
                startActivity(browserIntent)
                true
            } */
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun alertSchedule(today : String?, tomorrow : String?) {

        val util = Util()

        var str = today+"\n"+tomorrow

        util.alert(this, "일정", str)

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
    
    //초기화 - 데미지 배열을 2개로 고정해놓는다.
    fun init(){

        var innerView : LinearLayout = findViewById(R.id.innerView);
        var EditText = arrayOfNulls<EditText>(2)
        //레이아웃
        val layoutParams = LinearLayout.LayoutParams(225.topx(), 60.topx())
        layoutParams.setMargins(20, 0, 0, 0)

        //필터
        val maxLength = 4
        val fArray = arrayOfNulls<InputFilter>(1)
        fArray[0] = LengthFilter(maxLength)

        //첫번째
        EditText[0] = EditText(this)
        EditText[0]!!.inputType = InputType.TYPE_CLASS_NUMBER
        EditText[0]!!.id = damageArray[0]
       // EditText[0]!!.width = 225.topx()
      //  EditText[0]!!.height = 60.topx()
        EditText[0]!!.layoutParams = layoutParams
        EditText[0]!!.filters = fArray
        //두번째
        EditText[1] = EditText(this)
        EditText[1]!!.inputType = InputType.TYPE_CLASS_NUMBER
        EditText[1]!!.id = damageArray[1]
      //  EditText[1]!!.width = 225.topx()
     //   EditText[1]!!.height = 60.topx()
        EditText[1]!!.layoutParams = layoutParams
        EditText[1]!!.filters = fArray
        //화면에 적용.
        innerView!!.addView(EditText[0])
        innerView!!.addView(EditText[1])
      //  setContentView(container)

    }
    //dp를 픽셀로 리턴.
    fun Int.topx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

    //칸 추가 버튼 클릭
    fun add(){

        var innerView : LinearLayout = findViewById(R.id.innerView);
        var delView = findViewById<TextView>(R.id.resultMsg)

        //레이아웃
        val layoutParams = LinearLayout.LayoutParams(225.topx(), 60.topx())
        layoutParams.setMargins(20, 0, 0, 0)

        //필터
        val maxLength = 4
        val fArray = arrayOfNulls<InputFilter>(1)
        fArray[0] = LengthFilter(maxLength)

        //기존 결과 메시지 삭제 - 칸 겹침.
        innerView!!.removeView(delView)
        //최대값은 5.
        if(damageLength == 5){
            return
        }

        val newId = damageArray[damageLength]
        val prevId = damageArray[damageLength - 1]

        if(prevId == -1){
            return
        }

        //view 생성
        var editText = EditText(this)
        editText!!.inputType = InputType.TYPE_CLASS_NUMBER
        editText!!.id = newId
       // editText!!.width = 225.topx()
       // editText!!.height = 60.topx()
        editText!!.layoutParams = layoutParams
        editText!!.filters = fArray
        //화면에 적용.
        innerView!!.addView(editText)
        damageLength++;



    }

    //칸 제거 버튼 클릭
    fun delete(){

        var innerView : LinearLayout = findViewById(R.id.innerView);
        //기존 결과 메시지 삭제 - 칸 겹침.
        var delMsg = findViewById<TextView>(R.id.resultMsg)
        innerView!!.removeView(delMsg)

        if(damageLength == 1){
            return
        }

        var delView = findViewById<EditText>(damageArray[damageLength - 1])
        innerView!!.removeView(delView)

        damageLength--
    }

    //계산함수 호출.
    fun runCalculator(){
        var util = Util()
        val damageList = IntArray(damageLength)
        val bossHpStr = findViewById<EditText>(R.id.bossHp).text.toString()
        if(null == bossHpStr || "".equals(bossHpStr)){
            util.alert(this, "알림", "보스의 잔여 체력을 입력해주세요.")
            return
        }
        val bossHp = bossHpStr.toInt()

        for(i in 0 until damageList.size){

            var id = this.getDamageIdByIndex(i)

            val damageStr = findViewById<EditText>(id).text.toString()
            if(null == damageStr || "".equals(damageStr)){
                util.alert(this, "알림", "" + (i + 1) + "번째 칸에 데미지를 입력해주세요.")
                return
            }
            damageList[i] = damageStr.toInt()
        } // for

        var cal = Calculator()
        cal.attack(bossHp, damageList)

        this.showResultMsg(cal.messageList)
    }

    fun getDamageIdByIndex(index: Int): Int{
        return this.damageArray[index]
    }


    fun showResultMsg(messageList: ArrayList<String>){
        //기존 삭제

        var innerView : LinearLayout = findViewById(R.id.innerView);
        var delView = findViewById<TextView>(R.id.resultMsg)
        innerView!!.removeView(delView)

        var textView = TextView(this)

        var message = ""

        for(i in messageList){
            message += i
            message += "\n"
        }


        textView.setText(message)
        textView.id = R.id.resultMsg
        textView.textSize = 16f
        innerView!!.addView(textView)

    }

    fun reset(){
            // 다이얼로그
            val builder = AlertDialog.Builder(
                    ContextThemeWrapper(
                            this@MainActivity,
                            R.style.AlertDialog
                    )
            )
            builder.setTitle("확인")
            builder.setMessage("초기화 하시겠습니까?")

            builder.setPositiveButton(R.string.ok_button) { dialog, id ->
                val i = Intent(this@MainActivity, MainActivity::class.java)
                finish()
                overridePendingTransition(0, 0)
                startActivity(i)
                overridePendingTransition(0, 0)
            }
            builder.setNegativeButton(R.string.cancel_button) { dialog, id ->
                dialog.dismiss()
            }

        val alert = builder.create()
        alert.show()



    }
    
}