package pt.atp.bobi.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pt.atp.bobi.data.model.Message
import pt.atp.bobi.databinding.ItemMessageIncomingBinding
import pt.atp.bobi.databinding.ItemMessageOutgoingBinding

class ChatAdapter : ListAdapter<Message, ChatAdapter.MessagesViewHolder>(DiffCallback()) {

    private lateinit var bindingOutgoing: ItemMessageOutgoingBinding
    private lateinit var bindingIncoming: ItemMessageIncomingBinding

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MessagesViewHolder {
        if (viewType == 0) {
            bindingOutgoing = ItemMessageOutgoingBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            return MessagesViewHolder(bindingOutgoing)
        }

        bindingIncoming = ItemMessageIncomingBinding.inflate(LayoutInflater.from(viewGroup.context))
        return MessagesViewHolder(bindingIncoming)
    }

    override fun onBindViewHolder(viewHolder: MessagesViewHolder, position: Int) {
        val message = getItem(position)
        viewHolder.bind(message)
    }

    override fun getItemId(position: Int): Long {
        return currentList[position].timestamp.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return if(currentList[position].outgoing) {
            0
        } else {
            1
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Message>() {

        override fun areItemsTheSame(oldItem: Message, newItem: Message) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Message, newItem: Message) =
            oldItem == newItem
    }

    class MessagesViewHolder: RecyclerView.ViewHolder {

        private var bindingOutgoing: ItemMessageOutgoingBinding? = null
        private var bindingIncoming: ItemMessageIncomingBinding? = null

        constructor(itemBinding: ItemMessageOutgoingBinding) : super(itemBinding.root) {
            bindingOutgoing = itemBinding
        }

        constructor(itemBinding: ItemMessageIncomingBinding) : super(itemBinding.root) {
            bindingIncoming = itemBinding
        }

        fun bind(message: Message) {
            if (message.outgoing) {
                bindingOutgoing?.tvUser?.text = message.user
                bindingOutgoing?.tvContent?.text = message.content

            } else {
                bindingIncoming?.tvUser?.text = message.user
                bindingIncoming?.tvContent?.text = message.content
            }
        }
    }
}