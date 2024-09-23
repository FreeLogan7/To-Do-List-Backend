package com.freedman.logan.todo

import com.freedman.logan.todo.data.ListItemRequest
import com.freedman.logan.todo.data.ListItemResponse
import org.springframework.stereotype.Component

@Component
class Duplicates {

    fun checkExists(items: List<ListItemResponse>, item: ListItemRequest): Boolean {
        return items.any { each ->
            each.title == item.title
                    && each.description == item.description
        }
    }
}