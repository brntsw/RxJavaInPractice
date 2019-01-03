package bruno.com.br.rxexamples.ui.rx_activity

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.ProgressBar
import bruno.com.br.rxexamples.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class BackgroundWorkActivity : AppCompatActivity() {

    lateinit var progress: ProgressBar
    lateinit var listView: ListView
    lateinit var btStart: Button

    private var adapter: LogAdapter? = null
    private var logs: MutableList<String>? = ArrayList<String>()
    private var disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_background_work)

        init()

        btStart.setOnClickListener {
            progress.visibility = View.VISIBLE
            log("Button clicked")

            val d = getDisposableObserver()

            getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(d)

            disposables.add(d)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    private fun init(){
        progress = findViewById(R.id.progress_operation_running)
        listView = findViewById(R.id.list_threading_log)
        btStart = findViewById(R.id.btn_start_operation)

        adapter = LogAdapter(this, ArrayList<String>())
        listView.adapter = adapter
    }

    private fun getObservable(): Observable<Boolean>{
        return Observable.just(true)
            .map {
                log("Within Observable")
                doSomeLongOperationThatBlocksCurrentThread()
                it
            }
    }

    /**
     * Observer that handles the result through the 3 important actions:
     *
     * <p>1. onCompleted 2. onError 3. onNext
     */
    private fun getDisposableObserver(): DisposableObserver<Boolean>{
        return object : DisposableObserver<Boolean>(){
            override fun onComplete() {
                log("On complete")
                progress.visibility = View.INVISIBLE;
            }

            override fun onError(e: Throwable) {
                Timber.e(e, "Error in RxJava Demo concurrency")
                log(String.format("Boo! Error %s", e.message))
                progress.visibility = View.INVISIBLE
            }

            override fun onNext(bool: Boolean) {
                log(String.format("onNext with return value \"%b\"", bool))
            }
        }
    }

    // -----------------------------------------------------------------------------------
    // Method that help wiring up the example (irrelevant to RxJava)
    private fun doSomeLongOperationThatBlocksCurrentThread(){
        log("performing long operation")

        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            Timber.d("Operation was interrupted")
        }

    }

    private fun log(logMsg: String){
        if(isCurrentlyOnMainThread()){
            logs?.add(0, "$logMsg (main thread) ")
            adapter?.clear()
            adapter?.addAll(logs)
        }
        else{
            logs?.add(0, "$logMsg (NOT main thread) ")

            Handler(Looper.getMainLooper())
                .post {
                    adapter?.clear()
                    adapter?.addAll(logs)
                }
        }
    }

    private fun isCurrentlyOnMainThread(): Boolean{
        return Looper.myLooper() == Looper.getMainLooper()
    }

    private inner class LogAdapter(context: Context, logs: List<String>) :
        ArrayAdapter<String>(context, R.layout.item_log, R.id.item_log, logs)

}
