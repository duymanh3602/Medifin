package com.btl.medifin.fragment.doctor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btl.medifin.R;

public class DoctorChatFragment extends Fragment {



    public DoctorChatFragment() {
        // Required empty public constructor
    }


    public static DoctorChatFragment newInstance() {
        DoctorChatFragment fragment = new DoctorChatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bs_chat, container, false);
    }
}