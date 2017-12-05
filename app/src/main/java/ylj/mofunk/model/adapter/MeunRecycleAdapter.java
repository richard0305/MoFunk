package ylj.mofunk.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class MeunRecycleAdapter<T> extends RecyclerView.Adapter<BaseRecycleViewHolder> {

	private List<T> mObjects;
	private final Object mLock = new Object();
	private Context mContext;
	private LayoutInflater mInflater;

	@Override
	public void onBindViewHolder(BaseRecycleViewHolder holder, int position) {
		operationViewHolder(holder, position);
	}
	@Override
	public BaseRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return getBaseRecycleViewHolder(parent,viewType);
	}



	/**
	 * 改方法需要子类实现，需要返回item布局的resource id
	 *
	 * @return
	 */
	public abstract BaseRecycleViewHolder getBaseRecycleViewHolder(ViewGroup parent, int viewType);

	public abstract void operationViewHolder(BaseRecycleViewHolder holder, int position);



	public MeunRecycleAdapter(Context context) {
		init(context, new ArrayList<T>());
	}

	public MeunRecycleAdapter(Context context, T[] objects) {
		init(context, Arrays.asList(objects));
	}

	public MeunRecycleAdapter(Context context, List<T> objects) {
		init(context, objects);
	}

	private void init(Context context, List<T> objects) {
		mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mObjects = objects;
	}

	public void upData(List<T> object) {
		synchronized (mLock) {
			this.mObjects=object;
		}
			notifyDataSetChanged();
	}

	public void add(T object) {
		synchronized (mLock) {
			mObjects.add(object);
			int postion = getItemCount() - 1;
			notifyItemRangeInserted(postion, 1);
		}
	}

	public void addAll(Collection<? extends T> collection) {
		synchronized (mLock) {
			mObjects.addAll(collection);
			int postion = getItemCount();
			notifyItemRangeInserted(postion, collection.size());
		}
	}

	public void removeAll(Collection<? extends T> collection) {
		synchronized (mLock) {
			mObjects.removeAll(collection);
			notifyDataSetChanged();
		}
	}

	public void addAllToHeard(Collection<? extends T> collection) {
		synchronized (mLock) {

			mObjects.addAll(0, collection);

			notifyItemRangeInserted(0, collection.size());
		}

	}
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return position%2==0?2:1;// super.getItemViewType(position);
	}
	public void addAll(T... items) {
		synchronized (mLock) {
			Collections.addAll(mObjects, items);
			int postion = getItemCount() - 1;
			notifyItemRangeInserted(postion, items.length);
		}

	}

	public void insert(T object, int index) {
		synchronized (mLock) {
			mObjects.add(index, object);

			notifyItemInserted(index);

		}

	}

	public void remove(T object) {
		synchronized (mLock) {
			int position = mObjects.indexOf(object);
			notifyItemRemoved(position);
			mObjects.remove(object);
		}

	}

	public void clear() {
		synchronized (mLock) {
			mObjects.clear();
		}

		notifyDataSetChanged();
	}

	public void sort(Comparator<? super T> comparator) {
		synchronized (mLock) {
			Collections.sort(mObjects, comparator);
		}

		notifyDataSetChanged();
	}

	public Context getContext() {
		return mContext;
	}

	public T getItem(int position) {
		return mObjects.get(position);
	}

	public int getPosition(T item) {
		return mObjects.indexOf(item);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		try {
			return mObjects.size();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

	}

	public View inflate(int resource, ViewGroup root) {
		return mInflater.inflate(resource, root, false);
	}

	public LayoutInflater getInflater() {
		return mInflater;
	}

	public List<T> getmObjects() {
		return mObjects;
	}

}
