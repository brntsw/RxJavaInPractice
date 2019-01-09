package bruno.com.br.rxexamples.ui.rx_activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import bruno.com.br.rxexamples.R
import bruno.com.br.rxexamples.ui.adapter.LogAdapter
import bruno.com.br.rxexamples.util.RxAdapterUtil
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import timber.log.Timber
import java.util.concurrent.TimeUnit

class AccumulateCallsActivity : AppCompatActivity() {

    private lateinit var tapBtn: Button
    private lateinit var list: ListView
    private lateinit var disposable: Disposable

    private var adapter: LogAdapter? = null
    private var logs: MutableList<String>? = null
    private var adapterUtil: RxAdapterUtil? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accumulate_calls)

        init()
    }

    override fun onResume() {
        super.onResume()
        disposable = getBufferedDisposable()
    }

    override fun onPause() {
        super.onPause()
        disposable.dispose()
    }

    private fun init(){
        tapBtn = findViewById(R.id.btn_start_operation)
        list = findViewById(R.id.list_threading_log)

        logs = ArrayList<String>()
        adapter = LogAdapter(this, ArrayList<String>())
        list.adapter = adapter

        adapterUtil = RxAdapterUtil(logs, adapter)
    }

    private fun getBufferedDisposable(): Disposable{
        return RxView.clicks(tapBtn)
            .map {
                Timber.d("--------- GOT A TAP")
                adapterUtil?.log("GOT A TAP")
                1
            }
            .buffer(2, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<Int>>(){
                override fun onComplete() {
                    Timber.d("----- onCompleted")
                }

                override fun onNext(integers: List<Int>) {
                    Timber.d("--------- onNext")
                    if (integers.isNotEmpty()) {
                        adapterUtil?.log(String.format("%d taps", integers.size))
                    } else {
                        Timber.d("--------- No taps received ")
                    }
                }

                override fun onError(e: Throwable) {
                    Timber.e(e, "--------- Woops on error!")
                }

            })
    }
}
