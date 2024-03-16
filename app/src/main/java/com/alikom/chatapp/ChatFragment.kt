package com.alikom.chatapp

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChatFragment : Fragment(), ChatAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)



        // Создание адаптера и присоединение его к RecyclerView
        adapter = ChatAdapter(data.toMutableList(), this)
        recyclerView.adapter = adapter

        return view
    }

    // Подготовка данных для отображения
    private val data = listOf(
        ChatModel(R.drawable.ic_bryin,"Bryan", "What do you think?"),
        ChatModel(R.drawable.ic_bryin,"Bryan", "What do you think?"),
        ChatModel(R.drawable.ic_kari,"Kari", "Looks great!"),
        ChatModel(R.drawable.ic_diana,"Diana", "Lunch on Monday?"),
        ChatModel(R.drawable.ic_ben,"Ben", "You sent a photo."),
        ChatModel(R.drawable.ic_naomi,"Naomi", "Naomi sent a photo."),
        ChatModel(R.drawable.ic_alicia,"Alicia", "See you at 8."),
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter
        adapter.updateItems(data)
        view.findViewById<EditText>(R.id.searchEditText).apply {
            background = null
            doAfterTextChanged {
                filterChats(it)
            }
        }
    }

    private fun filterChats(query: Editable?) {
        query?.let {
            if (it.isNotEmpty()) {
                val searchQuery = query.toString().lowercase()
                adapter.updateItems(data.filter {
                    it.title.lowercase().contains(searchQuery) ||
                            it.lastMessage.lowercase().contains(searchQuery)
                })
            } else adapter.updateItems(data)
        }
    }

    override fun onItemClick(item: String) {
        Toast.makeText(activity, "Clicked on: $item", Toast.LENGTH_SHORT).show()

        // Переход на другой фрагмент
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, OtherFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onChatItemClick(message: String) {
        (activity as? MainActivity)?.showAlertDialog()
    }

    override fun onItemDeleteClick(position: Int) {
        adapter.removeItem(position)
    }
}
