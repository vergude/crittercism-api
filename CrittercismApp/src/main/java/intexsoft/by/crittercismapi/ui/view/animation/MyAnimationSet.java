package intexsoft.by.crittercismapi.ui.view.animation;


import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;

/**
 * Created by Евгений on 26.08.2014.
 */
public class MyAnimationSet extends AnimationSet
{

	private static final int DURATION_ANIMATION = 300;
	private static final int DURATION_ANIMATION_END = 450;
	private static final int OFFSET_ANIMATION = 50;
	private static final float SIZE_ANIMATION = 0.5f;

	public MyAnimationSet(boolean shareInterpolator)
	{
		super(shareInterpolator);

		Animation animationScale = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF,
				SIZE_ANIMATION, Animation.RELATIVE_TO_SELF, SIZE_ANIMATION);
		animationScale.setDuration(DURATION_ANIMATION);
		animationScale.setInterpolator(new AccelerateInterpolator());

		Animation animationAlpha = new AlphaAnimation(0, 1);
		animationAlpha.setDuration(DURATION_ANIMATION);
		animationAlpha.setStartOffset(OFFSET_ANIMATION);
		animationAlpha.setInterpolator(new AccelerateInterpolator());

		Animation animationAlphaEnd = new AlphaAnimation(1, 0);
		animationAlphaEnd.setDuration(DURATION_ANIMATION_END);
		animationAlphaEnd.setStartOffset(DURATION_ANIMATION);
		animationAlphaEnd.setInterpolator(new AccelerateInterpolator());



		this.addAnimation(animationScale);
		this.addAnimation(animationAlpha);
		this.addAnimation(animationAlphaEnd);
	}
}
