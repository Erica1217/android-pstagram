package com.pstagram.pstagram.pstagram.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pstagram.pstagram.pstagram.R;
import com.pstagram.pstagram.pstagram.databinding.FragmentAlimBinding;

public class AlimFragment extends Fragment {
    FragmentAlimBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_alim, container, false);
        return binding.getRoot();
    }
}
