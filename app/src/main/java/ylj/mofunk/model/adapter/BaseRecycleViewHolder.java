package ylj.mofunk.model.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * 各个控件的缓存
 */
public class BaseRecycleViewHolder extends RecyclerView.ViewHolder {
	
	private View convertView;
	public BaseRecycleViewHolder(View itemView) {
		super(itemView);
		this.convertView=itemView;
	}

	public SparseArray<View> views = new SparseArray<View>();

	/**
	 * 指定resId和类型即可获取到相应的view
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <V extends View> V obtainView(int resId) {
		View v = views.get(resId);
		if (null == v) {
			v = convertView.findViewById(resId);
			views.put(resId, v);
		}
		return (V) v;
	}
}