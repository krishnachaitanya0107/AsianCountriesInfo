package com.example.asiancountriesinfo;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener;

import java.util.List;

public class CountryDetailsAdapter extends RecyclerView.Adapter<CountryDetailsAdapter.ViewHolder> {

    Context context;
    List<Model> countryList;

    public CountryDetailsAdapter(Context context,List<Model> countryList)
    {
        this.context=context;
        this.countryList=countryList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView countryNameTextView,countryCapitalTextView,countryRegionTextView,
                countrySubRegionTextView,bordersTextView,
                languagesTextView,populationTextView;
        ImageView flagImageImageView;



        public ViewHolder(@NonNull View itemView)
        {
           super(itemView);
           countryNameTextView=itemView.findViewById(R.id.countryNameTextView);
           countryCapitalTextView=itemView.findViewById(R.id.countryCapitalTextView);
           countryRegionTextView=itemView.findViewById(R.id.CountryRegionTextView);
           countrySubRegionTextView=itemView.findViewById(R.id.CountrySubRegionTextView);
           bordersTextView=itemView.findViewById(R.id.CountryBordersTextView);
           flagImageImageView=itemView.findViewById(R.id.flagImageImageView);
           languagesTextView=itemView.findViewById(R.id.countryLanguagesTextView);
           populationTextView=itemView.findViewById(R.id.CountryPopulationTextView);
        }

    }

    @NonNull
    @Override
    public CountryDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_country_data, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CountryDetailsAdapter.ViewHolder holder, int position) {

        holder.populationTextView.setText(String.valueOf(countryList.get(position).getPopulation()));
        holder.languagesTextView.setText(countryList.get(position).getLanguages());
        holder.bordersTextView.setText(countryList.get(position).getBorders());
        holder.countryNameTextView.setText(countryList.get(position).getCountryName());
        holder.countrySubRegionTextView.setText(countryList.get(position).getCountrySubRegion());
        holder.countryRegionTextView.setText(countryList.get(position).getCountryRegion());
        holder.countryCapitalTextView.setText(countryList.get(position).getCountryCapital());

        Uri uri= Uri.parse(countryList.get(position).getFlagLink());

        GlideToVectorYou.init().with(context).withListener(new GlideToVectorYouListener() {
            @Override
            public void onLoadFailed() {
                Toast.makeText(context,"something went wrong...",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResourceReady() {
                Log.i("logs","image loaded successfully");
            }
        }).load(uri,holder.flagImageImageView);

    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }
}
