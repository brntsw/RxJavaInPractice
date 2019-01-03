package bruno.com.br.rxexamples.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bruno.com.br.rxexamples.R
import bruno.com.br.rxexamples.model.RxTopic
import bruno.com.br.rxexamples.ui.IRxTopicClickListener
import kotlinx.android.synthetic.main.rx_topic_item.view.*

class RxAdapter(private val listRxTopics: List<RxTopic>?,
                private val rxTopicClickListener: IRxTopicClickListener?) : RecyclerView.Adapter<RxAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rx_topic_item, parent, false)
        return RxAdapter.ViewHolder(v)
    }

    override fun getItemCount(): Int = listRxTopics?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rxTopic = listRxTopics?.get(position)
        holder.bind(rxTopic)
        holder.layout.setOnClickListener {
            rxTopicClickListener?.onRxTopicClicked(rxTopic)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val layout = itemView.rx_topic_layout
        private val tvTitle = itemView.tv_title
        private val tvDetails = itemView.tv_details

        fun bind(rxTopic: RxTopic?){
            tvTitle.text = rxTopic?.title
            tvDetails.text = rxTopic?.details

            if(rxTopic?.details?.isEmpty() == true)
                tvDetails.visibility = View.GONE
        }

    }

}