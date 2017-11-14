package com.lanwei.governmentstar.activity.spsq.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.spsq.QjApplySpActivity;
import com.lanwei.governmentstar.activity.spsq.utils.AndroidLifecycleUtils;
import com.lanwei.governmentstar.activity.zwyx.EmailBaseActivity;
import com.lanwei.governmentstar.activity.zyx.DetailsFJActivity;
import com.lanwei.governmentstar.bean.SpsqQjApplyDetails;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/7/007.
 */

public class PicAdapter extends RecyclerView.Adapter<PicAdapter.PhotoViewHolder> {
    private List<SpsqQjApplyDetails.ImgfilesBean> photoPaths = new ArrayList<>();
    private LayoutInflater inflater;
    private Activity activity;
    private String filePreview;

    public PicAdapter(Activity activity, List<SpsqQjApplyDetails.ImgfilesBean> photoPaths) {
        this.photoPaths = photoPaths;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);

    }


    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        itemView = inflater.inflate(R.layout.item_picadapter, parent, false);
        return new PhotoViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, final int position) {
        final String filePath = photoPaths.get(position).getFilePath();
        Log.e("66", filePath);

//        Glide.with(activity).load(filePath).into(holder.ivPhoto);
        String tag = (String) holder.ivPhoto.getTag();
        if (!TextUtils.equals(filePath, tag)) {
            holder.ivPhoto.setImageResource(R.drawable.__picker_photo_bg);
        }
        Glide.with(activity)
                .load(filePath)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        if (filePath != null)
                            holder.ivPhoto.setTag("");
                        holder.ivPhoto.setImageDrawable(resource);
                    }
                });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 跳去附件详情界面

                filePreview = photoPaths.get(position).getFilePreView();
//                Log.e("附件", filePreview);


                Intent intent = new Intent(activity, DetailsFJActivity.class);
                intent.putExtra("filePreview", filePreview);
                intent.putExtra("type", "2");
                activity.startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        return photoPaths.size();
    }


    class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photoadapter);
        }

    }

}
