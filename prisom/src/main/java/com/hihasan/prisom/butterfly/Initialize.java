package com.hihasan.prisom.butterfly;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Initialize
{
    static void now(@NonNull Butterfly butterfly) {
        morpheusLayoutSetup(butterfly);
        setupTextView(butterfly);
        setupBackground(butterfly);
        setupTextColor(butterfly);
        setupImageView(butterfly);
        setupVisibility(butterfly);
        setupAnimView(butterfly);
        setupClickListener(butterfly);
        setupViewTags(butterfly);
        setupStaticListeners(butterfly);
    }

    private static void morpheusLayoutSetup(@NonNull Butterfly butterfly) {
        if (butterfly.builder.layoutResID != 0) {
            butterfly.setContentView(butterfly.builder.layoutResID);
        }
    }

    private static void setupStaticListeners(@NonNull Butterfly butterfly) {
        if (butterfly.builder.onCancelListener != null) {
            butterfly.setOnCancelListener(butterfly);
        }

        if (butterfly.builder.onDismissListener != null) {
            butterfly.setOnDismissListener(butterfly);
        }

        if (butterfly.builder.onShowListener != null) {
            butterfly.setOnShowListener(butterfly);
        }
    }

    private static void setupViewTags(@NonNull Butterfly butterfly) {
        final Butterfly.Builder builder = butterfly.builder;
        if (builder != null) {
            final SparseArray<Butterfly.Tag> contentTag = builder.contentTag;
            if (contentTag != null && contentTag.size() > 0) {
                for (int i = 0; i < contentTag.size(); i++) {
                    int key = contentTag.keyAt(i);
                    final Butterfly.Tag tag = contentTag.get(key);
                    final View view = butterfly.findViewById(key);

                    if (view != null) {
                        if (tag.getKey() != 0 && tag.getTag() != null) {
                            view.setTag(tag.getKey(), tag.getTag());
                        } else if (tag.getTag() != null) {
                            view.setTag(tag.getTag());
                        }
                    }
                }
            }
        }
    }

    private static void setupClickListener(final @NonNull Butterfly butterfly) {
        final Butterfly.Builder builder = butterfly.builder;
        if (builder != null) {
            final SparseArray<Butterfly.OnClickListener> contentClickListener = builder.contentClickListener;
            if (contentClickListener != null && contentClickListener.size() > 0) {
                for (int i = 0; i < contentClickListener.size(); i++) {
                    final int key = contentClickListener.keyAt(i);
                    final View view = butterfly.findViewById(key);
                    if (view != null) {
                        view.setOnClickListener(butterfly);
                    }
                }
            }
        }
    }

    private static void setupTextView(@NonNull Butterfly butterfly) {
        final Butterfly.Builder builder = butterfly.builder;
        if (builder != null) {
            final SparseArray<CharSequence> contentText = builder.contentText;
            if (contentText != null && contentText.size() > 0) {
                for (int i = 0; i < contentText.size(); i++) {
                    final int key = contentText.keyAt(i);
                    final View view = butterfly.findViewById(key);

                    if (view != null && view instanceof TextView) {
                        final TextView textView = (TextView) view;
                        textView.setText(contentText.get(key));

                        final SparseArray<Typeface> contentTypeFace = builder.contentTypeFace;
                        if (contentTypeFace != null && contentTypeFace.size() > 0) {
                            textView.setTypeface(contentTypeFace.get(view.getId()));
                        }
                    }
                }
            }
        }
    }

    private static void setupVisibility(@NonNull Butterfly butterfly) {
        final Butterfly.Builder builder = butterfly.builder;
        if (builder != null) {
            final SparseIntArray contentVisibility = builder.contentVisibility;
            if (contentVisibility != null && contentVisibility.size() > 0) {
                for (int i = 0; i < contentVisibility.size(); i++) {
                    final int key = contentVisibility.keyAt(i);
                    final View view = butterfly.findViewById(key);

                    if (view != null) {
                        final int visibilityValue = contentVisibility.get(key);
                        view.setVisibility(visibilityValue);
                    }
                }
            }
        }
    }

    private static void setupBackground(@NonNull Butterfly butterfly) {
        final Butterfly.Builder builder = butterfly.builder;
        if (builder != null) {
            final SparseIntArray contentImageButton = builder.contentBackground;
            if (contentImageButton != null && contentImageButton.size() > 0) {
                for (int i = 0; i < contentImageButton.size(); i++) {
                    final int key = contentImageButton.keyAt(i);
                    final View view = butterfly.findViewById(key);

                    if (view != null) {
                        final int imageRes = contentImageButton.get(key);
                        if (imageRes != 0) {
                            view.setBackgroundResource(imageRes);
                        }
                    }
                }
            }
        }
    }

    private static void setupTextColor(@NonNull Butterfly butterfly) {
        final Butterfly.Builder builder = butterfly.builder;
        if (builder != null) {
            final SparseIntArray contentTextColor = builder.contentTextColor;
            if (contentTextColor != null && contentTextColor.size() > 0) {
                for (int i = 0; i < contentTextColor.size(); i++) {
                    final int key = contentTextColor.keyAt(i);
                    final View view = butterfly.findViewById(key);

                    if (view != null && view instanceof TextView) {
                        final TextView textView = (TextView) view;
                        final int textColorRes = ContextCompat.getColor(butterfly.getContext(), contentTextColor.get(key));
                        if (textColorRes != 0) {
                            textView.setTextColor(textColorRes);
                        }
                    }
                }
            }
        }
    }

    private static void setupImageView(@NonNull Butterfly butterfly) {
        final Butterfly.Builder builder = butterfly.builder;
        if (builder != null) {
            final SparseIntArray contentImage = builder.contentImage;
            if (contentImage != null && contentImage.size() > 0) {
                for (int i = 0; i < contentImage.size(); i++) {
                    final int key = contentImage.keyAt(i);
                    final View view = butterfly.findViewById(key);

                    if (view != null && view instanceof ImageView) {
                        final ImageView imageView = (ImageView) view;
                        imageView.setImageResource(contentImage.get(key));
                    }
                }
            }

            final SparseArray<Bitmap> contentBitmap = builder.contentBitmap;
            if (contentBitmap != null && contentBitmap.size() > 0) {
                for (int i = 0; i < contentBitmap.size(); i++) {
                    final int key = contentBitmap.keyAt(i);
                    final View view = butterfly.findViewById(key);
                    final Bitmap bitmap = contentBitmap.valueAt(i);

                    if (view != null && view instanceof ImageView && bitmap != null) {
                        final ImageView imageView = (ImageView) view;
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }
        }

    }

    private static void setupAnimView(@NonNull Butterfly butterfly) {
        final Butterfly.Builder builder = butterfly.builder;
        if (builder != null) {
            final SparseIntArray contentAnimation = builder.contentAnimation;
            if (contentAnimation != null && contentAnimation.size() > 0) {
                for (int i = 0; i < contentAnimation.size(); i++) {
                    final int key = contentAnimation.keyAt(i);
                    final View view = butterfly.findViewById(key);
                    if (view != null) {
                        final Animation animation = AnimationUtils.loadAnimation(builder.context, contentAnimation.get(key));
                        view.startAnimation(animation);

                        final SparseArray<Animation.AnimationListener> contentAnimationListener = builder.contentAnimationListener;
                        if (contentAnimationListener != null && contentAnimationListener.size() > 0) {
                            final Animation.AnimationListener animationListener = contentAnimationListener.get(view.getId());
                            animation.setAnimationListener(animationListener);
                        }
                    }
                }
            }
        }
    }

    static void startAnimation(Butterfly butterfly) {
        if (butterfly != null) {
            setupAnimView(butterfly);
        }
    }
}
