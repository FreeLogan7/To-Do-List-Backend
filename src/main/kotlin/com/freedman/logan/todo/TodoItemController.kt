package com.freedman.logan.todo

import com.freedman.logan.todo.data.ListItemRequest
import com.freedman.logan.todo.data.ListItemResponse
import com.freedman.logan.todo.data.errors.DuplicateItemException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
class TodoItemController(
    val duplicates: Duplicates,
    val items: MutableList<ListItemResponse>
) {

    //setting up getting all the list items
    @GetMapping("/")
    fun getAll(): List<ListItemResponse> = items

    //Get - differing use of PathVariable, filtering by title
    @GetMapping("/title/{title}")
    fun random(@PathVariable title: String): Any {
        // Filter the items based on the title
        return items.filter { it.title == title }
    }

    //Get a singular list item by ID
    @GetMapping("/id/{id}")
    fun getOne(
        @PathVariable
        id: String
    ): ResponseEntity<Any> {
        val selected = items.find { it.id == id }
        if (selected == null){
            return ResponseEntity.badRequest().body(
                mapOf("error" to "Could not find id"))
        }
        return ResponseEntity.ok().body(selected)
    }

    //setting up adding items
    @PostMapping("/")
    fun addItem(
        @RequestBody item: ListItemRequest
    ):  List<ListItemResponse>{
        if (duplicates.checkExists(items, item)){
            throw DuplicateItemException("Item already exists")
        }
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
    fun deleteItem(@PathVariable id: String): MutableList<ListItemResponse> {
        items.removeIf { it.id  == id}
        return items
        // -> return FUNCTION userSERVICE.function(id)  --> Items will be moved out of class
    }
}

//move business logic into a service, controller -> Service -> Repository
//Business Logic - todo SERVICE
// SERVICE interacts with REPOSITORY class
//REPOSITORY INTERACTS WITH THE DATABASE
//Create Sign in page - is an example