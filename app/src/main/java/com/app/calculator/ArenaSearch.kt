package com.app.calculator

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.tabs.TabLayout

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
}