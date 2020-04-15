package com.example.secondar;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andrognito.flashbar.Flashbar;

import java.util.ArrayList;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder>{

    private ArrayList<String> textNames = new ArrayList<>();
    private ArrayList<Integer> imagesPath = new ArrayList<>();
    private Context context;
    private ArrayList<String> modelNames = new ArrayList<>();


    public RecyclerviewAdapter(Context context,ArrayList<String> textNames, ArrayList<Integer> imagesPath,ArrayList<String> modelNames) {
        this.textNames = textNames;
        this.imagesPath = imagesPath;
        this.modelNames = modelNames;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(imagesPath.get(position));
        holder.textView.setText(textNames.get(position));

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.model = modelNames.get(position);

                Toast.makeText(context,"Selected: "+  modelNames.get(position), Toast.LENGTH_SHORT).show();
                new Flashbar.Builder((Activity) context)
                        .gravity(Flashbar.Gravity.TOP)
                        .title("BUY "+ modelNames.get(position)+ " NOW")
                        .message("Tap here to buy"+modelNames.get(position))
                        .listenBarTaps(new Flashbar.OnTapListener() {
                            @Override
                            public void onTap(Flashbar flashbar) {
                                String s = modelNames.get(position);

                                openInShopemneted(context,"com.aabiskar.shopmented",String.valueOf( s.substring(0, s.length() - 4)));
                            }
                        })
                        .build().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return imagesPath.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageview);
            textView = itemView.findViewById(R.id.text);
        }
    }


    public void openInShopemneted(Context context, String packageName,String modelName) {


        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        final ComponentName cn = new ComponentName("com.aabiskar.shopmented", "com.aabiskar.shopmented.RouterActivity");
        intent.setComponent(cn);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("message",modelName);
        try
        {
            context.startActivity(intent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(context,"Activity Not Found",Toast.LENGTH_SHORT).show();
        }

//
//
//        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
//        if (intent == null) {
//            // Bring user to the market or let them choose an app?
//            intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse("market://details?id=" + packageName));
//        }
//        intent.putExtra("message",modelName);
//        context.startActivity(intent);
    }
}
