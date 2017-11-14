package com.lanwei.governmentstar.activity.spsq.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.lll.refresh.OnItemClickListener;
import com.lanwei.governmentstar.activity.spsq.ApplyActivity;
import com.lanwei.governmentstar.activity.spsq.CapitalBxCsActivity;
import com.lanwei.governmentstar.activity.spsq.CapitalBxSpActivity;
import com.lanwei.governmentstar.activity.spsq.CapitalOtherCsActivity;
import com.lanwei.governmentstar.activity.spsq.CapitalOtherSpActivity;
import com.lanwei.governmentstar.activity.spsq.CapitalPayCsActivity;
import com.lanwei.governmentstar.activity.spsq.CapitalPaySpActivity;
import com.lanwei.governmentstar.activity.spsq.OtherActivityCsActivity;
import com.lanwei.governmentstar.activity.spsq.OtherActivitySpActivity;
import com.lanwei.governmentstar.activity.spsq.OtherHyspCsActivity;
import com.lanwei.governmentstar.activity.spsq.OtherHyspSpActivity;
import com.lanwei.governmentstar.activity.spsq.OtherReceptionCsActivity;
import com.lanwei.governmentstar.activity.spsq.OtherReceptionSpActivity;
import com.lanwei.governmentstar.activity.spsq.OtherTyspCsActivity;
import com.lanwei.governmentstar.activity.spsq.OtherTyspSpActivity;
import com.lanwei.governmentstar.activity.spsq.OutCarCsActivity;
import com.lanwei.governmentstar.activity.spsq.OutCarSpActivity;
import com.lanwei.governmentstar.activity.spsq.OutOtherCsActivity;
import com.lanwei.governmentstar.activity.spsq.OutOtherSpActivity;
import com.lanwei.governmentstar.activity.spsq.OutPublicCsActivity;
import com.lanwei.governmentstar.activity.spsq.OutPublicSpActivity;
import com.lanwei.governmentstar.activity.spsq.OutReimburseCsActivity;
import com.lanwei.governmentstar.activity.spsq.OutReimburseSpActivity;
import com.lanwei.governmentstar.activity.spsq.PersonCadreCsActivity;
import com.lanwei.governmentstar.activity.spsq.PersonCadreSpActivity;
import com.lanwei.governmentstar.activity.spsq.PersonJobsCsActivity;
import com.lanwei.governmentstar.activity.spsq.PersonJobsSpActivity;
import com.lanwei.governmentstar.activity.spsq.PersonLeaveCsActivity;
import com.lanwei.governmentstar.activity.spsq.PersonLeaveSpActivity;
import com.lanwei.governmentstar.activity.spsq.PersonOthersCsActivity;
import com.lanwei.governmentstar.activity.spsq.PersonOthersSpActivity;
import com.lanwei.governmentstar.activity.spsq.PersonOutCsActivity;
import com.lanwei.governmentstar.activity.spsq.PersonOutSpActivity;
import com.lanwei.governmentstar.activity.spsq.ProjectContractCsActivity;
import com.lanwei.governmentstar.activity.spsq.ProjectContractSpActivity;
import com.lanwei.governmentstar.activity.spsq.ProjectCsActivity;
import com.lanwei.governmentstar.activity.spsq.ProjectDepartmentCsActivity;
import com.lanwei.governmentstar.activity.spsq.ProjectDepartmentSpActivity;
import com.lanwei.governmentstar.activity.spsq.ProjectOtherCsActivity;
import com.lanwei.governmentstar.activity.spsq.ProjectOtherSpActivity;
import com.lanwei.governmentstar.activity.spsq.ProjectPointCsActivity;
import com.lanwei.governmentstar.activity.spsq.ProjectPointSpActivity;
import com.lanwei.governmentstar.activity.spsq.ProjectSpActivity;
import com.lanwei.governmentstar.activity.spsq.QjApplyCsActivity;
import com.lanwei.governmentstar.activity.spsq.QjApplySpActivity;
import com.lanwei.governmentstar.activity.spsq.SealLicenseCsActivity;
import com.lanwei.governmentstar.activity.spsq.SealLicenseSpActivity;
import com.lanwei.governmentstar.activity.spsq.SealOtherCsActivity;
import com.lanwei.governmentstar.activity.spsq.SealOtherSpActivity;
import com.lanwei.governmentstar.activity.spsq.SealUsesealCsActivity;
import com.lanwei.governmentstar.activity.spsq.SealUsesealSpActivity;
import com.lanwei.governmentstar.activity.spsq.SpsqFlowActivity;
import com.lanwei.governmentstar.activity.spsq.ThingsCustomPurchaseCsActivity;
import com.lanwei.governmentstar.activity.spsq.ThingsCustomPurchaseSpActivity;
import com.lanwei.governmentstar.activity.spsq.ThingsOtherPurchaseCsActivity;
import com.lanwei.governmentstar.activity.spsq.ThingsOtherPurchaseSpActivity;
import com.lanwei.governmentstar.activity.spsq.ThingsPurchaseApplyCsActivity;
import com.lanwei.governmentstar.activity.spsq.ThingsPurchaseApplySpActivity;
import com.lanwei.governmentstar.activity.spsq.ThingsReceiveCsActivity;
import com.lanwei.governmentstar.activity.spsq.ThingsReceiveSpActivity;
import com.lanwei.governmentstar.activity.spsq.ThingsRentCsActivity;
import com.lanwei.governmentstar.activity.spsq.ThingsRentSpActivity;
import com.lanwei.governmentstar.bean.SpsqApplyList;
import com.lanwei.governmentstar.utils.GetAccount;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/8/30/030.
 */

