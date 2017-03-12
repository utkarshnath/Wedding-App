package Helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shikhar.weddingappsample.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Models.CommentTemplate;

/**
 * Created by utkarshnath on 05/10/15.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private ArrayList<CommentTemplate> commentList;
    private LayoutInflater inflater;
    private Context context;

    public CommentAdapter(Context context,ArrayList<CommentTemplate> commentList){
        this.commentList = commentList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.comment_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Picasso.with(context).load("https://graph.facebook.com/" + commentList.get(i).getDpId() + "/picture?type=normal")
                .placeholder(R.drawable.appicon)
                .transform(new CircleTransform())
                .into(viewHolder.commentrphoto);
        viewHolder.commentrName.setText(commentList.get(i).getName());
        viewHolder.comment.setText(commentList.get(i).getComment());
    }



    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView commentrphoto;
        public TextView commentrName;
        public TextView comment;

        public ViewHolder(View itemView) {
            super(itemView);

            commentrphoto = (ImageView) itemView.findViewById(R.id.commentrdp);
            commentrName = (TextView) itemView.findViewById(R.id.commentrname);
            comment = (TextView) itemView.findViewById(R.id.commentedtext);
        }
    }
}


