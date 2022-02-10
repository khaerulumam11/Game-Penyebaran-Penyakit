package co.id.gamepenyebaranpenyakit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.id.gamepenyebaranpenyakit.R;
import co.id.gamepenyebaranpenyakit.model.UserModel;

public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.CommentHolder> {
    Context con;
    List<UserModel.ResultEntity> list = new ArrayList<>();

    //Constructor untuk adapter
    public ListUserAdapter(Context con) {
        this.con = con;
        notifyDataSetChanged();
    }

    public void setPost(List<UserModel.ResultEntity> list){
        this.list = list;
        notifyDataSetChanged();
    }
    //Return ViewHolder dari Recyclerview
    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentHolder(LayoutInflater.from(con).inflate(R.layout.item_list_user_score, parent, false));
    }

    //Mengikat nilai dari list dengan view
    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        UserModel.ResultEntity cur = list.get(position);
        holder.txtName.setText("Name : "+cur.getName());
        holder.txtEmail.setText("Email : "+cur.getEmail());
        holder.txtEmail.setVisibility(View.GONE);
        if (cur.getLevel().equalsIgnoreCase("1")){
            holder.txtLevel.setText("Level : Beginner");
        } else if (cur.getLevel().equalsIgnoreCase("2")){
            holder.txtLevel.setText("Level : Intermediate");
        } else {
            holder.txtLevel.setText("Level : Advance");
        }

        holder.txtScore.setText("Score : "+cur.getScore());
//        Glide.with(con).load(cur.getAvatar()).
//                apply(RequestOptions.circleCropTransform().placeholder(R.drawable.ic_account_circle_grey_24dp)).into(holder.gambar);

    }

    //Mendapatkan jumlah item pada recyclerview
    @Override
    public int getItemCount() {
        return list.size();
    }

    //Subclass sebagai viewholder
    class CommentHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtEmail, txtScore, txtLevel;
        ImageView gambar;

        public CommentHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtNama);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtScore = itemView.findViewById(R.id.txtScore);
            txtLevel = itemView.findViewById(R.id.txtLevel);
        }
    }
}