public class ApplyAdapter extends SwipeMenuAdapter<ApplyAdapter.DefaultViewHolder> {
    OnItemClickListener mOnItemClickListener;
    TextView creatName, tv_tag, spsx, szbm, fqtime, line1, textview1, things;
    LinearLayout myapply, department ,all;
    View line;
//    ImageView csd;

    private Activity activity;
    private String search;
    private List<SpsqApplyList.DataBean> datalist;
    private int thisPosition = -1;
    private int clickPosition = -1;   //定义一个索引记录点击的条目
    private String userId;
    private int type;

    public ApplyAdapter(int type, List<SpsqApplyList.DataBean> datalist, String s, ApplyActivity activity) {
        this.type = type;
        this.activity = activity;
        this.datalist = datalist;
        this.search = s;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
//        View view = null;
//        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_apply, parent, false);

//        if (type == 1 || type == 2) {
//        } else if (type == 3) {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_apply, parent, false);
//        }
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_apply, parent, false);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ApplyAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        creatName = (TextView) realContentView.findViewById(R.id.creat_name);
        tv_tag = (TextView) realContentView.findViewById(R.id.tv_tag);
        myapply = (LinearLayout) realContentView.findViewById(R.id.myapply);
        spsx = (TextView) realContentView.findViewById(R.id.spsx);
        szbm = (TextView) realContentView.findViewById(R.id.szbm);
        fqtime = (TextView) realContentView.findViewById(R.id.fqtime);
        line = realContentView.findViewById(R.id.line);
        line1 = (TextView) realContentView.findViewById(R.id.line1);

        textview1 = (TextView) realContentView.findViewById(R.id.textview1);
        things = (TextView) realContentView.findViewById(R.id.things);
        department = (LinearLayout) realContentView.findViewById(R.id.department);
        all = (LinearLayout) realContentView.findViewById(R.id.all_person);
//        csd = (ImageView) realContentView.findViewById(R.id.csd);


