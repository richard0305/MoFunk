package ylj.mofunk.model.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class ABSListViewAdapter<T> extends BaseAdapter {

	private List<T> mObjects;
  	private final Object mLock = new Object();

  	private boolean mNotifyOnChange = true;

  	protected Context mContext;

  	protected LayoutInflater mInflater;

  	public ABSListViewAdapter(Context context) {
  		init(context, new ArrayList<T>());
  	}

  	public ABSListViewAdapter(Context context, T[] objects) {
  		init(context, Arrays.asList(objects));
  	}

  	public ABSListViewAdapter(Context context, List<T> objects) {
  		init(context, objects);
  	}
		// TODO Auto-generated constructor stub
	/**
      * 改方法需要子类实现，需要返回item布局的resource id
      * @return
      */
     public abstract int  getLayoutResId();
 
     @Override
     public View getView(int position, View convertView, ViewGroup parent) {
    	 BaseViewHolder holder;
         if(null == convertView){
             holder = new BaseViewHolder(); 
             convertView = inflate(getLayoutResId(), null);            
             convertView.setTag(holder);
         }else{
             holder = (BaseViewHolder) convertView.getTag();
         }
         getView(position, convertView, parent, holder);
         return convertView;
     }
 
	/**
       * 使用该getView方法替换原来的getView方法，需要子类实现
       *  
       * @param position
       * @param convertView
       * @param parent
       * @param holder .obtainView()
       * @return
       */
      public abstract void getView(int position, View convertView, ViewGroup parent, BaseViewHolder holder);
      
      
      

  	private void init(Context context, List<T> objects) {
  		mContext = context;
  		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//  		mInflater=LayoutInflater.from(context);
  		mObjects = objects;
  	}

  	public void upData(List<T> object) {
  		synchronized (mLock) {
  			this.mObjects=object;
  		}
  		if (mNotifyOnChange)
  			notifyDataSetChanged();
  	}
  	
  	public void add(T object) {
  		synchronized (mLock) {
  			mObjects.add(object);
  		}
  		if (mNotifyOnChange)
  			notifyDataSetChanged();
  	}
   
  	public void addAll(Collection<? extends T> collection) {
  		synchronized (mLock) {
  			mObjects.addAll(collection);
  		}
  		if (mNotifyOnChange)
  			notifyDataSetChanged();
  	}
  	public void addAllToHeard(Collection<? extends T> collection) {
  		synchronized (mLock) {
  		
  			mObjects.addAll(0, collection);
  		}
  		if (mNotifyOnChange)
  			notifyDataSetChanged();
  	}
  	public void addAll(T... items) {
  		synchronized (mLock) {
  			Collections.addAll(mObjects, items);
  		}
  		if (mNotifyOnChange)
  			notifyDataSetChanged();
  	}

  	public void insert(T object, int index) {
  		synchronized (mLock) {
  			mObjects.add(index, object);
  		}
  		if (mNotifyOnChange)
  			notifyDataSetChanged();
  	}

  	public void remove(T object) {
  		synchronized (mLock) {
  			mObjects.remove(object);
  		}
  		if (mNotifyOnChange)
  			notifyDataSetChanged();
  	}

  	public void clear() {
  		synchronized (mLock) {
  			mObjects.clear();
  		}
  		if (mNotifyOnChange)
  			notifyDataSetChanged();
  	}

  	public void sort(Comparator<? super T> comparator) {
  		synchronized (mLock) {
  			Collections.sort(mObjects, comparator);
  		}
  		if (mNotifyOnChange)
  			notifyDataSetChanged();
  	}

  	@Override
  	public void notifyDataSetChanged() {
  		super.notifyDataSetChanged();
  		mNotifyOnChange = true;
  	}

  	public void setNotifyOnChange(boolean notifyOnChange) {
  		mNotifyOnChange = notifyOnChange;
  	}

  	public Context getContext() {
  		return mContext;
  	}

  	@Override
  	public int getCount() {
  		try {
  			return mObjects.size();
  		} catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  			return 0;
  		}
  	}

  	@Override
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

  	public View inflate(int resource, ViewGroup root) {
  		return mInflater.inflate(resource, root);
  	}

  	public LayoutInflater getInflater() {
  		return mInflater;
  	}

  	public List<T> getmObjects() {
  		return mObjects;
  	}
}
