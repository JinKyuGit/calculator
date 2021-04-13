package com.app.calculator

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow

class ThirdFragment : Fragment() {


    public val ch_array = arrayOf(
        R.drawable.ch_2,
        R.drawable.ch_3,
        R.drawable.ch_12,
        R.drawable.ch_13,
        R.drawable.ch_15,
        R.drawable.ch_21,
        R.drawable.ch_22,
        R.drawable.ch_30,
        R.drawable.ch_31,
        R.drawable.ch_32,
        R.drawable.ch_41,
        R.drawable.ch_42,
        R.drawable.ch_43,
        R.drawable.ch_44,
        R.drawable.ch_48,
        R.drawable.ch_49,
        R.drawable.ch_52,
        R.drawable.ch_55,
        R.drawable.ch_56,
        R.drawable.ch_61,
        R.drawable.ch_64,
        R.drawable.ch_67,
        R.drawable.ch_68,
        R.drawable.ch_70,
        R.drawable.ch_73,
        R.drawable.ch_74,
        R.drawable.ch_78,
        R.drawable.ch_79,
        R.drawable.ch_80,
        R.drawable.ch_81,
        R.drawable.ch_85,
        R.drawable.ch_86,
        R.drawable.ch_87,
        R.drawable.ch_91,
        R.drawable.ch_92,
        R.drawable.ch_106,
        R.drawable.ch_110



        )

    var listener: EventListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? EventListener
    }

    //dp를 픽셀로 리턴.
    fun Int.topx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Initialize
        activity?.let {
            val table3 : TableLayout = it.findViewById(R.id.table3)


            val displaymetrics = DisplayMetrics()
            activity!!.windowManager.defaultDisplay.getMetrics(displaymetrics)
            val height = displaymetrics.heightPixels
            val width = displaymetrics.widthPixels

            val layoutParams = TableRow.LayoutParams(width/5, 80.topx())
            layoutParams.setMargins(5, 5, 5, 5)
            //전체 row의 수.
            var rowSize = this.ch_array.size / 4;

            //나머지가 있으면 row의 수 + 1
            if(this.ch_array.size % 4 != 0){
                rowSize++;
            }

            var characterIndex = 0

            for(i in 0 until rowSize){

                var row : TableRow
                row = TableRow(it)

                //4개씩 반복.
                for(j in characterIndex .. characterIndex+3){

                    if(this.ch_array.size <= j){
                        break;
                    }

                    //이미지 view 생성 및 이미지 연결.
                    var imageView = ImageView(it)
                    var resId = ch_array[j]
                    imageView.setImageResource(resId)
                    imageView.tag = resId
                    imageView.layoutParams = layoutParams

                    imageView.setOnClickListener{
                        //listener!!.click(this.id_array[j]);

                        var tag : String = resources.getResourceName((it.getTag() as Int)!!).toString()
                        var idx : Int = tag.indexOf("ch")
                        var parameterId : String = tag.substring(idx, tag.length)
                        listener!!.click(parameterId);

                    }

                    // 이미지를 row에 연결.
                    row.addView(imageView)
                }
                characterIndex = characterIndex + 4

                table3.addView(row)
            }
        }
    }

    public fun newInstant() : ThirdFragment
    {
        val args = Bundle()
        val frag = ThirdFragment()
        frag.arguments = args
        return frag
    }


}