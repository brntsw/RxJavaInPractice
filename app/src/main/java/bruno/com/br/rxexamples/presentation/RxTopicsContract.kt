package bruno.com.br.rxexamples.presentation

import bruno.com.br.rxexamples.model.RxTopic
import java.util.*

interface RxTopicsContract {

    interface Presenter {
        fun rxTopicClick(rxTopic: RxTopic?)
        fun getLocalRxTopics(): List<RxTopic>
    }

}