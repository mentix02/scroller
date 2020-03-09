package com.mentix02.scroller.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import com.mentix02.scroller.R
import com.mentix02.scroller.models.Thought
import com.mentix02.scroller.ui.ThoughtsAdapter
import com.mentix02.scroller.network.ThoughtsAPI
import kotlinx.android.synthetic.main.thoughts_fragment.*

class ThoughtsFragment : Fragment() {

    companion object {
        fun newInstance() = ThoughtsFragment()
    }

    private lateinit var viewModel: ThoughtsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.thoughts_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ThoughtsViewModel::class.java)

        refreshLayout.setOnRefreshListener {
            fetchThoughts()
        }

        fetchThoughts()
    }

    private fun fetchThoughts() {

        refreshLayout.isRefreshing = true

        ThoughtsAPI()
            .getThoughts().enqueue(object: Callback<List<Thought>> {

                override fun onFailure(call: Call<List<Thought>>, t: Throwable) {
                    refreshLayout.isRefreshing = false
                    Toast.makeText(activity, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<List<Thought>>, response: Response<List<Thought>>) {
                    refreshLayout.isRefreshing = false
                    val thoughts = response.body()
                    thoughts?.let {
                        showThoughts(it)
                    }
                }

            })

    }

    private fun showThoughts(thoughts: List<Thought>) {
        recyclerViewThoughts.layoutManager = LinearLayoutManager(activity)
        recyclerViewThoughts.adapter = ThoughtsAdapter(thoughts)
    }

}
