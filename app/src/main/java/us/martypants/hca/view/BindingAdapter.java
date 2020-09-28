package us.martypants.hca.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;


public abstract class BindingAdapter<T extends ViewDataBinding> extends RecyclerView.Adapter<BindingViewHolder<T>> {

    private final int[] mLayoutResourceIds;

    public BindingAdapter(int layoutResourceId) {
        mLayoutResourceIds = new int[1];
        mLayoutResourceIds[0] = layoutResourceId;
    }

    public BindingAdapter(int[] resourceIds) {
        mLayoutResourceIds = resourceIds;
    }

    @Override
    public BindingViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        T binding = createBindingForViewType(parent, viewType);
        return new BindingViewHolder<>(binding);
    }

    private T createBindingForViewType(ViewGroup parent, int viewType) {
        return DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), mLayoutResourceIds[viewType], parent, false);
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<T> holder, int position) {
        T binding = holder.getBinding();
        updateBinding(binding, position);
    }

    protected abstract void updateBinding(T binding, int position);
}
