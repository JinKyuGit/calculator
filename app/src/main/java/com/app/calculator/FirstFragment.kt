package com.app.calculator

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.fragment.app.Fragment


class FirstFragment : Fragment() {


    public val ch_array = arrayOf(
        R.drawable.ch_4,
        R.drawable.ch_6,
        R.drawable.ch_7,
        R.drawable.ch_9,
        R.drawable.ch_10,
        R.drawable.ch_14,
        R.drawable.ch_16,
        R.drawable.ch_18,
        R.drawable.ch_19,
        R.drawable.ch_20,
        R.drawable.ch_27,
        R.drawable.ch_33,
        R.drawable.ch_34,
        R.drawable.ch_35,
        R.drawable.ch_50,
        R.drawable.ch_53,
        R.drawable.ch_54,
        R.drawable.ch_59,
        R.drawable.ch_62,
        R.drawable.ch_63,
        R.drawable.ch_72,
        R.drawable.ch_75,
        R.drawable.ch_77,
        R.drawable.ch_82,
        R.drawable.ch_83,
        R.drawable.ch_89,
        R.drawable.ch_93,
        R.drawable.ch_94,
        R.drawable.ch_95,
        R.drawable.ch_96,
        R.drawable.ch_97,
        R.drawable.ch_98,
        R.drawable.ch_99,
        R.drawable.ch_100,
        R.drawable.ch_101,
        R.drawable.ch_102,
        R.drawable.ch_103,
        R.drawable.ch_104,
        R.drawable.ch_105,
        R.drawable.ch_107,
        R.drawable.ch_108,
        R.drawable.ch_109
    )

    var listener: EventListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
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
            val table1 : TableLayout = it.findViewById(R.id.table1)

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

                table1.addView(row)
            }
        }
    }

    public fun newInstant() : FirstFragment
    {
        val args = Bundle()
        val frag = FirstFragment()
        frag.arguments = args
        return frag
    }


}