package bruno.com.br.rxexamples.presentation

import android.content.Intent
import android.util.Log
import bruno.com.br.rxexamples.App
import bruno.com.br.rxexamples.model.RxTopic
import bruno.com.br.rxexamples.ui.rx_activity.AccumulateCallsActivity
import bruno.com.br.rxexamples.ui.rx_activity.BackgroundWorkActivity
import bruno.com.br.rxexamples.ui.rx_activity.InstantSearchingActivity
import bruno.com.br.rxexamples.ui.rx_activity.RetrofitActivity
import timber.log.Timber
import java.util.*

class RxTopicsPresenter : RxTopicsContract.Presenter {

    override fun rxTopicClick(rxTopic: RxTopic?) {
        Timber.d("Rx topic: %s", rxTopic?.title)

        val intent = when(rxTopic?.id){
            1 -> Intent(App.getContext(), BackgroundWorkActivity::class.java)
            2 -> Intent(App.getContext(), AccumulateCallsActivity::class.java)
            3 -> Intent(App.getContext(), InstantSearchingActivity::class.java)
            4 -> Intent(App.getContext(), RetrofitActivity::class.java)
            else -> Intent(App.getContext(), BackgroundWorkActivity::class.java)
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        App.getContext()?.startActivity(intent)
    }

    override fun getLocalRxTopics(): List<RxTopic> {
        return Arrays.asList(
            RxTopic(1,"Background work & concurrency", "using Schedulers"),
            RxTopic(2,"Accumulate calls", "using buffer"),
            RxTopic(3,"Instant/Auto searching text listeners", "using Subjects & debounce"),
            RxTopic(4,"Networking with Retrofit & RxJava", "using zip, flatmap"),
            RxTopic(5,"Two-way data binding for TextViews", "using PublishSubject"),
            RxTopic(6,"Simple and Advanced polling", "using interval and repeatWhen"),
            RxTopic(7,"Simple and Advanced exponential backoff", "using delay and retryWhen"),
            RxTopic(8,"Form validation", "using combineLatest"),
            RxTopic(9,"Pseudo caching : retrieve data first from a cache, then a network call", "using concat, concatEager, merge or publish"),
            RxTopic(10,"Simple timing demos", "using timer, interval or delay"),
            RxTopic(11,"RxBus : event bus using RxJava", "using RxRelay (never terminating Subjects) and debouncedBuffer"),
            RxTopic(12,"Persist data on Activity rotations", "using Subjects and retained Fragments"),
            RxTopic(13,"Networking with Volley", ""),
            RxTopic(14,"Pagination with Rx", "using Subjects"),
            RxTopic(15,"Orchestrating Observable: make parallel network calls, then combine the result into a single data point", "using flatmap & zip"),
            RxTopic(16,"Simple Timeout example", "using timeout"),
            RxTopic(17,"Setup and teardown resources", "using using"),
            RxTopic(18,"Multicast playground", "")
        )
    }
}