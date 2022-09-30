package dev.guantel.forageapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.guantel.forageapp.R.*
import dev.guantel.forageapp.databinding.FragmentForageableListBinding

class ForageableListFragment : Fragment() {

    private val viewModel : ForageableViewModel by activityViewModels {
        ForageableViewModelFactory(
            (activity?.application as BaseApplication).database.forageableDao()
        )
    }
    private var _binding: FragmentForageableListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentForageableListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ForageableListAdapter{
            val action =
                ForageableListFragmentDirections
                    .actionForageableListFragmentToForageableDetailFragment(
                       )

            this.findNavController().navigate(action)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.adapter = adapter

        viewModel.allForageable.observe(viewLifecycleOwner){
            forageable ->
            forageable.let {
                adapter.submitList(it)
            }
        }

        binding.addForageableFab.setOnClickListener {
            val action = ForageableListFragmentDirections.
            actionForageableListFragmentToAddForageableFragment(
            )
            this.findNavController().navigate(action)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}