package com.demo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.demo.R;
import com.demo.activities.BaseActivity;
import com.demo.activities.CheckSearchActivity;
import com.demo.adapters.SearchAdapter;
import com.demo.adapters.UserAdapter;
import com.demo.model.DataManager;
import com.demo.model.SearchModel;
import com.demo.utils.RecyclerItemClickListener;

import java.util.List;

/**
 * Created by asielgil on 2/22/17.
 */

public class PreviousSearchFragment extends Fragment {

    RecyclerView recycler;
    private BaseActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (BaseActivity) getActivity();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recycler = (RecyclerView) view.findViewById(R.id.recycler_view);
        updateUI();

        return view;
    }

    public void updateUI() {
        final List<SearchModel> searchList = DataManager.getAll();

        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        SearchAdapter adapter = new SearchAdapter(getActivity(), searchList);
        recycler.setAdapter(adapter);

        recycler.addOnItemTouchListener(new RecyclerItemClickListener(activity, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(activity, CheckSearchActivity.class);
                intent.putExtra("searchId", searchList.get(position).getId());
                startActivity(intent);
            }
        }));
    }

}
