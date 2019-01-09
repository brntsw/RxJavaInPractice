package bruno.com.br.rxexamples.util

import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter

class RxAdapterUtil(private var logs: MutableList<String>?, private var adapter: ArrayAdapter<String>?) {

    fun log(logMsg: String){
        if(RxUtil.isCurrentlyOnMainThread()){
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

}