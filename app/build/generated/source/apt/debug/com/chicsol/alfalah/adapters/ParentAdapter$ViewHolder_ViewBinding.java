// Generated code from Butter Knife. Do not modify!
package com.chicsol.alfalah.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.chicsol.alfalah.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ParentAdapter$ViewHolder_ViewBinding implements Unbinder {
  private ParentAdapter.ViewHolder target;

  @UiThread
  public ParentAdapter$ViewHolder_ViewBinding(ParentAdapter.ViewHolder target, View source) {
    this.target = target;

    target.rv_child = Utils.findRequiredViewAsType(source, R.id.rv_child, "field 'rv_child'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ParentAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rv_child = null;
  }
}
