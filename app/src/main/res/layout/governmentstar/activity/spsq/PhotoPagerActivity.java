package com.lanwei.governmentstar.activity.spsq;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.gwnz.DocumentJDActivity;
import com.lanwei.governmentstar.activity.spsq.fragment.ImagePagerFragment;
import com.lanwei.governmentstar.utils.PopupWindowUtil;
import com.lanwei.governmentstar.view.Dialog02;

import java.util.List;

import static com.lanwei.governmentstar.activity.spsq.PhotoPreview.EXTRA_CURRENT_ITEM;
import static com.lanwei.governmentstar.activity.spsq.PhotoPreview.EXTRA_PHOTOS;
import static com.lanwei.governmentstar.activity.spsq.PhotoPreview.EXTRA_SHOW_DELETE;
import static com.lanwei.governmentstar.activity.spsq.view.PhotoPicker.KEY_SELECTED_PHOTOS;


/**
 * Created by donglua on 15/6/24.
 */
public class PhotoPagerActivity extends AppCompatActivity {

    private ImagePagerFragment pagerFragment;

    private ActionBar actionBar;
    private boolean showDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.picker_activity_photo_pager);

        int currentItem = getIntent().getIntExtra(EXTRA_CURRENT_ITEM, 0);
        List<String> paths = getIntent().getStringArrayListExtra(EXTRA_PHOTOS);
        showDelete = getIntent().getBooleanExtra(EXTRA_SHOW_DELETE, true);

        if (pagerFragment == null) {
            pagerFragment =
                    (ImagePagerFragment) getSupportFragmentManager().findFragmentById(R.id.photoPagerFragment);
        }
        pagerFragment.setPhotos(paths, currentItem);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            updateActionBarTitle();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                actionBar.setElevation(25);
            }
        }


        pagerFragment.getViewPager().addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                updateActionBarTitle();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (showDelete) {
            getMenuInflater().inflate(R.menu.__picker_menu_preview, menu);
        }
        return true;
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra(KEY_SELECTED_PHOTOS, pagerFragment.getPaths());
        setResult(RESULT_OK, intent);
        finish();

        super.onBackPressed();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        if (item.getItemId() == R.id.delete) {
            final int index = pagerFragment.getCurrentItem();

//            final String deletedPath = pagerFragment.getPaths().get(index);
//
//            Snackbar snackbar = Snackbar.make(pagerFragment.getView(), R.string.__picker_deleted_a_photo,
//                    Snackbar.LENGTH_LONG);

            if (pagerFragment.getPaths().size() > 0) {

                final Dialog02 dialog02 = new Dialog02(this);
                dialog02.setContent("您确定删除该照片吗？", Color.parseColor("#4f4f4f"));
                dialog02.setTitle("删除照片", Color.parseColor("#5184c3"));
                dialog02.setLeftBtn(R.drawable.select_button_left, Color.WHITE);
                dialog02.setRightBtn(R.drawable.select_button_right, Color.WHITE);
                dialog02.setYesOnclickListener("确定", new Dialog02.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        dialog02.dismiss();
                        pagerFragment.getPaths().remove(index);
                        pagerFragment.getViewPager().getAdapter().notifyDataSetChanged();
                        onBackPressed();
                    }
                });
                dialog02.setNoOnclickListener("取消", new Dialog02.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        dialog02.dismiss();
                    }
                });

                Window window = dialog02.getWindow();
                //设置显示动画
                window.setWindowAnimations(R.style.dialog_animstyle);
                WindowManager.LayoutParams wl = window.getAttributes();
                wl.x = 0;

                wl.y = -this.getWindowManager().getDefaultDisplay().getHeight() / 50;
                //设置显示位置
                dialog02.onWindowAttributesChanged(wl);//设置点击外围解散
                dialog02.setCanceledOnTouchOutside(true);

                dialog02.show();

//                // show confirm dialog
//                new AlertDialog.Builder(this)
//                        .setTitle(R.string.__picker_confirm_to_delete)
//                        .setPositiveButton(R.string.__picker_yes, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.dismiss();
//                                pagerFragment.getPaths().remove(index);
//                                pagerFragment.getViewPager().getAdapter().notifyDataSetChanged();
//                                onBackPressed();
//                            }
//                        })
//                        .setNegativeButton(R.string.__picker_cancel, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.dismiss();
//                            }
//                        })
//                        .show();

//            } else {
//
//                snackbar.show();
//
//                pagerFragment.getPaths().remove(index);
//                pagerFragment.getViewPager().getAdapter().notifyDataSetChanged();
//            }

//            snackbar.setAction(R.string.__picker_undo, new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (pagerFragment.getPaths().size() > 0) {
//                        pagerFragment.getPaths().add(index, deletedPath);
//                    } else {
//                        pagerFragment.getPaths().add(deletedPath);
//                    }
//                    pagerFragment.getViewPager().getAdapter().notifyDataSetChanged();
//                    pagerFragment.getViewPager().setCurrentItem(index, true);
                }
//            });

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateActionBarTitle() {
        if (actionBar != null) actionBar.setTitle(
                getString(R.string.__picker_image_index, pagerFragment.getViewPager().getCurrentItem() + 1,
                        pagerFragment.getPaths().size()));
    }
}
