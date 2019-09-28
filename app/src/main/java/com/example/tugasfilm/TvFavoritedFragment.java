package com.example.tugasfilm;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.tugasfilm.presenter.Tv;
import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

public class TvFavoritedFragment extends Fragment {

    private RecyclerView recyclerViewMain;
    private ProgressBar progressBarMain;
    private RealmResults<TvFavorite> listFavorited;
    private ListTvAdapter listFilmAdapter;
    private ArrayList<Tv> tv;
    private Realm realm;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_favorited, container, false);
        recyclerViewMain = view.findViewById(R.id.rv_main9);
        progressBarMain = view.findViewById(R.id.pb_main9);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            Realm.init(getActivity());
            realm = Realm.getDefaultInstance();
        } catch (RealmMigrationNeededException e) {
            Realm.deleteRealm(realm.getDefaultConfiguration());
            realm = Realm.getDefaultInstance();
        }
        tv = new ArrayList<>();
        showRecyclerList();
        loadData();
    }

    private void showRecyclerList(){
        listFilmAdapter = new ListTvAdapter(getActivity());
        listFilmAdapter.setListTv(tv);
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewMain.setAdapter(listFilmAdapter);
        listFavorited = realm.where(TvFavorite.class).findAll();
    }

    private void loadData() {
        listFavorited = realm.where(TvFavorite.class).findAll();
        progressBarMain.setVisibility(View.VISIBLE);
        if(!listFavorited.isEmpty()) {
            for(int i=0; i<listFavorited.size(); i++) {
                Tv dummy = new Tv();
                dummy.setName(listFavorited.get(i).getName());
                dummy.setId(listFavorited.get(i).getId());
                dummy.setPoster(listFavorited.get(i).getPoster());
                dummy.setDescription(listFavorited.get(i).getDescription());
                dummy.setDate(listFavorited.get(i).getDate());
                dummy.setRating(listFavorited.get(i).getRating());
                dummy.setLanguage(listFavorited.get(i).getLanguange());
                tv.add(dummy);
            }
        }
        progressBarMain.setVisibility(View.GONE);
        listFilmAdapter.setListTv(tv);
    }


}
