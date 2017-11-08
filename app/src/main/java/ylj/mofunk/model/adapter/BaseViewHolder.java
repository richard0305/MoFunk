package ylj.mofunk.model.adapter;

import android.util.SparseArray;
import android.view.View;

/**
* 各个控件的缓存
*/
public  class  BaseViewHolder{
public SparseArray<View> views = new SparseArray<View>();

/**
* 指定resId和类型即可获取到相应的view
* @param convertView
* @param resId
* @return
*/
@SuppressWarnings("unchecked")
    public <V extends View> V obtainView(View convertView, int resId){
View v = views.get(resId);
if(null == v){
v = convertView.findViewById(resId);
views.put(resId, v);
}
return (V)v;
}
}