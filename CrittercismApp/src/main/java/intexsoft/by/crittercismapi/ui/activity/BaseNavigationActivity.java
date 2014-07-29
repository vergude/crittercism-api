package intexsoft.by.crittercismapi.ui.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.ui.fragment.NavigationDrawerCloser;
import intexsoft.by.crittercismapi.ui.fragment.NavigationListener;


/**
 * Created by eugene.galonsky on 26.03.14.
 */
public abstract class BaseNavigationActivity extends FragmentActivity implements NavigationDrawerCloser, NavigationListener
{
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private View drawerContainer;
	private float drawerSlideOffset;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initDrawer();
	}

	private void initActionBar(ActionBar actionBar)
	{
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
	}

	private void initDrawer()
	{
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerContainer = findViewById(R.id.left_drawer);
		mDrawerToggle = new AppDrawerToogle(this, mDrawerLayout, R.drawable.ic_drawer, 0, 0);
		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	// Make drawer icon visible
	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		if (actionBar != null)
		{
			initActionBar(actionBar);
		}

		if (mDrawerToggle != null)
		{
			mDrawerToggle.syncState();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item))
		{
			return true;
		}
		// Handle your other action bar items...
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void closeDrawer()
	{
		if (mDrawerLayout != null && drawerContainer != null)
		{
			mDrawerLayout.closeDrawer(drawerContainer);
		}
	}

	protected boolean isDrawerOpen()
	{
		boolean result = false;
		if (mDrawerLayout != null && drawerContainer != null)
		{
			result = mDrawerLayout.isDrawerOpen(drawerContainer);
		}
		return result;
	}

	/**
	 * @return Whether drawer is fully closed
	 */
	protected boolean isDrawerHidden()
	{
		return drawerSlideOffset == 0.0f;
	}

	/**
	 * Handler for drawer toggling
	 */
	class AppDrawerToogle extends ActionBarDrawerToggle
	{
		public AppDrawerToogle(Activity activity, DrawerLayout drawerLayout, int drawerImageRes,
							   int openDrawerContentDescRes, int closeDrawerContentDescRes)
		{
			super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes, closeDrawerContentDescRes);
		}

		/**
		 * Called when a drawer has settled in a completely closed state.
		 */
		public void onDrawerClosed(View view)
		{
			// creates call to onPrepareOptionsMenu() when drawer is closed
			invalidateOptionsMenu();
		}

		/**
		 * Called when a drawer has settled in a completely open state.
		 */
		public void onDrawerOpened(View drawerView)
		{
			// creates call to onPrepareOptionsMenu() when drawer is opened
			invalidateOptionsMenu();
		}

		@Override
		public void onDrawerStateChanged(int newState)
		{
			// creates call to onPrepareOptionsMenu() when drawer changes its state
			invalidateOptionsMenu();
		}

		@Override
		public void onDrawerSlide(View drawerView, float slideOffset)
		{
			drawerSlideOffset = slideOffset;
			super.onDrawerSlide(drawerView, slideOffset);
		}
	}

}
