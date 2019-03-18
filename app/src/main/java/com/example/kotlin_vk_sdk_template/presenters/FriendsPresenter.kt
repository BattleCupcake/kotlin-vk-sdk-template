package com.example.kotlin_vk_sdk_template.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.kotlin_vk_sdk_template.R
import com.example.kotlin_vk_sdk_template.models.FriendModel
import com.example.kotlin_vk_sdk_template.providers.FriendsProvider
import com.example.kotlin_vk_sdk_template.views.FriendsView
import com.example.kotlin_vk_sdk_template.views.LoginView

@InjectViewState
class FriendsPresenter: MvpPresenter<FriendsView>() {

    fun loadFriends(){
        viewState.startLoading()
        FriendsProvider(presenter = this).testLoadFriends(hasFriends = false)
    }

    fun friendsLoaded(friendsList: ArrayList<FriendModel>) {
        viewState.endLoading()
        if (friendsList.size == 0) {
            viewState.setupEmptyList()
            viewState.showError(textResource = R.string.no_friends_item)
        } else {
            viewState.setupFriendsList(friendsList = friendsList)
        }
    }
}