package com.example.android.miwok;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class WordAdapter extends ArrayAdapter<Word> {
    public WordAdapter(@NonNull final Context context, @NonNull final List<Word> objects, final int backgroundColorId) {
        super(context, LIST_ITEM, objects);
        // ContextCompat.getColor()
        this.mBackgroundColor = context.getResources().getColor(backgroundColorId);
    }

    /*
    https://developer.android.com/reference/android/media/MediaPlayer.html
    */

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            // warning: attachToRoot must be false
            Log.d(TAG, String.format("LayoutInflater for position %1$s", position));
            view = LayoutInflater.from(getContext()).inflate(LIST_ITEM, parent, false);
        }
        clearView(view);


        ViewGroup viewGroup = view.findViewById(R.id.text_container);
        TextView miwokWord = view.findViewById(R.id.miwok_text_view);
        TextView defaultWord = view.findViewById(R.id.default_text_view);
        ImageView imageView = view.findViewById(R.id.image);

        final Word word = getItem(position);
        if (word != null) {
            defaultWord.setText(word.getDefaultTranslation());
            miwokWord.setText(word.getMiwokTranslation());
            if (word.hasImage()) {
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageResource(word.getImageResourceId());
            }
        }


        /*
         * put a dark rectangle behind the view and set the view's opacity. This saves painting the rectangle when the view is 100% opaque.
         * imageView.setColorFilter(Color.rgb(123, 123, 123), android.graphics.PorterDuff.Mode.MULTIPLY);
         * view.getBackground().setColorFilter(color, PorterDuff.Mode.DARKEN);
         */

        Drawable drawable = viewGroup.getBackground();
        if (drawable != null) {
            final int alpha = 40;
            switch (position % 3) {
                case 0:
                    drawable.setColorFilter(Color.argb(alpha, 0, 0, 0), PorterDuff.Mode.DARKEN);
                    break;
                case 1:
                    drawable.setColorFilter(Color.argb(alpha, 255, 255, 255), PorterDuff.Mode.LIGHTEN);
                    break;
                case 2:
                    break;
            }
        }

        return view;
    }

    private void clearView(@NonNull final View view) {
        TextView miwokWord = view.findViewById(R.id.miwok_text_view);
        TextView defaultWord = view.findViewById(R.id.default_text_view);
        ImageView imageView = view.findViewById(R.id.image);
        ViewGroup viewGroup = view.findViewById(R.id.text_container);

        miwokWord.setText(null);
        defaultWord.setText(null);
        imageView.setImageResource(Word.NO_IMAGE_ID);
        imageView.setVisibility(View.GONE);
        viewGroup.setBackgroundColor(this.mBackgroundColor);
    }

    private static final int LIST_ITEM = R.layout.list_item;
    private final int mBackgroundColor;

    private static final String TAG = WordAdapter.class.getSimpleName();
}
