package bruno.com.br.rxexamples.ui.adapter

import android.content.Context
import android.widget.ArrayAdapter
import bruno.com.br.rxexamples.R

class LogAdapter(context: Context, logs: List<String>) :
    ArrayAdapter<String>(context, R.layout.item_log, R.id.item_log, logs)