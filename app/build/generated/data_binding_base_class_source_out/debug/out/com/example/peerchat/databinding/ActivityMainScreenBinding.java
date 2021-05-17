// Generated by view binder compiler. Do not edit!
package com.example.peerchat.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewpager.widget.ViewPager;
import com.example.peerchat.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainScreenBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final AppBarLayout barLayout;

  @NonNull
  public final CircleImageView profileImage;

  @NonNull
  public final TabLayout tabLayout;

  @NonNull
  public final Toolbar toolbar;

  @NonNull
  public final TextView username;

  @NonNull
  public final ViewPager viewPage;

  private ActivityMainScreenBinding(@NonNull LinearLayout rootView, @NonNull AppBarLayout barLayout,
      @NonNull CircleImageView profileImage, @NonNull TabLayout tabLayout, @NonNull Toolbar toolbar,
      @NonNull TextView username, @NonNull ViewPager viewPage) {
    this.rootView = rootView;
    this.barLayout = barLayout;
    this.profileImage = profileImage;
    this.tabLayout = tabLayout;
    this.toolbar = toolbar;
    this.username = username;
    this.viewPage = viewPage;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainScreenBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainScreenBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main_screen, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainScreenBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bar_layout;
      AppBarLayout barLayout = rootView.findViewById(id);
      if (barLayout == null) {
        break missingId;
      }

      id = R.id.profile_image;
      CircleImageView profileImage = rootView.findViewById(id);
      if (profileImage == null) {
        break missingId;
      }

      id = R.id.tab_layout;
      TabLayout tabLayout = rootView.findViewById(id);
      if (tabLayout == null) {
        break missingId;
      }

      id = R.id.toolbar;
      Toolbar toolbar = rootView.findViewById(id);
      if (toolbar == null) {
        break missingId;
      }

      id = R.id.username;
      TextView username = rootView.findViewById(id);
      if (username == null) {
        break missingId;
      }

      id = R.id.view_page;
      ViewPager viewPage = rootView.findViewById(id);
      if (viewPage == null) {
        break missingId;
      }

      return new ActivityMainScreenBinding((LinearLayout) rootView, barLayout, profileImage,
          tabLayout, toolbar, username, viewPage);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
