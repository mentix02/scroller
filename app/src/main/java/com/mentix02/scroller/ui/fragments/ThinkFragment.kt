package com.mentix02.scroller.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest

import com.mentix02.scroller.R
import kotlinx.android.synthetic.main.fragment_think.*
import org.json.JSONObject
import java.lang.Exception

class ThinkFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_think, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        thinkThoughtButton.setOnClickListener{
            val thoughtBodyText = thoughtBody.text
            val url = "http://169.233.147.48:8000/thoughts/new/"

            val params = HashMap<String,String>()
            params["body"] = thoughtBodyText.toString()
            params["token"] = "7f02ea07cff14b33387264c363062748101304797fef4b3f2e070effd255cf3c"
            val jsonObject = JSONObject(params as Map<*, *>)

            val request = JsonObjectRequest(Request.Method.POST, url, jsonObject,
                Response.Listener { response ->
                    try {
                        println(response.toString())
                        thoughtBody.setText("")
                        Toast.makeText(activity, "Thought 'thunk' successfully.", Toast.LENGTH_LONG).show()
                    } catch ( exception : Exception ) {
                        Toast.makeText(activity, "An error occured $exception", Toast.LENGTH_LONG).show()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(activity, "Error : $it", Toast.LENGTH_LONG).show()
                })

            request.retryPolicy = DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                // 0 means no retry
                0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
                1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )

            VolleySingleton.getInstance(activity!!.applicationContext).addToRequestQueue(request)

        }

    }

}
