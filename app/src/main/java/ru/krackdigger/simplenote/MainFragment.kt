package ru.krackdigger.simplenote

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import ru.krackdigger.simplenote.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        @Suppress("DEPRECATION")
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val adapter = UserAdapter(object : UserActionListener {

            override fun onUserDelete(user: UserEntity) {

                val number: Int = user.title
                viewModel.delete(user)
                viewModel.insertPool(PoolEntity(number))
            }
        })

        val orientation_screen = getResources().getConfiguration().orientation

        val numberOfColumns: Int

        when (orientation_screen) {
            1 -> numberOfColumns = 2
            2 -> numberOfColumns = 4
            else -> numberOfColumns = 2
        }

        binding.userList.layoutManager = GridLayoutManager(context, numberOfColumns)
        binding.userList.adapter = adapter

        viewModel.getListUsers()?.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.refreshUsers(it)
            }
        })
        viewModel.getListUsersPool()?.observe(viewLifecycleOwner, Observer {
        })
    }
}