        ApplyAdapter.DefaultViewHolder viewHolder = new ApplyAdapter.DefaultViewHolder(realContentView);
        viewHolder.mOnItemClickListener = mOnItemClickListener;

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ApplyAdapter.DefaultViewHolder holder, final int position) {


        if (thisPosition != position) {
            holder.line1.setVisibility(View.INVISIBLE);
        }else{
            holder.line1.setVisibility(View.VISIBLE);
        }
//
        String opCreateName = datalist.get(position).getPersonName();
        final String opId = datalist.get(position).getOpId();
        final String opTypeName = datalist.get(position).getName();
        final String opState = datalist.get(position).getState();
        final String opStatus = datalist.get(position).getStatus();
        String opTime = datalist.get(position).getTime();
        String deptName = datalist.get(position).getDeptName();

//        holder.creatName.setText(datalist.get(position).getPersonName());
        holder.spsx.setText(datalist.get(position).getName());
        holder.szbm.setText(datalist.get(position).getDeptName());
        holder.fqtime.setText(datalist.get(position).getTime());
        SpannableString spannableString = matcherSearchText(Color.parseColor("#df1214"), opCreateName, search);
        holder.creatName.setText(spannableString);

        switch (datalist.get(position).getStatus()){

            case "1":
                holder.tv_tag.setText("状态：等待审定");
                if(datalist.get(position).getState().equals("1")){
//                    holder.tv_tag.setBackgroundResource(R.drawable.apply_round_blue);
                    holder.tv_tag.setTextColor(activity.getResources().getColor(R.color.blue_text_color));
                }else{
//                    holder.tv_tag.setBackgroundResource(R.drawable.apply_round_gray);
                    holder.tv_tag.setTextColor(activity.getResources().getColor(R.color.gray_text_color));
                }

                break;

            case "3":
                holder.tv_tag.setText("状态：等待审核");
                if(datalist.get(position).getState().equals("1")){
//                    holder.tv_tag.setBackgroundResource(R.drawable.apply_round_blue);
                    holder.tv_tag.setTextColor(activity.getResources().getColor(R.color.blue_text_color));
                }else{
//                    holder.tv_tag.setBackgroundResource(R.drawable.apply_round_gray);
                    holder.tv_tag.setTextColor(activity.getResources().getColor(R.color.gray_text_color));
                }

                break;

            case "4":
                holder.tv_tag.setText("状态：等待批准");
                if(datalist.get(position).getState().equals("1")){
//                    holder.tv_tag.setBackgroundResource(R.drawable.apply_round_blue);
                    holder.tv_tag.setTextColor(activity.getResources().getColor(R.color.blue_text_color));
                }else{
//                    holder.tv_tag.setBackgroundResource(R.drawable.apply_round_gray);
                    holder.tv_tag.setTextColor(activity.getResources().getColor(R.color.gray_text_color));
                }

                break;

            case "6":
                if(datalist.get(position).getState().equals("1")){
//                    holder.tv_tag.setBackgroundResource(R.drawable.apply_round_blue);
                    holder.tv_tag.setTextColor(activity.getResources().getColor(R.color.blue_text_color));
                    holder.tv_tag.setText("状态：等待查看");
                }else{
//                    holder.tv_tag.setBackgroundResource(R.drawable.apply_round_gray);
                    holder.tv_tag.setTextColor(activity.getResources().getColor(R.color.gray_text_color));
                    holder.tv_tag.setText("状态：已查看");
                }

                break;

            case "0":
//                    holder.tv_tag.setBackgroundResource(R.drawable.apply_round_gray);
                    holder.tv_tag.setTextColor(activity.getResources().getColor(R.color.gray_text_color));
                    holder.tv_tag.setText("状态：未通过");

                break;

            case "5":
//                    holder.tv_tag.setBackgroundResource(R.drawable.apply_round_gray);
                    holder.tv_tag.setTextColor(activity.getResources().getColor(R.color.gray_text_color));
                    holder.tv_tag.setText("状态：已通过");
                break;

            case "8":
//                    holder.tv_tag.setBackgroundResource(R.drawable.apply_round_gray);
                    holder.tv_tag.setTextColor(activity.getResources().getColor(R.color.gray_text_color));
                    holder.tv_tag.setText("状态：未批准");
                break;

            default:

                break;

        }
//
//        if (opTypeName != null) {
//            holder.spsx.setText(opTypeName);
//        }
//
//        if (opTime != null) {
//            holder.fqtime.setText(opTime);
//        }
//
//        if (deptName != null) {
//            holder.szbm.setText(deptName);
//        }
//
//        SpannableString spannableString = matcherSearchText(Color.parseColor("#df1214"), opCreateName, search);
//        holder.creatName.setText(spannableString);
//
//        if (opState != null) {
//            if (opState.equals("0")) {   //没有权限审核
//                holder.textview1.setText("发起者");
//                holder.things.setText("审批事项");
//                holder.department.setVisibility(View.VISIBLE);
//                holder.csd.setVisibility(View.GONE);
//                holder.tv_tag.setBackgroundResource(R.drawable.apply_round_gray);
//                holder.tv_tag.setTextColor(activity.getResources().getColor(R.color.gray_text_color));
//            } else if (opState.equals("1")) {  //有权限审核
//                holder.textview1.setText("审批人");
//                holder.things.setText("申请事项");
//                holder.department.setVisibility(View.GONE);
//                holder.csd.setVisibility(View.GONE);
//                if (opStatus != null) {
//                    if (opStatus.equals("0")) {
//                        holder.tv_tag.setBackgroundResource(R.drawable.apply_round_red);
//                        holder.tv_tag.setTextColor(activity.getResources().getColor(R.color.red_text_color));
//                    } else {
//                        holder.tv_tag.setBackgroundResource(R.drawable.apply_round_blue);
//                        holder.tv_tag.setTextColor(activity.getResources().getColor(R.color.blue_text_color));
//                    }
//                }
//            } else if (opState.equals("2")) {   //抄送人查看权限
//                holder.textview1.setText("发起者");
//                holder.things.setText("审批事项");
//                holder.department.setVisibility(View.VISIBLE);
//                holder.csd.setVisibility(View.VISIBLE);
//                holder.tv_tag.setBackgroundResource(R.drawable.apply_round_yellow);
//                holder.tv_tag.setTextColor(activity.getResources().getColor(R.color.yellow_text_color));
//            } else {
//                holder.tv_tag.setBackgroundResource(R.drawable.apply_round_red);
//                holder.tv_tag.setTextColor(activity.getResources().getColor(R.color.red_text_color));
//            }
//        } else {
//            holder.textview1.setText("发起者");
//            holder.things.setText("审批事项");
//            holder.department.setVisibility(View.VISIBLE);
//            holder.csd.setVisibility(View.GONE);
//        }

//
//        if (opStatus != null) {
//            holder.tv_tag.setVisibility(View.VISIBLE);
//            switch (opStatus) {
//                case "0":
//                    holder.tv_tag.setText("  未通过  ");
//                    break;
//                case "1":
////                holder.tv_tag.setBackgroundResource(R.drawable.apply_round_blue);
////                holder.tv_tag.setTextColor(activity.getResources().getColor(R.color.blue_text_color));
//                    holder.tv_tag.setText("等待审定");
//                    break;
//                case "2":
////                holder.tv_tag.setBackgroundResource(R.drawable.apply_round_blue);
////                holder.tv_tag.setTextColor(activity.getResources().getColor(R.color.blue_text_color));
//                    holder.tv_tag.setText("等待审核");
//                    break;
//                case "3":
////                holder.tv_tag.setBackgroundResource(R.drawable.apply_round_blue);
////                holder.tv_tag.setTextColor(activity.getResources().getColor(R.color.blue_text_color));
//                    holder.tv_tag.setText("等待审阅");
//                    break;
//                case "4":
////                holder.tv_tag.setBackgroundResource(R.drawable.apply_round_blue);
////                holder.tv_tag.setTextColor(activity.getResources().getColor(R.color.blue_text_color));
//                    holder.tv_tag.setText("等待审批");
//                    break;
//                case "5":
////                holder.tv_tag.setBackgroundResource(R.drawable.apply_round_gray);
////                holder.tv_tag.setTextColor(activity.getResources().getColor(R.color.gray_text_color));
//                    holder.tv_tag.setText("  已通过  ");
//                    break;
//                case "6":
////                holder.tv_tag.setBackgroundResource(R.drawable.apply_round_yellow);
////                holder.tv_tag.setTextColor(activity.getResources().getColor(R.color.yellow_text_color));
//                    holder.tv_tag.setText("  立即查看  ");
//                    break;
//                case "7":
////                holder.tv_tag.setBackgroundResource(R.drawable.apply_round_gray);
////                holder.tv_tag.setTextColor(activity.getResources().getColor(R.color.gray_text_color));
//                    holder.tv_tag.setText("  已查看  ");
//                    break;
//                case "8":
////                holder.tv_tag.setBackgroundResource(R.drawable.apply_round_red);
////                holder.tv_tag.setTextColor(activity.getResources().getColor(R.color.red_text_color));
//                    holder.tv_tag.setText("  未批准  ");
//                    break;
//                default:
//                    holder.tv_tag.setText("  已通过  ");
////                holder.tv_tag.setBackgroundResource(R.drawable.apply_round_gray);
////                holder.tv_tag.setTextColor(activity.getResources().getColor(R.color.gray_text_color));
//                    break;
//            }
//        } else {
//            holder.tv_tag.setVisibility(View.GONE);
//        }

        holder.all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (thisPosition != -1) {
                    clickPosition = thisPosition;
                    notifyItemChanged(clickPosition);
                }
                thisPosition = holder.getLayoutPosition();
                holder.line1.setVisibility(View.VISIBLE);

                Intent intent2 = new Intent();
                Intent intent = null;
                switch (datalist.get(position).getStatus()){
                    case "1":
                    case "3":
                    case "4":
                      intent = intentSp(intent2, opTypeName ,opState);  //审批人界面跳转
                        break;

                    case "6":
                        datalist.get(position).setState("0");
                        notifyDataSetChanged();
                        intent = intentCs(intent2, opTypeName ,"0");   //抄送人界面跳转
                        break;

                    case "0":
                    case "5":
                    case "8":
                        intent = intentSp(intent2, opTypeName ,"0");  //审批人界面跳转
                        break;
                }

                int opStatus1 = Integer.parseInt(opStatus);
                userId = new GetAccount(activity).opId();
                Log.d("bb", opStatus);

                if (intent != null){
                    intent.putExtra("opId", opId);
                    intent.putExtra("userId", userId);
                    activity.startActivityForResult(intent,6);
                }
            }
        });

    }

    private Intent intentSp(Intent intent, String opTypeName ,String opState) {
        switch (opTypeName) {
            case "资质印章-其他":
                intent = new Intent(activity, SealOtherSpActivity.class);
                break;
            case "资质印章-机构证照申请":
                intent = new Intent(activity, SealLicenseSpActivity.class);
                break;
            case "资质印章-用印申请":
                intent = new Intent(activity, SealUsesealSpActivity.class);
                break;


            case "项目申请-其他":
                intent = new Intent(activity, ProjectOtherSpActivity.class);
                break;
            case "项目申请-立项申请":
                intent = new Intent(activity, ProjectSpActivity.class);
                break;
            case "项目申请-合同审批":
                intent = new Intent(activity, ProjectContractSpActivity.class);
                break;
            case "项目申请-工作指示":
                intent = new Intent(activity, ProjectPointSpActivity.class);
                break;
            case "项目申请-部门协作":
                intent = new Intent(activity, ProjectDepartmentSpActivity.class);
                break;


            case "资金申请-付款申请":
                intent = new Intent(activity, CapitalPaySpActivity.class);
                break;
            case "资金申请-备用金申请":
                intent = new Intent(activity, CapitalOtherSpActivity.class);
                break;
            case "资金申请-报销申请":
                intent = new Intent(activity, CapitalBxSpActivity.class);
                break;


            case "请假申请-病假":
            case "请假申请-事假":
            case "请假申请-婚假":
            case "请假申请-丧假":
            case "请假申请-产假":
            case "请假申请-年休假":
                intent = new Intent(activity, QjApplySpActivity.class);
                break;

            case "人事申请-其他":
                intent = new Intent(activity, PersonOthersSpActivity.class);
                break;
            case "人事申请-招聘申请":
                intent = new Intent(activity, PersonJobsSpActivity.class);
                break;
            case "人事申请-调派申请":
                intent = new Intent(activity, PersonOutSpActivity.class);
                break;
            case "人事申请-离职申请":
                intent = new Intent(activity, PersonLeaveSpActivity.class);
                break;
            case "人事申请-后备干部申请":
                intent = new Intent(activity, PersonCadreSpActivity.class);
                break;

            case "外出申请-其他":
                intent = new Intent(activity, OutOtherSpActivity.class);
                break;
            case "外出申请-用车申请":
                intent = new Intent(activity, OutCarSpActivity.class);
                break;
            case "外出申请-执行公务申请":
                intent = new Intent(activity, OutPublicSpActivity.class);
                break;
            case "外出申请-出差报销申请":
                intent = new Intent(activity, OutReimburseSpActivity.class);
                break;


            case "物品申请-租用申请":
                intent = new Intent(activity, ThingsRentSpActivity.class);
                break;
            case "物品申请-其他采购":
                intent = new Intent(activity, ThingsOtherPurchaseSpActivity.class);
                break;
            case "物品申请-物品领用":
                intent = new Intent(activity, ThingsReceiveSpActivity.class);
                break;
            case "物品申请-申请采购":
                intent = new Intent(activity, ThingsPurchaseApplySpActivity.class);
                break;
            case "物品申请-定制采购":
                intent = new Intent(activity, ThingsCustomPurchaseSpActivity.class);
                break;

            case "其他申请-通用审批":
                intent = new Intent(activity, OtherTyspSpActivity.class);
                break;
            case "其他申请-接待申请":
                intent = new Intent(activity, OtherReceptionSpActivity.class);
                break;
            case "其他申请-活动申请":
                intent = new Intent(activity, OtherActivitySpActivity.class);
                break;
            case "其他申请-会议审批":
                intent = new Intent(activity, OtherHyspSpActivity.class);
                break;
        }
        intent.putExtra("type",opState);
        return intent;
    }

    private Intent intentCs(Intent intent, String opTypeName ,String opState) {
        switch (opTypeName) {
            case "资质印章-其他":
                intent = new Intent(activity, SealOtherCsActivity.class);
                break;
            case "资质印章-机构证照申请":
                intent = new Intent(activity, SealLicenseCsActivity.class);
                break;
            case "资质印章-用印申请":
                intent = new Intent(activity, SealUsesealCsActivity.class);
                break;

            case "项目申请-其他":
                intent = new Intent(activity, ProjectOtherCsActivity.class);
                break;
            case "项目申请-立项申请":
                intent = new Intent(activity, ProjectCsActivity.class);
                break;
            case "项目申请-合同审批":
                intent = new Intent(activity, ProjectContractCsActivity.class);
                break;
            case "项目申请-工作指示":
                intent = new Intent(activity, ProjectPointCsActivity.class);
                break;
            case "项目申请-部门协作":
                intent = new Intent(activity, ProjectDepartmentCsActivity.class);
                break;


            case "资金申请-付款申请":
                intent = new Intent(activity, CapitalPayCsActivity.class);
                break;
            case "资金申请-备用金申请":
                intent = new Intent(activity, CapitalOtherCsActivity.class);
                break;
            case "资金申请-报销申请":
                intent = new Intent(activity, CapitalBxCsActivity.class);
                break;

            case "请假申请-病假":
            case "请假申请-事假":
            case "请假申请-婚假":
            case "请假申请-丧假":
            case "请假申请-产假":
            case "请假申请-年休假":
                intent = new Intent(activity, QjApplyCsActivity.class);
                break;

            case "人事申请-其他":
                intent = new Intent(activity, PersonOthersCsActivity.class);
                break;

            case "人事申请-招聘申请":
                intent = new Intent(activity, PersonJobsCsActivity.class);
                break;
            case "人事申请-调派申请":
                intent = new Intent(activity, PersonOutCsActivity.class);
                break;
            case "人事申请-离职申请":
                intent = new Intent(activity, PersonLeaveCsActivity.class);
                break;
            case "人事申请-后备干部申请":
                intent = new Intent(activity, PersonCadreCsActivity.class);
                break;

            case "外出申请-其他":
                intent = new Intent(activity, OutOtherCsActivity.class);
                break;
            case "外出申请-用车申请":
                intent = new Intent(activity, OutCarCsActivity.class);
                break;
            case "外出申请-公务出行申请":
                intent = new Intent(activity, OutPublicCsActivity.class);
                break;
            case "外出申请-出差报销申请":
                intent = new Intent(activity, OutReimburseCsActivity.class);
                break;


            case "物品申请-租用申请":
                intent = new Intent(activity, ThingsRentCsActivity.class);
                break;
            case "物品申请-其他采购":
                intent = new Intent(activity, ThingsOtherPurchaseCsActivity.class);
                break;
            case "物品申请-物品领用":
                intent = new Intent(activity, ThingsReceiveCsActivity.class);
                break;
            case "物品申请-申请采购":
                intent = new Intent(activity, ThingsPurchaseApplyCsActivity.class);
                break;
            case "物品申请-定制采购":
                intent = new Intent(activity, ThingsCustomPurchaseCsActivity.class);
                break;

            case "其他申请-通用审批":
                intent = new Intent(activity, OtherTyspCsActivity.class);
                break;
            case "其他申请-接待申请":
                intent = new Intent(activity, OtherReceptionCsActivity.class);
                break;
            case "其他申请-活动申请":
                intent = new Intent(activity, OtherActivityCsActivity.class);
                break;
            case "其他申请-会议审批":
                intent = new Intent(activity, OtherHyspCsActivity.class);
                break;
        }
        intent.putExtra("type",opState);
        return intent;
    }


    @Override
    public int getItemCount() {
        return datalist == null ? 0 : datalist.size();
    }

    class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnItemClickListener mOnItemClickListener;
        TextView creatName, tv_tag, spsx, szbm, fqtime, line1, textview1, things;
        LinearLayout myapply, department ,all;
        View line;
        ImageView csd;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            creatName = (TextView) itemView.findViewById(R.id.creat_name);
            tv_tag = (TextView) itemView.findViewById(R.id.tv_tag);
            spsx = (TextView) itemView.findViewById(R.id.spsx);
            szbm = (TextView) itemView.findViewById(R.id.szbm);
            fqtime = (TextView) itemView.findViewById(R.id.fqtime);
            line = itemView.findViewById(R.id.line);
            line1 = (TextView) itemView.findViewById(R.id.line1);

            myapply = (LinearLayout) itemView.findViewById(R.id.myapply);
            all = (LinearLayout) itemView.findViewById(R.id.all_person);

            textview1 = (TextView) itemView.findViewById(R.id.textview1);
            things = (TextView) itemView.findViewById(R.id.things);
            department = (LinearLayout) itemView.findViewById(R.id.department);
//            csd = (ImageView) itemView.findViewById(R.id.csd);


        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

    //匹配搜索的关键字
    private SpannableString matcherSearchText(int color, String text, String keyword) {
        SpannableString ss = new SpannableString(text);
        Pattern pattern = Pattern.compile(keyword);
        Matcher matcher = pattern.matcher(ss);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            ss.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }

}
