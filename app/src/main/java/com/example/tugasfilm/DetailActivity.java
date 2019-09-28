package com.example.tugasfilm;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.tugasfilm.presenter.Tv;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

public class DetailActivity extends AppCompatActivity {

    TextView tvName;
    TextView tvDescription;
    ImageView ivPhoto;
    RatingBar rating;
    TextView tvDate;
    TextView tvLanguage;
    TextView ratingText;
    Tv tv;
    private boolean favorite = false;
    private Realm realm;
    RealmResults<TvFavorite> resultFavorite;
    private int id;
    private String title, description, poster, date, language;
    private double ratingBar;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        try {
            Realm.init(this);
            realm = Realm.getDefaultInstance();
        } catch(RealmMigrationNeededException r) {
            Realm.deleteRealm(realm.getDefaultConfiguration());
            realm = Realm.getDefaultInstance();
        }
        tvName = findViewById(R.id.tv_detail_name);
        tvDescription = findViewById(R.id.tv_detail_desc);
        ivPhoto = findViewById(R.id.iv_detail_photo);
        rating = findViewById(R.id.tv_rating);
        tvDate = findViewById(R.id.tv_detail_date);
        tvLanguage = findViewById(R.id.tv_detail_language);
        ratingText = findViewById(R.id.tv_rating_text);

        this.tv = getIntent().getParcelableExtra("tv");
        this.id = tv.getId();
        this.title = tv.getName();
        this.description = tv.getDescription();
        this.poster = tv.getPoster();
        this.date = tv.getDate();
        this.ratingBar = tv.getRating();
        this.language = tv.getLanguage();

        tvName.setText(tv.getName());
        tvDescription.setText(tv.getDescription());
        String photo = BuildConfig.MOVIE_POSTER+tv.getPoster();
        Glide.with(this)
                .load(photo)
                .into(ivPhoto);
        float rate = (float) (tv.getRating()/10)*5;
        ratingText.setText(String.valueOf(rate));
        rating.setRating(rate);
        tvDate.setText(tv.getDate());
        tvLanguage.setText(tv.getLanguage());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        favoriteState();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.item1:
                if(favorite) {
                    boolean delete = removeFavorite();
                    if(delete) {
                        item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_like_satu));
                        Toast.makeText(this, "Remove from favorite", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed remove from favorite", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    favorite = addToFavorite();
                    if(favorite) {
                        item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_like));
                        Toast.makeText(this, "Add to favorite.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed add to favorite.", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            default:
                break;
        }
        return false;
    }

    private boolean addToFavorite() {
        TvFavorite tvFavorite = new TvFavorite();
        tvFavorite.setId(this.id);
        tvFavorite.setName(this.title);
        tvFavorite.setDescription(this.description);
        tvFavorite.setPoster(this.poster);
        tvFavorite.setDate(this.date);
        tvFavorite.setLanguange(this.language);
        tvFavorite.setRating(this.ratingBar);

        realm = Realm.getDefaultInstance();
        TvFavorite puppies = realm.where(TvFavorite.class).equalTo("id", this.id).findFirst();
        if(puppies == null) {
            try {
                realm.beginTransaction();
                realm.copyToRealm(tvFavorite);
                realm.commitTransaction();
                realm.close();
            } catch(Exception e) {
                e.printStackTrace();
                return false;
            }
            try {
                realm.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    private boolean removeFavorite() {
        try {
            realm.beginTransaction();
            TvFavorite tvFavorite = realm.where(TvFavorite.class).equalTo("id", this.id).findFirst();
            if(tvFavorite!=null) {
                tvFavorite.deleteFromRealm();
                realm.commitTransaction();
                while(realm.isInTransaction()) {
                    Log.e("Realm", "still in transaction");
                }
                realm.close();
                return true;
            }
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        try {
            realm.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void favoriteState() {
        resultFavorite = realm.where(TvFavorite.class).equalTo("id", this.id).findAll();
        favorite = !resultFavorite.isEmpty();
        if(favorite) {
            this.menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_like));
        } else {
            this.menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_like_satu));
        }
    }
}

