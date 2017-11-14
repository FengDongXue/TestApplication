package lanwei.com.gesture_application;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.leo.extendedittext.ExtendEditText;
import com.leo.extendedittext.Rule;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by 蓝威科技-技术开发1 on 2017/11/9.
 */

public class EditorView_Activity extends AppCompatActivity {


    @InjectView(R.id.extend_edit_text)
    ExtendEditText extendEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_editor);
        ButterKnife.inject(this);
        extendEditText.enableHistory(true); // 开启历史记录
        extendEditText.setRule(Rule.EXCLUSIVE_INCLUSIVE);
        extendEditText.cover()
                .bold()
                .italic()
                .underline()
                .strikethrough()
                .link()
                .bullet()
                .quote()
                .action();


    }
}
