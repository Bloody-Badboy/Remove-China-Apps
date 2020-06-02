package com.chinaappsremover.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chinaappsremover.R;
import com.chinaappsremover.listener.OnItemClickListener;
import com.chinaappsremover.wrapper.AppInfo;

import java.util.List;

public class ItemlistAdapter extends RecyclerView.Adapter<ItemlistAdapter.ViewHolder> {
    private List<AppInfo> appInfos;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public ItemlistAdapter(Context ctx, List<AppInfo> appInfoList, OnItemClickListener clickListener) {
        context = ctx;
        appInfos = appInfoList;
        onItemClickListener = clickListener;
    }


    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_item, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final AppInfo item = getItem(position);
        viewHolder.appsName.setText(item.appName);
        viewHolder.size.setText(item.size);
        viewHolder.img.setImageDrawable(item.icon);
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                onItemClickListener.onItemClick(position);

                Uri packageUri = Uri.parse("package:" + item.packageName);

                Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setData(packageUri);

                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                }
            }
        });
    }

    public AppInfo getItem(int position) {
        return appInfos.get(position);
    }

    public int getItemCount() {
        return appInfos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView appsName;
        ImageView delete;
        ImageView img;
        TextView size;

        public ViewHolder(View view) {
            super(view);
            appsName = view.findViewById(R.id.appsName);
            size = view.findViewById(R.id.size);
            img = view.findViewById(R.id.img);
            delete = view.findViewById(R.id.delete);
        }
    }
}
