package com.example.kotlin_vk_sdk_template.activities

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.kotlin_vk_sdk_template.R
import com.example.kotlin_vk_sdk_template.adapters.FriendsAdapter
import com.example.kotlin_vk_sdk_template.models.FriendModel
import com.example.kotlin_vk_sdk_template.presenters.FriendsPresenter
import com.example.kotlin_vk_sdk_template.views.FriendsView
import com.github.rahatarmanahmed.cpv.CircularProgressView

class FriendsActivity : MvpAppCompatActivity(), FriendsView {

    @InjectPresenter
    lateinit var friendsPresenter: FriendsPresenter

    private lateinit var mRvFriends: RecyclerView

    private lateinit var mTxtNoItems: TextView

    private lateinit var mCvrWait: CircularProgressView

    private lateinit var mAdapter: FriendsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        mRvFriends = findViewById(R.id.recycler_friends)
        mTxtNoItems = findViewById(R.id.txt_friends_no_items)
        mCvrWait = findViewById(R.id.cpv_friends)

        val mTxtSearch: EditText = findViewById(R.id.txt_friends_search)
        mTxtSearch.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mAdapter.filter(s.toString())
            }

        })

        friendsPresenter.loadFriends()
        mAdapter = FriendsAdapter()

        mRvFriends.adapter = mAdapter
        mRvFriends.layoutManager = LinearLayoutManager(applicationContext, OrientationHelper.VERTICAL, false)
        mRvFriends.setHasFixedSize(true)
    }


    //Friens view implementation
    override fun showError(textResource: Int) {
        mTxtNoItems.text = getString(textResource)
    }

    override fun setupEmptyList() {
        mRvFriends.visibility = View.GONE
        mTxtNoItems.visibility = View.VISIBLE
    }

    override fun setupFriendsList(friendsList: ArrayList<FriendModel>) {
        mRvFriends.visibility = View.VISIBLE
        mTxtNoItems.visibility = View.GONE

        mAdapter.setupFriends(friendList = friendsList)
    }

    override fun startLoading() {
        mRvFriends.visibility = View.GONE
        mTxtNoItems.visibility = View.GONE
        mCvrWait.visibility = View.VISIBLE
    }

    override fun endLoading() {
        mCvrWait.visibility = View.GONE
    }
}
