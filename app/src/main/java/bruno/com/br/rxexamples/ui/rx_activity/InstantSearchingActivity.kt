package bruno.com.br.rxexamples.ui.rx_activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import bruno.com.br.rxexamples.R
import bruno.com.br.rxexamples.ui.adapter.LogAdapter
import bruno.com.br.rxexamples.util.RxAdapterUtil
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.activity_instant_searching.*
import kotlinx.android.synthetic.main.content_instant_searching.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

class InstantSearchingActivity : AppCompatActivity() {

    private var adapter: LogAdapter? = null
    private var logs: MutableList<String> = ArrayList<String>()

    private var disposable: Disposable? = null
    private var adapterUtil: RxAdapterUtil? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instant_searching)
        setSupportActionBar(toolbar)

        init()

        clr_debounce.setOnClickListener {
            logs = ArrayList<String>()
            adapter?.clear()
        }

        disposable = RxTextView.textChangeEvents(input_txt_debounce)
            .debounce(400, TimeUnit.MILLISECONDS)
            .filter{it.text().toString().isNotEmpty()}
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(getSearchObserver())
    }

    private fun init(){
        adapter = LogAdapter(this, ArrayList<String>())
        list_threading_log.adapter = adapter

        adapterUtil = RxAdapterUtil(logs, adapter)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    private fun getSearchObserver(): DisposableObserver<TextViewTextChangeEvent> {
        return object : DisposableObserver<TextViewTextChangeEvent>(){
            override fun onComplete() {
                Timber.d("----------- onCompleted")
            }

            override fun onNext(t: TextViewTextChangeEvent) {
                adapterUtil?.log(String.format("Searching for %s", t.text().toString()))
            }

            override fun onError(e: Throwable) {
                Timber.e(e, "Woops on error!")
                adapterUtil?.log("Dang error, check your logs")
            }

        }
    }

}
