package com.thoughtworks.craftsbeer;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.thoughtworks.craftsbeer.adapter.BeersAdapter;
import com.thoughtworks.craftsbeer.data.Beers;
import com.thoughtworks.craftsbeer.servicemanger.ApiClient;
import com.thoughtworks.craftsbeer.servicemanger.ApiInterface;
import com.thoughtworks.craftsbeer.servicemanger.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements
        View.OnClickListener {

    String TAG = "CraftsBeer";
    private RecyclerView recyclerView;
    private BeersAdapter mAdapter;
    private ProgressBar progressBar;
    private boolean sortOrder = true;
    private EditText searchEt;
    private TextView badge_textview;
    private List<Beers> beersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initWidgets();
        callBeerService();



        // search suggestions using the edittext widget
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    private void initWidgets() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        searchEt = (EditText) findViewById(R.id.searchEt);
        badge_textview = (TextView) findViewById(R.id.badge_textview);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new BeersAdapter(MainActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

    public void addItemToCart(int count) {

        badge_textview.setText("" + count);

    }

    public void filter(String text) {

        if (beersList != null) {
            List<Beers> temp = new ArrayList();
            for (Beers d : beersList) {
                if (d.getName().contains(text)) {
                    temp.add(d);
                }
            }
            //update recyclerview
            updateList(temp);
        }
    }

    public void filterByBearStyle(String text) {
        List<Beers> temp = new ArrayList();

        if (beersList != null) {
            for (Beers d : beersList) {
                if (d.getStyle().contains(text)) {
                    temp.add(d);
                }
            }
        }


        //update recyclerview
        updateList(temp);
    }

    public void updateList(List<Beers> temp) {

        mAdapter.updateList(temp);


    }

    /*
     * SORT
     */
    private void sortData(boolean asc) {
        //SORT ARRAY ASCENDING AND DESCENDING

        if (beersList != null) {
            if (asc) {
                Collections.sort(beersList, new Comparator<Beers>() {
                    public int compare(Beers obj1, Beers obj2) {
                        // notice the cast to (Integer) to invoke compareTo
                        return (Integer) (obj1.getAbv()).compareTo(obj2.getAbv());
                    }
                });

                sortOrder = false;

            } else {
                Collections.reverse(beersList);
                sortOrder = true;
            }


            setAdapter();

        }


    }


    private void callBeerService() {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        Call<List<Beers>> call = apiService.getBeersList();
        call.enqueue(new Callback<List<Beers>>() {
            @Override
            public void onResponse(Call<List<Beers>> call, Response<List<Beers>> response) {

                progressBar.setVisibility(View.GONE);
                beersList = response.body();
                setAdapter();

                Log.d(TAG, ">>>>>>>>>" + beersList.size());
            }

            @Override
            public void onFailure(Call<List<Beers>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                // Log error here since request failed
                showToast();
                Log.e(TAG, ">>>>>>>>>" + t.toString());
            }
        });
    }

    private void showToast() {
        Toast.makeText(this, Constants.NETWORK_ERROR, Toast.LENGTH_LONG).show();
    }

    private void setAdapter() {

        mAdapter.updateList(beersList);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:

                sortData(sortOrder);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showFilterDialog() {


        final String[] items = {"lager", "ale", "IPA"};
        // arraylist to keep the selected items
        final ArrayList<String> seletedItems = new ArrayList();

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Filter by Beer style")
                .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                        if (isChecked) {
                            seletedItems.add(items[indexSelected]);
                        } else {
                            seletedItems.remove(items[indexSelected]);
                        }
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        for (int index = 0; index < seletedItems.size(); index++) {
                            filterByBearStyle(seletedItems.get(index));
                        }

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Cancel
                    }
                }).create();
        dialog.show();
    }

    /* Click events in Floating Action Button */
    @Override
    public void onClick(View v) {

        showFilterDialog();

    }
}


