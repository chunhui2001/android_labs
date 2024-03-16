package com.android_labs.cryptocurrencyapp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.majorik.sparklinelibrary.SparkLineLayout
import java.text.DecimalFormat

class BalanceListFragment : Fragment() {

    companion object {
        fun newInstance(): BalanceListFragment {
            return BalanceListFragment()
        }
    }

    private lateinit var listView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_balance_list, container, false)

        this.listView = view.findViewById(R.id.list_view)
        this.listView.layoutManager = LinearLayoutManager(context)

        this.listView.adapter = BalanceListAdapter(
            listOf(
                Token("ethereum", "ETH", 1435.0, 1.0, listOf(1, 3, 3, 6,2,7,8), 1.0123, 1.2),
                Token("bitcoin", "BTC", 1435.0, 1.0, listOf(1, 3, 3), 0.0, 1.2),
                Token("ethereum", "USDT", 1435.0, -0.65, listOf(1, 3, 3), 0.0, 1.2),
                Token("trox", "TRX", 1435.0, 1.0, listOf(1, 3, 3), 0.0, 1.2),
                Token("ethereum", "MATIC", 1435.0, 1.0, listOf(1, 3, 3), 0.0, 1.2),
                Token("ethereum", "BNB", 1435.0, 1.0, listOf(1, 3, 3), 0.0, 1.2),
                Token("ethereum", "FIL", 1435.0, 1.0, listOf(1, 3, 3), 0.0, 1.2),
            )
        )

        return view
    }

    private inner class BalanceListHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private var tokenSymbolTxt: TextView = view.findViewById(R.id.textView13)
        private var tokenPriceTxt: TextView = view.findViewById(R.id.textView14)
        private var tokenPercentTxt: TextView = view.findViewById(R.id.textView15)
        private var tokenAmountTxt: TextView = view.findViewById(R.id.textView16)
        private var tokenSizeTxt: TextView = view.findViewById(R.id.textView17)

        private var tokenIcon: ImageView = view.findViewById(R.id.imageView4)
        private var lineChat: SparkLineLayout = view.findViewById(R.id.line_data)

        private val decimalFormatter = DecimalFormat("###,###,###,###.##")

        fun bind(token: Token) {
            this.tokenSymbolTxt.text = token.symbol.uppercase()
            this.tokenPriceTxt.text = decimalFormatter.format(token.price)
            this.tokenAmountTxt.text = "$" + decimalFormatter.format(token.amount)
            this.tokenPercentTxt.text = token.changePercent.toString() + "%"
            this.tokenSizeTxt.text = token.size.toString() + "" + token.symbol.uppercase()
            this.lineChat.setData(ArrayList(token.lineData))

            if (token.changePercent > 0) {
                this.tokenPercentTxt.setTextColor(Color.parseColor("#12c737"))
                this.lineChat.sparkLineColor = Color.parseColor("#12c737")
            } else if (token.changePercent < 0) {
                this.tokenPercentTxt.setTextColor(Color.parseColor("#fc0000"))
                this.lineChat.sparkLineColor = Color.parseColor("#fc0000")
            } else {
                this.tokenPercentTxt.setTextColor(Color.parseColor("#ffffff"))
                this.lineChat.sparkLineColor = Color.parseColor("#ffffff")
            }

            var resourceId = view.context.resources.getIdentifier(token.logo, "drawable", view.context.packageName)
            Glide.with(view.context).load(resourceId).into(tokenIcon)

        }
    }

    private inner class BalanceListAdapter(val balanceList: List<Token>) :
        RecyclerView.Adapter<BalanceListHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceListHolder {
            return BalanceListHolder(
                layoutInflater.inflate(
                    R.layout.view_holder_balance_list,
                    parent,
                    false
                )
            )
        }

        override fun getItemCount(): Int {
            return balanceList.size
        }

        override fun onBindViewHolder(holder: BalanceListHolder, position: Int) {
            holder.bind(balanceList[position])

//            holder.itemView.context.resources.getIdentifier("trox", "drawable", holder.itemView.context.packageName)
        }
    }
}