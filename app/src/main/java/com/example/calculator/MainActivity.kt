package com.example.calculator

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.text.InputType
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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.marginLeft


class MainActivity : AppCompatActivity() {

    public var container: ConstraintLayout? = null
    public var applyConstraintSet = ConstraintSet()
    public val damageArray = arrayOf(R.id.damage1, R.id.damage2, R.id.damage3, R.id.damage4, R.id.damage5)
    public var damageLength = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //변수 생성 및 초기화.
        container = findViewById(R.id.container);
        applyConstraintSet.clone(container);

        //화면클릭시 키보드 숨기기.
        this.setupUI(findViewById(R.id.container))

        val addBtn = findViewById<Button>(R.id.addBtn); //칸 추가
        val delBtn = findViewById<Button>(R.id.delBtn); //칸 제거
        val calculBtn =  findViewById<Button>(R.id.calculateBtn); //계산
        val resetBtn = findViewById<Button>(R.id.resetBtn); //초기화




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




        //화면 초기화 함수 호출.
       this.init()
    }

    fun hideSoftKeyboard() {
        currentFocus?.let {
            val inputMethodManager = ContextCompat.getSystemService(this, InputMethodManager::class.java)!!
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

        val layoutParams = LinearLayout.LayoutParams(225.topx(), 60.topx())
        layoutParams.setMargins(50, 0, 0, 0)
        //첫번째
        EditText[0] = EditText(this)
        EditText[0]!!.inputType = InputType.TYPE_CLASS_NUMBER
        EditText[0]!!.id = damageArray[0]
       // EditText[0]!!.width = 225.topx()
      //  EditText[0]!!.height = 60.topx()
        EditText[0]!!.layoutParams = layoutParams
        //두번째
        EditText[1] = EditText(this)
        EditText[1]!!.inputType = InputType.TYPE_CLASS_NUMBER
        EditText[1]!!.id = damageArray[1]
      //  EditText[1]!!.width = 225.topx()
     //   EditText[1]!!.height = 60.topx()
        EditText[1]!!.layoutParams = layoutParams
        //화면에 적용.
        innerView!!.addView(EditText[0])
        innerView!!.addView(EditText[1])
      //  setContentView(container)

        /**
        //constraint 적용
        //첫번째 데미지 칸
        applyConstraintSet.constrainHeight(damageArray[0],
            ConstraintSet.WRAP_CONTENT)
        applyConstraintSet.constrainWidth(damageArray[0],
            ConstraintSet.WRAP_CONTENT)
        applyConstraintSet.setHorizontalBias(damageArray[0], 0.104f)
        applyConstraintSet.connect(damageArray[0], ConstraintSet.LEFT,
            ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0)
        applyConstraintSet.connect(damageArray[0], ConstraintSet.RIGHT,
            ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0)
        applyConstraintSet.connect(damageArray[0], ConstraintSet.TOP,
            R.id.damageTitle, ConstraintSet.BOTTOM, 26.topx())

        //두번째 데미지 칸.
        applyConstraintSet.constrainHeight(damageArray[1],
            ConstraintSet.WRAP_CONTENT)
        applyConstraintSet.constrainWidth(damageArray[1],
            ConstraintSet.WRAP_CONTENT)
        applyConstraintSet.setHorizontalBias(damageArray[1], 0.104f)
        applyConstraintSet.connect(damageArray[1], ConstraintSet.LEFT,
            ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0)
        applyConstraintSet.connect(damageArray[1], ConstraintSet.RIGHT,
            ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0)
        applyConstraintSet.connect(damageArray[1], ConstraintSet.TOP,
            damageArray[0], ConstraintSet.BOTTOM, 26.topx())

        //ConstraintSet을 통해 view에 적용.
        applyConstraintSet.applyTo(container)

        */



    }
    //dp를 픽셀로 리턴.
    fun Int.topx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

    //칸 추가 버튼 클릭
    fun add(){

        var innerView : LinearLayout = findViewById(R.id.innerView);
        var delView = findViewById<TextView>(R.id.resultMsg)
        val layoutParams = LinearLayout.LayoutParams(225.topx(), 60.topx())
        layoutParams.setMargins(50, 0, 0, 0)
        //기존 결과 메시지 삭제 - 칸 겹침.
        innerView!!.removeView(delView)
        //최대값은 5.
        if(damageLength == 5){
            return
        }

        val newId = damageArray[damageLength]
        val prevId = damageArray[damageLength-1]

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
        //화면에 적용.
        innerView!!.addView(editText)
        damageLength++;

    /**
        //constraint 적용
        applyConstraintSet.constrainHeight(newId,
            ConstraintSet.WRAP_CONTENT)
        applyConstraintSet.constrainWidth(newId,
            ConstraintSet.WRAP_CONTENT)
        applyConstraintSet.setHorizontalBias(newId, 0.104f)
        applyConstraintSet.connect(newId, ConstraintSet.LEFT,
            ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0)
        applyConstraintSet.connect(newId, ConstraintSet.RIGHT,
            ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0)
        applyConstraintSet.connect(newId, ConstraintSet.TOP,
            prevId, ConstraintSet.BOTTOM, 26.topx())

        //ConstraintSet을 통해 view에 적용.
        applyConstraintSet.applyTo(container)
**/


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

        var delView = findViewById<EditText>(damageArray[damageLength-1])
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
                util.alert(this, "알림", ""+(i+1)+"번째 칸에 데미지를 입력해주세요.")
                return
            }
            damageList[i] = damageStr.toInt()
        } // for

        var cal = Calculator()
        cal.attack(bossHp, damageList)

        this.showResultMsg(cal.messageList)
    }

    fun getDamageIdByIndex(index : Int): Int{
        return this.damageArray[index]
    }


    fun showResultMsg(messageList : ArrayList<String>){
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

        /*
        //constraint 적용
        applyConstraintSet.constrainHeight(R.id.resultMsg,
            ConstraintSet.WRAP_CONTENT)
        applyConstraintSet.constrainWidth(R.id.resultMsg,
            ConstraintSet.WRAP_CONTENT)
        applyConstraintSet.setHorizontalBias(R.id.resultMsg, 0.104f)
        applyConstraintSet.connect(R.id.resultMsg, ConstraintSet.LEFT,
            ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0)
        applyConstraintSet.connect(R.id.resultMsg, ConstraintSet.RIGHT,
            ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0)
        applyConstraintSet.connect(R.id.resultMsg, ConstraintSet.TOP,
            this.getDamageIdByIndex(damageLength-1), ConstraintSet.BOTTOM, 26.topx())

        //ConstraintSet을 통해 view에 적용.
        applyConstraintSet.applyTo(container)
*/
    }

    fun reset(){
            // 다이얼로그
            val builder = AlertDialog.Builder(ContextThemeWrapper(this@MainActivity, R.style.AlertDialog))
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