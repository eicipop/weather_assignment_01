package com.example.weather_assignment_01.`interface`

interface RecyclerViewListener {
    fun onClick(position: Int, item: Any)
    fun onClickItemForViewId(position: Int, item: Any, viewId: Int)
}