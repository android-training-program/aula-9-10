package pt.atp.bobi.presentation.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.atp.bobi.BobiApplication
import pt.atp.bobi.EXTRA_DOG_BREED
import pt.atp.bobi.EXTRA_DOG_NAME
import pt.atp.bobi.R
import pt.atp.bobi.data.model.Breed
import pt.atp.bobi.presentation.ui.adapters.BreedsAdapter
import pt.atp.bobi.presentation.ui.DetailsActivity

private const val TAG = "BreedsFragment"

class BreedsFragment : Fragment() {

    private val viewModel by viewModels<BreedsViewModel> {
        BreedsViewModelFactory((requireActivity().application as BobiApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_breeds, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()
        viewModel.loadDogs()
    }

    private fun setup() {
        requireView().findViewById<RecyclerView>(R.id.rv_breeds).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = BreedsAdapter {
                openDetailsScreen(it)
            }
        }

        viewModel.dogsLiveData.observe(viewLifecycleOwner) {
            val adapter =
                requireView().findViewById<RecyclerView>(R.id.rv_breeds).adapter as BreedsAdapter
            adapter.submitList(it)
        }

        viewModel.loadDogsDatabase().observe(viewLifecycleOwner) {
            Log.d(TAG, "$it")
        }
    }

    private fun openDetailsScreen(breed: Breed) {
        val intent = Intent(requireContext(), DetailsActivity::class.java)
        intent.putExtra(EXTRA_DOG_NAME, breed.name)
        intent.putExtra(EXTRA_DOG_BREED, breed.id)
        startActivity(intent)
    }
}