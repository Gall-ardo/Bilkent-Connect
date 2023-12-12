package com.hao.bilkentconnect;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hao.bilkentconnect.Adapter.PostAdapter;
import com.hao.bilkentconnect.databinding.FragmentMainFlowBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFlowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFlowFragment extends Fragment {
    private FragmentMainFlowBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    // buraya bi liste etc. eklicez. postlari tuttugumuz liste

    public MainFlowFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFlowFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFlowFragment newInstance(String param1, String param2) {
        MainFlowFragment fragment = new MainFlowFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainFlowBinding.inflate(inflater, container, false);
        recyclerView = binding.recyclerView;
        View view = binding.getRoot();

        //postList = new ArrayList<>();
        // Add posts
        //postAdapter = new PostAdapter(postList);

        recyclerView.setAdapter(postAdapter);
        return view;
    }
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}