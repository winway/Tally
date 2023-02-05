package com.example.tally.common;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.example.tally.R;

/**
 * @PackageName: com.example.tally.common
 * @ClassName: MyKeyBoard
 * @Author: winwa
 * @Date: 2023/2/1 8:29
 * @Description:
 **/
public class MyKeyBoard {
    private final Keyboard mKeyboard;
    private KeyboardView mKeyboardView;
    private EditText mEditText;

    private OnKeyboardOKPressedListener mOKPressedListener;

    private KeyboardView.OnKeyboardActionListener mActionListener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int i) {

        }

        @Override
        public void onRelease(int i) {

        }

        @Override
        public void onKey(int i, int[] ints) {
            Editable text = mEditText.getText();
            int selectionStart = mEditText.getSelectionStart();

            switch (i) {
                case Keyboard.KEYCODE_DELETE: // 删除
                    if (text != null && text.length() > 0) {
                        if (selectionStart > 0) {
                            text.delete(selectionStart - 1, selectionStart);
                        }
                    }
                    break;
                case Keyboard.KEYCODE_DONE: // 确定
                    if (mOKPressedListener != null) {
                        mOKPressedListener.onOK();
                    }
                    break;
                case Keyboard.KEYCODE_CANCEL: // 清零
                    text.clear();
                    break;
                default:
                    text.insert(selectionStart, Character.toString((char) i));
                    break;
            }

        }

        @Override
        public void onText(CharSequence charSequence) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };

    public MyKeyBoard(KeyboardView keyboardView, EditText editText) {
        mKeyboardView = keyboardView;
        mEditText = editText;
        mEditText.setInputType(InputType.TYPE_NULL);

        mKeyboard = new Keyboard(mEditText.getContext(), R.xml.keys);
        mKeyboardView.setKeyboard(mKeyboard);

        mKeyboardView.setEnabled(true);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setOnKeyboardActionListener(mActionListener);
    }

    public void setOKPressedListener(OnKeyboardOKPressedListener OKPressedListener) {
        mOKPressedListener = OKPressedListener;
    }

    public void show() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.INVISIBLE || visibility == View.GONE) {
            mKeyboardView.setVisibility(View.VISIBLE);
        }
    }

    public void hide() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.INVISIBLE || visibility == View.VISIBLE) {
            mKeyboardView.setVisibility(View.GONE);
        }
    }

    public interface OnKeyboardOKPressedListener {
        public void onOK();
    }
}
