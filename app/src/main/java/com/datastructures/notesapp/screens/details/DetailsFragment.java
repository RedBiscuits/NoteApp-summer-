package com.datastructures.notesapp.screens.details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.datastructures.notesapp.R;
import com.datastructures.notesapp.databinding.FragmentDetailsBinding;

public class DetailsFragment extends Fragment {

    private String title;
    private String details;
    private FragmentDetailsBinding binding;

    public DetailsFragment() {
        super(R.layout.fragment_details);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        title = DetailsFragmentArgs.fromBundle(getArguments()).getNoteSelected();
        details = DetailsFragmentArgs.fromBundle(getArguments()).getNoteSelectedDetails();
        binding = FragmentDetailsBinding.inflate(inflater ,container , false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.noteTV.setText(title);
        binding.noteDetailsTV.setText(details);

    }
}