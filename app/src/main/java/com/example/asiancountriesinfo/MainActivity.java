package com.example.asiancountriesinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Context context;
    List<Model> countryList=new ArrayList<>();
    Button deleteButton;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerView);
        deleteButton=findViewById(R.id.deleteButton);
        progressBar=findViewById(R.id.progressBar);

        context=getApplicationContext();

        deleteButton.setOnClickListener(view -> deleteOfflineData());

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if(activeNetworkInfo != null && activeNetworkInfo.isConnected())
        {
            Log.i("logs","internet is available");

            loadOnlineData();
        }
        else
        {
            Log.i("logs","no internet");

            loadOfflineData();
        }


    }

    public void loadOnlineData()
    {
        url="https://restcountries.eu/rest/v2/region/asia";
        RequestQueue requestQueue= Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET,url,null,
                response -> {
                    for(int j = 0; j< response.length(); j++)
                    {
                        JSONObject jsonObject;
                        try {
                            jsonObject= response.getJSONObject(j);

                            String languages,borders,name,region,subRegion,capital,flagLink;
                            long population;
                            languages="";
                            borders="";
                            name=jsonObject.getString("name");
                            region=jsonObject.getString("region");
                            subRegion=jsonObject.getString("subregion");
                            subRegion=subRegion.replace(" ","\n");
                            capital=jsonObject.getString("capital");
                            flagLink=jsonObject.getString("flag");
                            population=jsonObject.getLong("population");

                            JSONArray tempBordersArray=jsonObject.getJSONArray("borders");

                            for(int k=0;k<tempBordersArray.length();k++)
                            {
                                if(k!=0)
                                {
                                borders=borders+",\n"+tempBordersArray.get(k);
                                }
                                else
                                    {
                                        borders= String.format("%s%s", borders, tempBordersArray.get(k));
                                    }
                            }

                            JSONArray languagesArray=jsonObject.getJSONArray("languages");

                            for(int k=0;k<languagesArray.length();k++)
                            {
                                if(k!=0)
                                {
                                languages= languages+",\n"+languagesArray.getJSONObject(k).get("name");
                                }
                                else
                                    {
                                        languages= String.format("%s%s", languages, languagesArray.getJSONObject(k).get("name"));
                                    }
                            }

                            countryList.add(new Model(name,capital,region,subRegion,
                                    flagLink,borders,languages,population));

                            saveDataForOffline(name,capital,region,subRegion,flagLink,
                                    borders,languages,population);

                            progressBar.setVisibility(View.GONE);

                            CountryDetailsAdapter adapter=new CountryDetailsAdapter(context,countryList);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                error -> {
                         Toast.makeText(context,
                                 "Something went wrong...",Toast.LENGTH_SHORT).show();
                         error.printStackTrace();
                }
                );

        requestQueue.add(jsonArrayRequest);

    }

    public void saveDataForOffline(String countryName,String countryCapital,String countryRegion,
                                   String countrySubRegion,String flagLink,String borders,
                                   String languages,long population)
    {
        new Thread(()->
        {
            Country country=new Country();
            country.setName(countryName);
            country.setCapital(countryCapital);
            country.setRegion(countryRegion);
            country.setSubRegion(countrySubRegion);
            country.setFlagLink(flagLink);
            country.setBorders(borders);
            country.setLanguages(languages);
            country.setPopulation(population);

            DatabaseClient.getInstance(context).getAppDatabase()
                    .dao().insert(country);
            Log.i("logs","Inserting into offline database");

        }).start();
    }

    public void loadOfflineData()
    {
        List<Model> offlineModelList=new ArrayList<>();
        new Thread(()->
        {
            List<Country> offlineCountryList=DatabaseClient.getInstance(context)
                    .getAppDatabase().dao().getAll();

            for(int i=0;i<offlineCountryList.size();i++)
            {
                offlineModelList.add(new Model(offlineCountryList.get(i).getName(),
                        offlineCountryList.get(i).getCapital(),
                        offlineCountryList.get(i).getRegion(),
                        offlineCountryList.get(i).getSubRegion(),
                        offlineCountryList.get(i).getFlagLink(),
                        offlineCountryList.get(i).getBorders(),
                        offlineCountryList.get(i).getLanguages(),
                        offlineCountryList.get(i).getPopulation()));
            }
        }).start();

        progressBar.setVisibility(View.GONE);

        CountryDetailsAdapter adapter=new CountryDetailsAdapter(context,offlineModelList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    public void deleteOfflineData()
    {
        new Thread(()->
        {
            DatabaseClient.getInstance(context)
                    .getAppDatabase().dao().deleteAll();
            Log.i("logs","deleting offline data");
        }).start();
        Toast.makeText(context,"Deleting offline data",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.delete)
        {
            deleteOfflineData();
            return true;
        }
        return false;
    }
}