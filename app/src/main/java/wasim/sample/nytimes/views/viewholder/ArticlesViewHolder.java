package wasim.sample.nytimes.views.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import wasim.sample.nytimes.R;


public class ArticlesViewHolder extends RecyclerView.ViewHolder {

    public TextView txtHeader;
    public TextView date;
    final public ImageView img_article_icon;
    public TextView source;
    public TextView byLine;


    public ArticlesViewHolder(View v) {
        super(v);
        txtHeader = (TextView) v.findViewById(R.id.title);
        img_article_icon = (ImageView)v.findViewById(R.id.img_article_icon);
        date = (TextView)v.findViewById(R.id.date);
        byLine = (TextView)v.findViewById(R.id.byLine);


    }
}
