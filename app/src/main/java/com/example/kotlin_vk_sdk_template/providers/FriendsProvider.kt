package com.example.kotlin_vk_sdk_template.providers

import android.os.Handler
import android.util.Log
import com.example.kotlin_vk_sdk_template.R
import com.example.kotlin_vk_sdk_template.models.FriendModel
import com.example.kotlin_vk_sdk_template.presenters.FriendsPresenter
import com.google.gson.JsonParser
import com.vk.sdk.api.*

class FriendsProvider(var presenter: FriendsPresenter) {

    private val TAG: String = FriendsProvider::class.java.simpleName

    fun testLoadFriends(hasFriends: Boolean) {

        Handler().postDelayed({
            val friendsList: ArrayList<FriendModel> = ArrayList()
            if (hasFriends) {
                val friend1 = FriendModel(_name = "Ivan", surname = "Ivanov", city = null,
                    avatar = "https://makiyazhglaz.com/sites/default/files/makiyazh-glaz/0-1/56412-1390742581.jpg", isOnline = true)
                val friend2 = FriendModel(_name = "Evlampiya", surname = "Evlampova", city = "Moscow",
                    avatar = "https://cdn.maximsfinest.com/38855bf752c53085a225f11615dc928d.jpg", isOnline = true)
                val friend3 = FriendModel(_name = "Petr", surname = "Petrov", city = "Spb",
                    avatar = "https://24smi.org/public/media/resize/660x-/celebrity/2016/12/08/fSIZoLTXfTS2_petr-i.jpg", isOnline = true)

                friendsList.add(friend1)
                friendsList.add(friend2)
                friendsList.add(friend3)

            }
            presenter.friendsLoaded(friendsList = friendsList)
        }, 2000)
    }

    fun loadFriends () {
        val request = VKApi.friends().get(VKParameters.from(VKApiConst.COUNT, 9000, VKApiConst.FIELDS, "sex, bdate, city, country, photo_100, online"))
        request.executeWithListener(object : VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse?) {
                super.onComplete(response)

                val jsonParser = JsonParser()
                val parsedJson = jsonParser.parse(response?.json.toString()).asJsonObject
                val friendsList: ArrayList<FriendModel> = ArrayList()

                parsedJson.get("response").asJsonObject.getAsJsonArray("items").forEach {
                    val city = if (it.asJsonObject.get("city") == null) {
                        null
                    } else {
                        it.asJsonObject.get("city").asJsonObject.get("title").asString
                    }
                    val friend = FriendModel (
                        _name = it.asJsonObject.get("first_name").asString,
                        surname = it.asJsonObject.get("last_name").asString,
                        city = city,
                        avatar = it.asJsonObject.get("photo_100").asString,
                        isOnline = it.asJsonObject.get("online").asInt ==1)
                    friendsList.add(friend)
                }
                presenter.friendsLoaded(friendsList = friendsList)

            }

            override fun onError(error: VKError?) {
                super.onError(error)
                presenter.showError(textResource = R.string.friends_error_loading)
            }
        })
    }
}