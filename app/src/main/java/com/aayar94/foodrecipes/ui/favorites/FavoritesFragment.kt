package com.aayar94.foodrecipes.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aayar94.foodrecipes.R
import com.aayar94.foodrecipes.databinding.DeleteAllAlertDialogBinding
import com.aayar94.foodrecipes.databinding.FragmentFavoritesBinding
import com.aayar94.foodrecipes.ui.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private var mBinding: FragmentFavoritesBinding? = null
    private val binding get() = mBinding!!

    private val mainViewModel: MainViewModel by viewModels()
    private val mAdapter: FavoriteRecipesAdapter by lazy {
        FavoriteRecipesAdapter(
            requireActivity(), mainViewModel
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.mAdapter = mAdapter
        setupRecyclerView(binding.favoriteRecipesRecyclerView)

        mainViewModel.readFavoriteRecipes.observe(viewLifecycleOwner) { favoritesEntity ->
            mAdapter.setData(favoritesEntity)
        }
        setupMenu()
        return binding.root
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.favorites_fragment_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                val builder = MaterialAlertDialogBuilder(requireContext())
                val view = DeleteAllAlertDialogBinding.inflate(layoutInflater)
                builder.setView(view.root)
                view.dialogTitle.text = getString(R.string.delete_all)
                view.dialogMessage.text =
                    getString(R.string.do_you_want_to_delete_all_saved_recipes)
                val dialog = builder.create()
                view.dialogCancelButton.setOnClickListener {
                    dialog.dismiss()
                }
                view.dialogDeleteAllButton.setOnClickListener {
                    mainViewModel.deleteAllFavoriteRecipes()
                    dialog.dismiss()
                }
                dialog.show()

                return true
            }
        })
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        mAdapter.clearContextualActionMode()
        mBinding = null
    }
}