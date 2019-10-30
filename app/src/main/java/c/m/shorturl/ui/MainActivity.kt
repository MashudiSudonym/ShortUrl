package c.m.shorturl.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import c.m.shorturl.R
import c.m.shorturl.util.StringWithTag
import c.m.shorturl.webservice.ApiInterface
import c.m.shorturl.webservice.RetrofitService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var spinnerAdapter: ArrayAdapter<StringWithTag>? = null
    private var keyStringWithTag: String? = ""
    private var valueStringWithTag: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerSetup()

        btn_shorturl.setOnClickListener {
            // Do process here after button clicked
            // Show Loading Status
            tv_result.text = getString(R.string.loading_status)
            val getLongUrl = edt_write_url.text.toString() // get long url from edit text
            // Initiate retrofit service
            val apiService = RetrofitService.getInstance(valueStringWithTag.toString())
                .create(ApiInterface::class.java)

            // running process in AsyncTask
            GlobalScope.launch(Dispatchers.IO) {
                // this code for request short url to API
                val result = apiService.createShortUrl("json", getLongUrl).shorturl

                // send result to text view
                tv_result.text = result
            }
        }
    }

    // Initiate Setup for Spinner
    private fun spinnerSetup() {
        val shortUrlHostList = mapOf(
            "IS.GD" to "https://is.gd/",
            "V.GD" to "https://v.gd/"
        )

        val spinnerItemAdapter = ArrayList<StringWithTag>()

        shortUrlHostList.forEach {
            val value = it.value
            val key = it.key
            spinnerItemAdapter.add(StringWithTag(value, key))
        }

        spinnerAdapter = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            spinnerItemAdapter
        )

        spinner_select_shorturl.adapter = spinnerAdapter
        spinner_select_shorturl.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val stringWithTag = parent?.getItemAtPosition(position) as StringWithTag
                    valueStringWithTag = stringWithTag.value.toString()
                    keyStringWithTag = stringWithTag.key
                }
            }
    }
}
