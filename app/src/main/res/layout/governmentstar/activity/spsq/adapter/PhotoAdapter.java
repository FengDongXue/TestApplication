package com.lanwei.governmentstar.activity.spsq.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.spsq.utils.AndroidLifecycleUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by donglua on 15/5/31.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private ArrayList<String> photoPaths = new ArrayList<String>();
    private LayoutInflater inflater;

    private Context mContext;

    public final static int TYPE_ADD = 1;
    final static int TYPE_PHOTO = 2;

    public final static int MAX = 9;
    private LinearLayout llAdd;
    private PhotoViewHolder holder;

    public PhotoAdapter(Context mContext, ArrayList<String> photoPaths) {
        this.photoPaths = photoPaths;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);

    }


    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        switch (viewType) {
            case TYPE_ADD:
                itemView = inflater.inflate(R.layout.item_add, parent, false);
                llAdd = (LinearLayout) itemView.findViewById(R.id.ll_add);
                if (photoPaths.size() >= MAX ){
                    llAdd.setVisibility(View.GONE);
                }else {
                    llAdd.setVisibility(View.VISIBLE);
                }
                break;
            case TYPE_PHOTO:
                itemView = inflater.inflate(R.layout.picker_item_photo, parent, false);
                break;
        }
        return new PhotoViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, final int position) {
        this.holder = holder;
        if (getItemViewType(position) == TYPE_PHOTO) {
            Uri uri = Uri.fromFile(new File(photoPaths.get(position)));

            boolean canLoadImage = AndroidLifecycleUtils.canLoadImage(holder.ivPhoto.getContext());

            if (canLoadImage) {
                Glide.with(mContext)
                        .load(uri)
                        .centerCrop()
                        .thumbnail(0.1f)
                        .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                        .error(R.drawable.mis_default_error)
                        .into(holder.ivPhoto);
            }
        }
    }


    @Override
    public int getItemCount() {
        int count = photoPaths.size() + 1;
        if (count > MAX) {
            count = MAX;
            holder.setMaxGone();
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == photoPaths.size() && position != MAX) ? TYPE_ADD : TYPE_PHOTO;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;
        private LinearLayout llAdd;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            llAdd = (LinearLayout) itemView.findViewById(R.id.ll_add);
        }

        public void setMaxGone() {
            llAdd.setVisibility(View.GONE);
        }
    }

}
