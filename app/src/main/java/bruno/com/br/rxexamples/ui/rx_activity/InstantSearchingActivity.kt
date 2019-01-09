package bruno.com.br.rxexamples.ui.rx_activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import bruno.com.br.rxexamples.R
import bruno.com.br.rxexamples.ui.adapter.LogAdapter
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_instant_searching.*
import kotlinx.android.synthetic.main.content_instant_searching.*

class InstantSearchingActivity : AppCompatActivity() {

    private var adapter: LogAdapter? = null
    private var logs: List<String> = ArrayList<String>()

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instant_searching)
        setSupportActionBar(toolbar)

        init()
    }

    private fun init(){
        adapter = LogAdapter(this, ArrayList<String>())
        list_threading_log.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

}
