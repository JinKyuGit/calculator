package com.app.calculator

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.app.calculator.api.Api
import com.app.calculator.data.ArenaInfo
import com.app.calculator.data.ArenaInfoWrapper
import com.app.calculator.data.ScheduleWrapper
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArenaSearch : AppCompatActivity(), EventListener {

    lateinit var mAdView2 : AdView

    var cartList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.arena_search)

        //구글 광고 적용.
        MobileAds.initialize(this) { }

        mAdView2 = findViewById(R.id.adView2)
        val adRequest = AdRequest.Builder().build()
        mAdView2.loadAd(adRequest)


        //탭
        val pagerAdapter = FragmentAdapter(supportFragmentManager)

        val pager = findViewById<ViewPager>(R.id.viewPager)
        pager.adapter = pagerAdapter

        val tab = findViewById<TabLayout>(R.id.tab)
        tab.setupWithViewPager(pager)

        //버튼 설정
        //조회
        val searchBtn = findViewById<Button>(R.id.searchBtn)

        searchBtn.setOnClickListener {
            this.search()
        }


    }



/*    interface ArenaSearchInterface{
        fun click(id : Int) {

            System.out.println("click 이벤트 : "+id)

        }
    }*/


    //dp를 픽셀로 리턴.
    fun Int.topx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

    override fun click(id: String) {

        if(this.validCart(id)){
            var cartLayout : LinearLayout = findViewById(R.id.searchCart)

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

    fun search(){

        var util = Util()

        if(this.cartList.size != 5){
            util.alert(this, "알림", "캐릭터 5명을 선택해주세요.")
            return
        }

        var param = ArenaInfo()
        param.df_1_character_id = cartList.get(0)
        param.df_2_character_id = cartList.get(1)
        param.df_3_character_id = cartList.get(2)
        param.df_4_character_id = cartList.get(3)
        param.df_5_character_id = cartList.get(4)

        val api = Api().getService()
        var request = api.searchArenaInfo(param)

        var result = request.enqueue(object : Callback<ArenaInfoWrapper> {
            override fun onFailure(call: Call<ArenaInfoWrapper>, t: Throwable) {t.printStackTrace()}
            override fun onResponse(call: Call<ArenaInfoWrapper>, response: Response<ArenaInfoWrapper>) {

                var resultList : ArrayList<ArenaInfo> = response.body()!!.result

                if(null == resultList || resultList.size == 0){
                    //데이터가 없음 -> 등록화면으로 이동하게 함.
                }else {
                    //화면 이동 함수 호출.
                    setResultView(param, resultList)

                }


            }
        })
    }

    fun setResultView(df: ArenaInfo, ofList: ArrayList<ArenaInfo>){

        val intent = Intent(this, ArenaSearchResult::class.java)

        intent.putExtra("df", df);
        intent.putExtra("ofList", ofList);

        startActivity(intent);


    }
}