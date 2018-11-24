package wasim.sample.nytimes.views.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import wasim.sample.nytimes.R;
import wasim.sample.nytimes.models.pojo.Medium;
import wasim.sample.nytimes.models.pojo.Result;
import wasim.sample.nytimes.views.viewholder.ArticlesViewHolder;


public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesViewHolder> {
    private List<Result> response;
    private Context mContext;


    public ArticlesAdapter() {
        response = new ArrayList<>();
    }

    @Override
    public ArticlesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ArticlesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.articles_row_view, parent, false));
    }

    public void updateDataList(List<Result> data) {
        this.response.clear();
        if(data != null)
            this.response.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ArticlesViewHolder holder, int position) {
        Result dataElement = response.get(position);
        if(dataElement != null) {
            holder.txtHeader.setText(dataElement.getTitle());
            holder.date.setText(dataElement.getPublishedDate());
            holder.byLine.setText(dataElement.getByline());
            List<Medium> med = dataElement.getMedia();
            if(med != null ) {
                String url = med.get(0).getMediaMetadata().get(0).getUrl();
                if (url != null) {
                    Glide.with(mContext)
                            .load(url)
                            .asBitmap()
                            .centerCrop()
                            .into(new BitmapImageViewTarget(holder.img_article_icon) {
                                @Override
                                protected void setResource(Bitmap resource) {
                                    RoundedBitmapDrawable circularBitmapDrawable =
                                            RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                                    circularBitmapDrawable.setCircular(true);
                                    holder.img_article_icon.setImageDrawable(circularBitmapDrawable);
                                }
                            });
                }
            }
        }
    }



    @Override
    public int getItemCount() {
        return response.size();
    }

    public Result getItemAtPosition(int position) {
        return response.get(position);
    }

}

