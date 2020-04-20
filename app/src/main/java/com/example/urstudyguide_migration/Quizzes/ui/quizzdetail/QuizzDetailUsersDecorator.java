package com.example.urstudyguide_migration.Quizzes.ui.quizzdetail;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class QuizzDetailUsersDecorator extends RecyclerView.ItemDecoration {
    private final static int vertOverlap = -100;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        final int itemPosition = parent.getChildAdapterPosition(view);

        outRect.set(0, 0, vertOverlap, 0);
    }
}
