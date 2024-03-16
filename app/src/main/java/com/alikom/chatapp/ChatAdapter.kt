package com.alikom.chatapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val dataSet: MutableList<ChatModel>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    private val items = ArrayList<ChatModel>()

    fun updateItems(items: List<ChatModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(item: String)
        fun onChatItemClick(message: String)
        fun onItemDeleteClick(position: Int)
    }

    fun removeItem(position: Int) {
        dataSet.removeAt(position)
        notifyItemRemoved(position)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        private val lastMessageTextView = itemView.findViewById<TextView>(R.id.lastMessageTextView)
        private val avatarImg: ImageView = view.findViewById(R.id.imageView)
//        val deleteButton: TextView = view.findViewById(R.id.deleteTextView)

        fun bind(item: ChatModel) {
            titleTextView.text = item.title
            lastMessageTextView.text = item.lastMessage
            avatarImg.setImageResource(item.avatar)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = dataSet[position]
        holder.bind(items[position])
        holder.itemView.setOnClickListener{
            listener.onItemClick(message.toString())
        }
        holder.itemView.setOnLongClickListener{
            listener.onChatItemClick(message.toString())
            true
        }
//        holder.deleteButton.setOnClickListener {
//            if (position != RecyclerView.NO_POSITION) {
//                listener.onItemDeleteClick(position)
//            }
//        }
    }


    override fun getItemCount() = dataSet.size

}
