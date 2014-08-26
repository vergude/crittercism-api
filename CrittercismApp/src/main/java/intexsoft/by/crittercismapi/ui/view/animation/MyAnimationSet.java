package intexsoft.by.crittercismapi.ui.view.animation;

import android.view.animation.*;

/**
 * Created by Евгений on 26.08.2014.
 */
public class MyAnimationSet extends AnimationSet
{

	public MyAnimationSet(boolean shareInterpolator)
	{
		super(shareInterpolator);

		Animation animationScale = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animationScale.setDuration(300);
		animationScale.setInterpolator(new AccelerateInterpolator());

		Animation animationAlpha = new AlphaAnimation(0, 1);
		animationAlpha.setDuration(300);
		animationAlpha.setStartOffset(50);
		animationAlpha.setInterpolator(new AccelerateInterpolator());

		Animation animationAlphaEnd = new AlphaAnimation(1, 0);
		animationAlphaEnd.setDuration(450);
		animationAlphaEnd.setStartOffset(300);
		animationAlphaEnd.setInterpolator(new AccelerateInterpolator());



		this.addAnimation(animationScale);
		this.addAnimation(animationAlpha);
		this.addAnimation(animationAlphaEnd);
	}
}
