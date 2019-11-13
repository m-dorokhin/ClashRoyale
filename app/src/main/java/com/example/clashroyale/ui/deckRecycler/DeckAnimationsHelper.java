package com.example.clashroyale.ui.deckRecycler;

import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.example.clashroyale.utilits.Action;

public class DeckAnimationsHelper {
    public static Animation getDealCardsAnimation(Action hideCardShirt) {
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(getDistributionCardAnimation());
        animationSet.addAnimation(getTurnCardPart1Animation(hideCardShirt));
        animationSet.addAnimation(getTurnCardPart2Animation());
        return animationSet;
    }

    private static Animation getDistributionCardAnimation() {
        Animation animation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT, -1.0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0.0f,
                TranslateAnimation.RELATIVE_TO_PARENT, -1.0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(400);
        return animation;
    }

    private static Animation getTurnCardPart1Animation(Action hideCardShirt) {
        Animation animation = new ScaleAnimation(
                1.0f, 0.0f, 1.0f, 1.0f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setFillEnabled(true);
        animation.setFillAfter(false);
        animation.setDuration(250);
        animation.setStartOffset(400);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                hideCardShirt.execute();
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        return animation;
    }

    private static Animation getTurnCardPart2Animation() {
        Animation animation = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setFillEnabled(true);
        animation.setFillBefore(false);
        animation.setDuration(250);
        animation.setStartOffset(650);
        return animation;
    }

    public static Animation getIdleAnimation() {
        Animation animation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f);
        animation.setFillEnabled(true);
        animation.setFillAfter(false);
        animation.setDuration(150);

        Animation animation2 = new ScaleAnimation(1.1f, 1.0f, 1.1f, 1.0f);
        animation2.setFillEnabled(true);
        animation2.setFillBefore(false);
        animation2.setStartOffset(150);
        animation2.setDuration(150);

        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(animation);
        animationSet.addAnimation(animation2);
        return animationSet;
    }

    public static Animation getCollectCardsAnimation() {
        Animation animation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 3
        );
        animation.setDuration(1000);
        animation.setFillEnabled(true);
        animation.setFillAfter(true);
        return animation;
    }
}
