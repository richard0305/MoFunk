package ylj.mofunk.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class MeasureSizeGridview extends GridView {


	public MeasureSizeGridview(Context context) {
		super(context);
	}

	public MeasureSizeGridview(Context context, AttributeSet attr) {
		super(context, attr);
	}
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

//		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
//				MeasureSpec.EXACTLY);
//		super.onMeasure(widthMeasureSpec, expandSpec);
		
		int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);   
	}
	
}
