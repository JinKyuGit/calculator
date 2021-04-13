package com.app.calculator

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow

class SecondFragment : Fragment() {

    public val ch_array = arrayOf(
        R.drawable.ch_1,
        R.drawable.ch_5,
        R.drawable.ch_8,
        R.drawable.ch_11,
        R.drawable.ch_17,
        R.drawable.ch_23,
        R.drawable.ch_24,
        R.drawable.ch_25,
        R.drawable.ch_26,
        R.drawable.ch_28,
        R.drawable.ch_29,
        R.drawable.ch_36,
        R.drawable.ch_37,
        R.drawable.ch_38,
        R.drawable.ch_39,
        R.drawable.ch_46,
        R.drawable.ch_47,
        R.drawable.ch_51,
        R.drawable.ch_57,
        R.drawable.ch_58,
        R.drawable.ch_60,
        R.drawable.ch_65,
        R.drawable.ch_66,
        R.drawable.ch_69,
        R.drawable.ch_71,
        R.drawable.ch_84,
        R.drawable.ch_88,
        R.drawable.ch_90
    )

    var listener: EventListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
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
            val table2 : TableLayout = it.findViewById(R.id.table2)


            val layoutParams = TableRow.LayoutParams(80.topx(), 80.topx())
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

                table2.addView(row)
            }
        }
    }

    public fun newInstant() : SecondFragment
    {
        val args = Bundle()
        val frag = SecondFragment()
        frag.arguments = args
        return frag
    }




}