package us.martypants.hca.view;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class BindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    final T mLayoutBinding;

    BindingViewHolder(T layoutBinding) {
        super(layoutBinding.getRoot());
        mLayoutBinding = layoutBinding;
    }

    T getBinding() {
        return mLayoutBinding;
    }
}
