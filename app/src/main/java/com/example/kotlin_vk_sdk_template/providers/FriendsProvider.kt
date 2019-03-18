package com.example.kotlin_vk_sdk_template.providers

import android.os.Handler
import com.example.kotlin_vk_sdk_template.models.FriendModel
import com.example.kotlin_vk_sdk_template.presenters.FriendsPresenter

class FriendsProvider(var presenter: FriendsPresenter) {

    fun testLoadFriends(hasFriends: Boolean) {

        Handler().postDelayed({
            val friendsList: ArrayList<FriendModel> = ArrayList()
            if (hasFriends) {
                val friend1 = FriendModel(_name = "Ivan", surname = "Petrov", city = null,
                    avatar = "https://ru.wikipedia.org/wiki/%D0%9F%D0%B5%D1%82%D1%80%D0%BE%D0%B2,_%D0%98%D0%B2%D0%B0%D0%BD_%D0%98%D0%B2%D0%B0%D0%BD%D0%BE%D0%B2%D0%B8%D1%87_(%D0%BF%D0%B5%D0%B2%D0%B5%D1%86)#/media/File:%D0%98%D0%B2%D0%B0%D0%BD_%D0%98%D0%B2%D0%B0%D0%BD%D0%BE%D0%B2%D0%B8%D1%87_%D0%9F%D0%B5%D1%82%D1%80%D0%BE%D0%B2_(%D0%BF%D0%B5%D0%B2%D0%B5%D1%86).jpg", isOnline = true)
                val friend2 = FriendModel(_name = "Krutaya", surname = "Telka", city = "Moscow",
                    avatar = "https://cdn.maximsfinest.com/38855bf752c53085a225f11615dc928d.jpg", isOnline = true)
                val friend3 = FriendModel(_name = "Evgeny", surname = "Veretennikov", city = "Spb",
                    avatar = "https://kazanreporter.ru/storage/web/cache/images/news/1/9e0e73254e15974285f1ae9b1eb6a69c1571c9a8.jpg", isOnline = true)

                friendsList.add(friend1)
                friendsList.add(friend2)
                friendsList.add(friend3)

            }
            presenter.friendsLoaded(friendsList = friendsList)
        }, 2000)
    }
}