package com.hihasan.prisom.tagy;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hihasan.prisom.R;
import com.hihasan.prisom.utils.TagAddCallback;
import com.hihasan.prisom.utils.TagDeletedCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tagy extends FrameLayout
        implements View.OnClickListener, TextView.OnEditorActionListener, View.OnKeyListener {

    private FlowLayout flowLayout;

    private EditText editText;

    private int tagViewLayoutRes;

    private int inputTagLayoutRes;

    private int deleteModeBgRes;

    private Drawable defaultTagBg;

    private boolean isEditableStatus = true;

    private TextView lastSelectTagView;

    private List<String> tagValueList = new ArrayList<>();

    private boolean isDelAction = false;

    private TagAddCallback tagAddCallBack;

    private TagDeletedCallback tagDeletedCallback;


    public Tagy(Context context) {
        this(context, null);
    }

    public Tagy(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Tagy(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.EditTag);
        tagViewLayoutRes =
                mTypedArray.getResourceId(R.styleable.EditTag_tag_layout, R.layout.activity_tag);
        inputTagLayoutRes = mTypedArray.getResourceId(R.styleable.EditTag_input_layout,
                R.layout.activity_default_tag);
        deleteModeBgRes =
                mTypedArray.getResourceId(R.styleable.EditTag_delete_mode_bg, R.color.blue_grey);
        mTypedArray.recycle();
        setupView();
    }

    private void setupView() {
        flowLayout = new FlowLayout(getContext());
        LayoutParams layoutParams =
                new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        flowLayout.setLayoutParams(layoutParams);
        addView(flowLayout);
        addInputTagView();
    }

    private void addInputTagView() {
        editText = createInputTag(flowLayout);
        editText.setTag(new Object());
        editText.setOnClickListener(this);
        setupListener();
        flowLayout.addView(editText);
        isEditableStatus = true;
    }

    private void setupListener() {
        editText.setOnEditorActionListener(this);
        editText.setOnKeyListener(this);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        boolean isHandle = false;
        if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
            String tagContent = editText.getText().toString();
            if (TextUtils.isEmpty(tagContent)) {
                int tagCount = flowLayout.getChildCount();
                if (lastSelectTagView == null && tagCount > 1) {
                    if (isDelAction) {
                        flowLayout.removeViewAt(tagCount - 2);
                        if (tagDeletedCallback != null) {
                            tagDeletedCallback.onTagDelete(tagValueList.get(tagCount - 2));
                        }
                        tagValueList.remove(tagCount - 2);
                        isHandle = true;
                    } else {
                        TextView delActionTagView = (TextView) flowLayout.getChildAt(tagCount - 2);
                        delActionTagView.setBackgroundDrawable(getDrawableByResId(deleteModeBgRes));
                        lastSelectTagView = delActionTagView;
                        isDelAction = true;
                    }
                } else {
                    removeSelectedTag();
                }
            } else {
                int length = tagContent.length();
                editText.getText().delete(length, length);
            }
        }
        return isHandle;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean isHandle = false;
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            String tagContent = editText.getText().toString();
            if (TextUtils.isEmpty(tagContent)) {
                // do nothing, or you can tip "can'nt add empty tag"
            } else {
                if (tagAddCallBack == null || (tagAddCallBack != null
                        && tagAddCallBack.onTagAdd(tagContent))) {
                    TextView tagTextView = createTag(flowLayout, tagContent);
                    if (defaultTagBg == null) {
                        defaultTagBg = tagTextView.getBackground();
                    }
                    tagTextView.setOnClickListener(Tagy.this);
                    flowLayout.addView(tagTextView, flowLayout.getChildCount() - 1);
                    tagValueList.add(tagContent);
                    // reset action status
                    editText.getText().clear();
                    editText.performClick();
                    isDelAction = false;
                    isHandle = true;
                }
            }
        }
        return isHandle;
    }

    @Override
    public void onClick(View view) {
        if (view.getTag() == null && isEditableStatus) {
            // TextView tag click
            if (lastSelectTagView == null) {
                lastSelectTagView = (TextView) view;
                view.setBackgroundDrawable(getDrawableByResId(deleteModeBgRes));
            } else {
                if (lastSelectTagView.equals(view)) {
                    lastSelectTagView.setBackgroundDrawable(defaultTagBg);
                    lastSelectTagView = null;
                } else {
                    lastSelectTagView.setBackgroundDrawable(defaultTagBg);
                    lastSelectTagView = (TextView) view;
                    view.setBackgroundDrawable(getDrawableByResId(deleteModeBgRes));
                }
            }
        } else {
            // EditText tag click
            if (lastSelectTagView != null) {
                lastSelectTagView.setBackgroundDrawable(defaultTagBg);
                lastSelectTagView = null;
            }
        }
    }

    private void removeSelectedTag() {
        int size = tagValueList.size();
        if (size > 0 && lastSelectTagView != null) {
            int index = flowLayout.indexOfChild(lastSelectTagView);
            tagValueList.remove(index);
            flowLayout.removeView(lastSelectTagView);
            if (tagDeletedCallback != null) {
                tagDeletedCallback.onTagDelete(lastSelectTagView.getText().toString());
            }
            lastSelectTagView = null;
            isDelAction = false;
        }
    }

    private TextView createTag(ViewGroup parent, String s) {
        TextView tagTv =
                (TextView) LayoutInflater.from(getContext()).inflate(tagViewLayoutRes, parent, false);
        tagTv.setText(s);
        return tagTv;
    }

    private EditText createInputTag(ViewGroup parent) {
        editText =
                (EditText) LayoutInflater.from(getContext()).inflate(inputTagLayoutRes, parent, false);
        return editText;
    }

    private void addTagView(List<String> tagList) {
        int size = tagList.size();
        for (int i = 0; i < size; i++) {
            addTag(tagList.get(i));
        }
    }

    private Drawable getDrawableByResId(int resId) {
        return getContext().getResources().getDrawable(resId);
    }

    public void setEditable(boolean editable) {
        if (editable) {
            if (!isEditableStatus) {
                flowLayout.addView((editText));
            }
        } else {
            int childCount = flowLayout.getChildCount();
            if (isEditableStatus && childCount > 0) {
                flowLayout.removeViewAt(childCount - 1);
                if (lastSelectTagView != null) {
                    lastSelectTagView.setBackgroundDrawable(defaultTagBg);
                    isDelAction = false;
                    editText.getText().clear();
                }
            }
        }
        this.isEditableStatus = editable;
    }

    public boolean addTag(String tagContent) {
        if (TextUtils.isEmpty(tagContent)) {
            // do nothing, or you can tip "can't add empty tag"
            return false;
        } else {
            if (tagAddCallBack == null || (tagAddCallBack != null
                    && tagAddCallBack.onTagAdd(tagContent))) {
                TextView tagTextView = createTag(flowLayout, tagContent);
                if (defaultTagBg == null) {
                    defaultTagBg = tagTextView.getBackground();
                }
                tagTextView.setOnClickListener(Tagy.this);
                if (isEditableStatus) {
                    flowLayout.addView(tagTextView, flowLayout.getChildCount() - 1);
                } else {
                    flowLayout.addView(tagTextView);
                }

                tagValueList.add(tagContent);
                // reset action status
                editText.getText().clear();
                editText.performClick();
                isDelAction = false;
                return true;
            }
        }
        return false;
    }

    public void setTagList(List<String> mTagList) {
        addTagView(mTagList);
    }

    public List<String> getTagList() {
        return tagValueList;
    }

    public void setTagAddCallBack(TagAddCallback tagAddCallBack) {
        this.tagAddCallBack = tagAddCallBack;
    }

    public void setTagDeletedCallback(TagDeletedCallback tagDeletedCallback) {
        this.tagDeletedCallback = tagDeletedCallback;
    }

    /*
     * Remove tag view by value
     * warning: this method will remove tags which has the same value
     */
    public void removeTag(String... tagValue) {
        List<String> tagValues = Arrays.asList(tagValue);
        int childCount = flowLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (tagValues.size() > 0) {
                View view = flowLayout.getChildAt(i);
                try {
                    String value = ((TextView) view).getText().toString();
                    if (tagValues.contains(value)) {
                        tagValueList.remove(value);
                        if (tagDeletedCallback != null) {
                            tagDeletedCallback.onTagDelete(value);
                        }
                        flowLayout.removeView(view);
                        i = 0;
                        childCount = flowLayout.getChildCount();
                        continue;
                    }
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
}
