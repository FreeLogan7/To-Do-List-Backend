package com.freedman.logan.todo

import com.freedman.logan.todo.data.ListItemRequest
import com.freedman.logan.todo.data.ListItemResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
class TodoItemController {

    //setting up a list of items
    private val items = mutableListOf<ListItemResponse>()

    //setting up getting all the list items
    @GetMapping("/")
    fun getAll(): List<ListItemResponse> = items


    //setting up adding items
    @PostMapping("/")
    fun addItem(
        @RequestBody item: ListItemRequest
    ):  List<ListItemResponse>{
        val newItem = ListItemResponse(
            id = UUID.randomUUID().toString(),
            title = item.title,
            description = item.description
        )
        items.add(newItem)
        return items
    }

    //update specific ID with new info
    @PutMapping("/{id}")
    fun putItem(
        @PathVariable
        id: String,
        @RequestBody
        payload: ListItemRequest
    ): ResponseEntity<Any> {
        val selected = items.find { it.id == id}
        if (selected == null){
            return ResponseEntity.badRequest().body(
               mapOf("error" to "Could not find id")
            )
        }
        selected.title = payload.title
        selected.description = payload.description

        return ResponseEntity.ok().body(items)
    }

    //delete specific ID
    @DeleteMapping("/{id}")
    fun deleteItem(
        @PathVariable
        id: String
    ): MutableList<ListItemResponse> {
        items.removeIf { it.id  == id}
        return items
    }
}