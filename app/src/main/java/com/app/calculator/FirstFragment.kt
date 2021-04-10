package com.app.calculator

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.fragment.app.Fragment
import java.util.*


class FirstFragment : Fragment() {

    public val first_row_array = arrayOf(
        R.id.first_row1,
        R.id.first_row2,
        R.id.first_row3,
        R.id.first_row4,
        R.id.first_row5,
        R.id.first_row6,
        R.id.first_row7,
        R.id.first_row8,
        R.id.first_row9,
        R.id.first_row10
    )

    public val ch_array = arrayOf(
        R.id.ch_4,
        R.id.ch_6,
        R.id.ch_7,
        R.id.ch_9,
        R.id.ch_10,
        R.id.ch_14,
        R.id.ch_16,
        R.id.ch_18,
        R.id.ch_19,
        R.id.ch_20,
        R.id.ch_27,
        R.id.ch_33,
        R.id.ch_34,
        R.id.ch_35,
        R.id.ch_50,
        R.id.ch_53,
        R.id.ch_54,
        R.id.ch_59,
        R.id.ch_62,
        R.id.ch_63,
        R.id.ch_72,
        R.id.ch_75,
        R.id.ch_77,
        R.id.ch_82,
        R.id.ch_83,
        R.id.ch_89,
        R.id.ch_93,
        R.id.ch_94,
        R.id.ch_95,
        R.id.ch_96,
        R.id.ch_97,
        R.id.ch_98,
        R.id.ch_99,
        R.id.ch_100,
        R.id.ch_101,
        R.id.ch_102,
        R.id.ch_103,
        R.id.ch_105,
        R.id.ch_107,
        R.id.ch_108
    )

    public val first_image_array = arrayOf(
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
        R.drawable.ch_105,
        R.drawable.ch_107,
        R.drawable.ch_108
    )

    public val id_array = arrayOf(
        "ch_4",
        "ch_6",
        "ch_7",
        "ch_9",
        "ch_10",
        "ch_14",
        "ch_16",
        "ch_18",
        "ch_19",
        "ch_20",
        "ch_27",
        "ch_33",
        "ch_34",
        "ch_35",
        "ch_50",
        "ch_53",
        "ch_54",
        "ch_59",
        "ch_62",
        "ch_63",
        "ch_72",
        "ch_75",
        "ch_77",
        "ch_82",
        "ch_83",
        "ch_89",
        "ch_93",
        "ch_94",
        "ch_95",
        "ch_96",
        "ch_97",
        "ch_98",
        "ch_99",
        "ch_100",
        "ch_101",
        "ch_102",
        "ch_103",
        "ch_105",
        "ch_107",
        "ch_108"
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
                row.id = this.first_row_array[i]

                //4개씩 반복.
                for(j in characterIndex .. characterIndex+3){
                    //이미지 view 생성 및 이미지 연결.
                    var imageView = ImageView(it)
                    imageView.id = this.ch_array[j]
                    var resId = first_image_array[j]
                    imageView.setImageResource(resId)
                    imageView.layoutParams = layoutParams

                    imageView.setOnClickListener{
                        listener!!.click(this.id_array[j]);
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