package dev.guantel.forageapp


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dev.guantel.forageapp.data.Forageable
import dev.guantel.forageapp.databinding.FragmentAddForageableBinding

class AddForageableFragment : Fragment() {

    private val viewModel : ForageableViewModel by activityViewModels {
        ForageableViewModelFactory(
            (activity?.application as BaseApplication).database.forageableDao()
        )
    }
    private val navigationArgs : AddForageableFragmentArgs by navArgs()
    lateinit var forageable: Forageable
    private var _binding: FragmentAddForageableBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentAddForageableBinding.inflate(inflater,container,false)
       return binding.root
    }

    override fun onViewCreated(view: View,
                               savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.id
        if (id > 0) {
            viewModel.getForageableById(id).observe(viewLifecycleOwner) {
                forageable = it
                bindForageable(it)
            }

            binding.deleteBtn.visibility = View.VISIBLE
            binding.deleteBtn.setOnClickListener {
                deleteForageable(forageable)
            }
        }else{
            binding.saveBtn.setOnClickListener{
                addForageable()
            }
        }
        }

    private fun addForageable() {
       if (isValidEntry()){
           viewModel.addForeageable(
               binding.nameInput.text.toString(),
               binding.locationAddressInput.text.toString(),
               binding.inSeasonCheckbox.isChecked,
               binding.notesInput.text.toString()
           )
           findNavController().navigate(
               R.id.action_addForageableFragment_to_forageableListFragment
           )
       }
    }


    private fun deleteForageable(forageable: Forageable) {
        viewModel.deleteForeageable(forageable)
        findNavController().navigate(
            R.id.action_addForageableFragment_to_forageableListFragment
        )

    }

    private fun bindForageable(forageable: Forageable) {
      binding.apply {
          nameInput.setText(forageable.name, TextView.BufferType.SPANNABLE)
          locationAddressInput.setText(forageable.address, TextView.BufferType.SPANNABLE)
          inSeasonCheckbox.isChecked = forageable.inSeason
          notesInput.setText(forageable.notes, TextView.BufferType.SPANNABLE)
          saveBtn.setOnClickListener {
              updateForageable()
          }
      }
      }

    private fun updateForageable() {
      if (isValidEntry()){

          viewModel.updateForeageable(
              id = navigationArgs.id,
              name = binding.nameInput.text.toString(),
              address = binding.locationAddressInput.text.toString(),
              inSeason = binding.inSeasonCheckbox.isChecked,
              notes = binding.notesInput.text.toString()
          )
          findNavController().navigate(
              R.id.action_addForageableFragment_to_forageableListFragment
          )
      }
    }

    private fun isValidEntry() = viewModel.isValidEntry(
        binding.nameInput.text.toString(),
        binding.locationAddressInput.text.toString()
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


