package com.wpfl5.chattutorial.binding


import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wpfl5.chattutorial.model.response.FbResponse
import com.wpfl5.chattutorial.model.response.UserResponse
import com.wpfl5.chattutorial.ui.adapter.FriendAdapter
import com.wpfl5.chattutorial.ui.base.BaseAdapter

object RecyclerBinding {

    @JvmStatic
    @BindingAdapter("setAdapter")
    fun setAdapter(view: RecyclerView, adapter: BaseAdapter<*,*>){
        view.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("userResponse")
    fun userResponse(view: RecyclerView, response: FbResponse<List<UserResponse>>){
        when(response){
            is FbResponse.Success -> {
                if(!response.data.isNullOrEmpty()){
                    (view.adapter as? FriendAdapter)?.submitList(response.data)
                }
            }
        }
    }


}