package com.handshake.pritz.OrganicHomeStay;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import com.squareup.picasso.Picasso;

public class Login extends AppCompatActivity {
    private EditText seaa;
    private ImageButton btnn;
    private RecyclerView resultt;
    DatabaseReference mdatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        seaa = findViewById(R.id.sea);
        btnn = findViewById(R.id.btn);
        resultt = findViewById(R.id.result);
        resultt.setHasFixedSize(true);
        resultt.setLayoutManager(new LinearLayoutManager(this));
        mdatabase = FirebaseDatabase.getInstance().getReference().child("EastHomestays");

        btnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String se=seaa.getText().toString();
                FirebaseSearch(se);
            }
        });
    }

    private void FirebaseSearch(String se) {

        Query firebasequery=mdatabase.orderByChild("Name").startAt(se).endAt(se + "\uf8ff");

FirebaseRecyclerAdapter<gtr,BlogViewholder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<gtr, BlogViewholder>(
        gtr.class,R.layout.design2,Login.BlogViewholder.class,firebasequery) {
    @Override
    protected void populateViewHolder(BlogViewholder viewHolder, gtr model, int position) {
        viewHolder.setName(model.getName());
        viewHolder.setHomestayPic(getApplicationContext(), model.getHomestayPic());
        viewHolder.setHomeaddress(model.getHomeaddress());
        viewHolder.setPrice(model.getPrice());
    }
};
resultt.setAdapter(firebaseRecyclerAdapter);
        }


    public static class BlogViewholder extends RecyclerView.ViewHolder {
        View view;

        public BlogViewholder(View itemview) {
            super(itemview);
            view = itemview;

        }

        public void setName(String nname) {
            TextView ptitle = view.findViewById(R.id.hotelname);
            ptitle.setText(nname);
        }

        public void setHomeaddress(String Hommeaddress) {
            TextView det = view.findViewById(R.id.hoteladdress);
            det.setText(Hommeaddress);
        }

        public void setHomestayPic(Context ctx, String image) {
            ImageView post = view.findViewById(R.id.hotelpic);
            Picasso.with(ctx).load(image).into(post);
            Picasso.with(ctx)
                    .load(image)
                    .placeholder(R.mipmap.file_icon)
                    .into(post);
        }

        public void setPrice(String Pprice) {
            TextView jo = view.findViewById(R.id.rate);
            jo.setText(Pprice);
        }
    }
}

