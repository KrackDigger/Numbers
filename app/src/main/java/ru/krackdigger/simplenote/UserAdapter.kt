package ru.krackdigger.simplenote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.krackdigger.simplenote.databinding.ItemRowBinding
import ru.krackdigger.simplenote.databinding.MainFragmentBinding
import androidx.recyclerview.widget.DiffUtil

interface UserActionListener {

    fun onUserDelete(user: UserEntity)

}

class UsersDiffCallback(
        private val oldList: List<UserEntity>,
        private val newList: List<UserEntity>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser.id == newUser.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser == newUser
    }

}

class UserAdapter(private val actionListener: UserActionListener) :
        RecyclerView.Adapter<UserAdapter.UserHolder>(), View.OnClickListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private var users = mutableListOf<UserEntity>()

    override fun onClick(v: View) {

        val user = v.tag as UserEntity
        actionListener.onUserDelete(user)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val itemBinding = ItemRowBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)

        itemBinding.button.setOnClickListener(this)

        return UserHolder(itemBinding)
    }

    override fun onBindViewHolder(viewHolder: UserHolder, position: Int) {
        viewHolder.bind(users[position])

    }

    override fun getItemCount() = users.size

    fun refreshUsers(users: List<UserEntity>) {
        
        val diffCallback = UsersDiffCallback(this.users, users)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.users.clear()
        this.users.addAll(users)
        diffResult.dispatchUpdatesTo(this)
    }

    class UserHolder(private val itemBinding: ItemRowBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
                fun bind(user: UserEntity) = with(itemView) {
                    ("" + user.title).also { itemBinding.number.text = it }
                    itemBinding.button.tag = user
    }
    }
}