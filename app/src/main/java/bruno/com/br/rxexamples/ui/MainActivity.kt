package bruno.com.br.rxexamples.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import bruno.com.br.rxexamples.R
import bruno.com.br.rxexamples.model.RxTopic
import bruno.com.br.rxexamples.presentation.RxTopicsContract
import bruno.com.br.rxexamples.presentation.RxTopicsPresenter
import bruno.com.br.rxexamples.ui.adapter.RxAdapter

class MainActivity : AppCompatActivity(), IRxTopicClickListener {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: RxAdapter
    lateinit var rxTopicsPresenter: RxTopicsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setup()

        adapter = RxAdapter(rxTopicsPresenter.getLocalRxTopics(), this)
        recyclerView.adapter = adapter
    }

    fun setup(){
        rxTopicsPresenter = RxTopicsPresenter()

        recyclerView = findViewById(R.id.recycler_rx)

        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onRxTopicClicked(rxTopic: RxTopic?) {
        rxTopicsPresenter.rxTopicClick(rxTopic)
    }
}
