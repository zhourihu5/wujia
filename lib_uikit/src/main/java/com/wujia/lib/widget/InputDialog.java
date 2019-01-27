package com.wujia.lib.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wujia.lib.uikit.R;


/**
 * Created by shenbingkai on 19/1/25 下午5:48
 * Email shenbingkai@163.com
 * 带输入框的dialog
 */
public class InputDialog extends Dialog implements View.OnClickListener {
    private final EditText inputEt;
    private OnInputDialogListener listener;

    public InputDialog(Context context, String title, String hint, String confim) {
//        super(context, R.style.NormalDialogBottomStyle);
        super(context);
        //初始化布局
        setContentView(R.layout.layout_dialog_input);
//        Window dialogWindow = getWindow();
//        dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialogWindow.setGravity(Gravity.CENTER);
//        setCanceledOnTouchOutside(true);
//        setCancelable(false);

        TextView titleTv = findViewById(R.id.dialog_input_title);
        inputEt = findViewById(R.id.dialog_input);
        TextView confimBtn = findViewById(R.id.dialog_sure);

        confimBtn.setOnClickListener(this);
        findViewById(R.id.dialog_cancel).setOnClickListener(this);

        titleTv.setText(title);
        inputEt.setHint(hint);
        confimBtn.setText(confim);
    }

    @Override
    public void onClick(View v) {

        if (R.id.dialog_sure == v.getId()) {
            if (null != listener) {
                listener.dialogSureClick(inputEt.getText().toString());
            }
        }

        dismiss();
    }

    public interface OnInputDialogListener {
        void dialogSureClick(String input);

        void dialogCancelClick();
    }

    private void setListener(OnInputDialogListener listener) {
        this.listener = listener;
    }


    public static class Builder {
        private String title;
        private String hint;
        private String confirm;
        private OnInputDialogListener listener;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder hint(String hint) {
            this.hint = hint;
            return this;
        }

        public Builder confirm(String confirm) {
            this.confirm = confirm;
            return this;
        }

        public Builder listener(OnInputDialogListener listener) {
            this.listener = listener;
            return this;
        }

        public InputDialog build(Context context) {
            InputDialog dialog = new InputDialog(context, title, hint, confirm);
            dialog.setListener(listener);

            return dialog;
        }
    }
}
