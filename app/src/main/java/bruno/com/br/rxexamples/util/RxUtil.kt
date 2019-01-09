package bruno.com.br.rxexamples.util

import android.os.Looper

class RxUtil {

    companion object {
        fun isCurrentlyOnMainThread(): Boolean {
            return Looper.myLooper() == Looper.getMainLooper()
        }
    }

}