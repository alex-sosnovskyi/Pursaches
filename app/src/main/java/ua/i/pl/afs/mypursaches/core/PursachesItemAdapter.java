package ua.i.pl.afs.mypursaches.core;

import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.i.pl.afs.mypursaches.BuildConfig;
import ua.i.pl.afs.mypursaches.R;
import ua.i.pl.afs.mypursaches.models.Pursache;

public class PursachesItemAdapter extends RecyclerView.Adapter<PursachesItemAdapter.PursachesItemHolder> {
    private List<Pursache> items = new ArrayList<>();
    private ClickedCallback callback;

    public void setItems(List<Pursache> items) {
        clearItems();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems() {
        items.clear();
    }

    public void setCallback(ClickedCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public PursachesItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pursache_item, parent, false);
        return new PursachesItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PursachesItemHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class PursachesItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.pursache_name)
        TextView pursacheName;
        @BindView(R.id.pursache_img)
        ImageView pursacheImg;
        @BindView(R.id.bought_check_box)
        CheckBox boughtCheckBox;

        public PursachesItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Pursache pursache) {
            pursacheName.setText(pursache.getName());
            pursacheImg.setVisibility(View.GONE);
            if (null != pursache.getPict()) {
                File photoFile = new File(pursache.getPict());
                if (photoFile.exists()) {
                    try {
                        Uri uriForFile = FileProvider.getUriForFile(itemView.getContext(), BuildConfig.APPLICATION_ID + ".provider", photoFile);
                        Picasso.get().load(uriForFile).fit().into(pursacheImg);
                        pursacheImg.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        System.out.println("Exception e " + e.getMessage());
                    }
                } else {
                    if(pursache.getPictUri().isEmpty()){
                        pursacheImg.setVisibility(View.GONE);
                    }else{
                        Picasso.get().load(Uri.parse(pursache.getPictUri())).fit().into(pursacheImg);
                        pursacheImg.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                pursacheImg.setVisibility(View.GONE);
            }
            boughtCheckBox.setChecked(pursache.isBought());
            if (pursache.isBought()) {
                boughtCheckBox.setEnabled(false);
            } else {
                boughtCheckBox.setEnabled(true);
            }
            boughtCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.clicked(pursache.getId(), boughtCheckBox.isChecked());
                }
            });
        }
    }
}
